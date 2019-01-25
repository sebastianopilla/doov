/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.mapping.ast;

import static io.doov.core.dsl.meta.DefaultOperator.when;
import static io.doov.core.dsl.meta.MetadataType.MAPPING_LEAF;
import static io.doov.core.dsl.meta.i18n.ResourceBundleProvider.BUNDLE;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.*;
import java.util.*;

import io.doov.core.dsl.meta.LeafMetadata;
import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.ast.AbstractAstVisitor;
import io.doov.core.dsl.meta.i18n.ResourceProvider;

public class AstDoomVisitorSimple extends AbstractAstVisitor {

    protected final ResourceProvider bundle;
    protected final OutputStream ops;
    protected final Locale locale;
    protected int newLineIndex = 0;
    private static List<SingleMappingMetadata> noNestedMapping = new ArrayList<>();
    private int countNested = 0;
    protected static final String headArray = "<table>\n" +
            "  <tr>\n" +
            "    <th>IN</th>\n" +
            "    <th>Converter</th>\n" +
            "    <th>OUT</th>\n" +
            "  </tr>\n";

    public AstDoomVisitorSimple(OutputStream ops, ResourceProvider bundle, Locale locale) {
        this.ops = ops;
        this.bundle = bundle;
        this.locale = locale;
    }

    //Debug
    private static void displayAllChld(Metadata metadata, int depth) {
        long count = metadata.children().count();

        System.out.println(metadata.type() + " " + depth + " " + count);
        if (count == 0) {
            return;
        }
        for (int i = 0; i < depth; i++) {
            System.out.print(" ");
        }
        for (int i = 0; i < count; i++) {
            displayAllChld(metadata.childAt(i), depth + 1);
        }
    }

    private static StringBuilder getNoNestedMappingHtml() {

        StringBuilder sb = new StringBuilder();

        for (SingleMappingMetadata smm : noNestedMapping) {

            sb.append(beginElement("tr"))
                    .append(beginElement("td"))
                    .append(smm.input)
                    .append(endElement("td")).append("\n")
                    .append(beginElement("td")).append("\n");

            if (smm.converters.isEmpty()) {
                sb.append("none");
            } else {
                sb.append(beginElement("ul"));
                for (LeafMetadata<?> converter : smm.converters) {
                    sb.append(beginElement("li"))
                            .append(converter)
                            .append(endElement("li")).append("\n");
                }
                sb.append(endElement("ul"));
            }

            sb.append(endElement("td")).append("\n")
                    .append(beginElement("td"))
                    .append(smm.output)
                    .append(endElement("td")).append("\n");
        }
        return sb;
    }

    public static String astToHtml(Metadata metadata, Locale locale) {
        ByteArrayOutputStream ops = new ByteArrayOutputStream();
        new AstDoomVisitorSimple(ops, BUNDLE, locale).browse(metadata, 0);
        StringBuilder res = new StringBuilder();
        noNestedMapping = new ArrayList<>(new LinkedHashSet<>(noNestedMapping));

        if (!noNestedMapping.isEmpty()) {
            res.append(headArray).append(getNoNestedMappingHtml()).append(endElement("table"));
        }

        if (ops.size() > 0) {
            res.append(new String(ops.toByteArray(), UTF_8));
        }
        noNestedMapping = new ArrayList<>();
        return res.toString();
    }

    protected void write(String s) {
        try {
            ops.write(s.getBytes(UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String beginElement(String elementType) {
        return "<" + elementType + ">";
    }

    private static String endElement(String elementType) {
        return "</" + elementType + ">";
    }

    public Metadata getLeafFromSingleMapping(Metadata metadata) {
        if (metadata.type() == MAPPING_LEAF) {
            return metadata;
        }
        return getLeafFromSingleMapping(metadata.childAt(0));
    }

    @Override
    public void startMappingRule(Metadata metadata, int depth) {
        switch (metadata.type()) {
            case SINGLE_MAPPING:
                if (countNested == 0) {
                    noNestedMapping.add(new SingleMappingMetadata(getLeafFromSingleMapping(metadata.childAt(0)),
                            metadata.childAt((int) metadata.children().count() - 1)));
                    break;
                }
                write(headArray + beginElement("tr") + "\n"
                        + beginElement("td")
                        + getLeafFromSingleMapping(metadata) + endElement("td")
                        + "\n"
                        + beginElement("td"));
                break;
            case MULTIPLE_MAPPING:
                break;
            case MAPPING_INPUT:
                break;
            case THEN_MAPPING:
                break;
            case ELSE_MAPPING:
                if (countNested == 0) {
                    break;
                }
                write(endElement("li") + "\n");
                if (metadata.children().count() == 0) {
                    write(endElement("ul") + "\n");
                    break;
                }
                write(beginElement("li")
                        + beginElement("i")
                        + "else"
                        + endElement("i")
                );
                break;
            default:
                break;
        }
    }

    @Override
    public void endMappingRule(Metadata metadata, int depth) {
        switch (metadata.type()) {
            case SINGLE_MAPPING:
                if (countNested == 0) {
                    break;
                }
                write(endElement("td") + "\n"
                        + beginElement("td")
                        + metadata.childAt((int) metadata.children().count() - 1)
                        + endElement("td") + "\n" + endElement("tr") + "\n"
                        + endElement("table") + "\n");
                break;
            case MULTIPLE_MAPPING:
                break;
            case MAPPING_INPUT:
                break;
            case THEN_MAPPING:
                break;
            case ELSE_MAPPING:
                countNested--;
                if (metadata.children().count() != 0) {
                    write(endElement("li") + endElement("ul"));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void startWhen(Metadata metadata, int depth) {
        countNested++;
        write(beginElement("ul")
                + "\n"
                + beginElement("li")
                + beginElement("i")
                + formatWhen(metadata)
                + endElement("i")
                + "\n");
    }

    @Override
    public void startTypeConverter(LeafMetadata<?> metadata, int depth) {
        if (countNested == 0) {
            noNestedMapping.get(noNestedMapping.size() - 1).addConverter(metadata);
            return;
        }
        write(metadata.toString());
    }

    protected String formatWhen() {
        return bundle.get(when, locale);
    }

    private String formatWhen(Metadata metadata) {
        return metadata == null ? null : metadata.readable();
    }

    private class SingleMappingMetadata {

        private Metadata input;
        private List<LeafMetadata<?>> converters;
        private Metadata output;

        public SingleMappingMetadata(Metadata input, Metadata output) {
            converters = new ArrayList<>();
            this.input = input;
            this.output = output;
        }

        public void addConverter(LeafMetadata<?> metadata) {
            converters.add(metadata);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            SingleMappingMetadata that = (SingleMappingMetadata) o;

            return Objects.equals(input, that.input) &&
                    Objects.equals(converters, that.converters) &&
                    Objects.equals(output, that.output);

        }

        @Override
        public int hashCode() {
            return Objects.hash(input, converters, output);
        }

        @Override
        public String toString() {
            return "{" +
                    "input=" + input +
                    ", converters=" + converters.size() +
                    ", output=" + output +
                    '}';
        }
    }
}
