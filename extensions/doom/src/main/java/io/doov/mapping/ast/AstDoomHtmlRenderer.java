/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.mapping.ast;

import static io.doov.core.dsl.meta.MetadataType.MAPPING_LEAF;
import static io.doov.core.dsl.meta.i18n.ResourceBundleProvider.BUNDLE;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Locale;

import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.ast.HtmlWriter;
import io.doov.core.dsl.meta.i18n.ResourceProvider;

public class AstDoomHtmlRenderer extends HtmlWriter {

    public AstDoomHtmlRenderer(Locale locale, OutputStream os, ResourceProvider resources) {
        super(locale, os, resources);
    }

    protected static final String headArray = "<table>\n" +
            "  <tr>\n" +
            "    <th>IN</th>\n" +
            "    <th>Converter</th>\n" +
            "    <th>OUT</th>\n" +
            "  </tr>\n";

    public static String toHtml(Metadata metadata, Locale locale) {
        final ByteArrayOutputStream ops = new ByteArrayOutputStream();
        new AstDoomHtmlRenderer(locale, ops, BUNDLE).toHtml(metadata);
        return new String(ops.toByteArray(), UTF_8);
    }

    public void toHtml(Metadata metadata) {
        toHtml(metadata, new ArrayDeque<>());
    }

    public Metadata getLeafFromSingleMapping(Metadata metadata) {
        if (metadata.type() == MAPPING_LEAF) {
            return metadata;
        }
        return getLeafFromSingleMapping(metadata.childAt(0));
    }

    private void toHtml(Metadata metadata, ArrayDeque<Metadata> parents) {
        parents.push(metadata);
        try {
            switch (metadata.type()) {
                case SINGLE_MAPPING:
                    singleMapping(metadata, parents);
                    break;
                case MAPPING_INPUT:
                    toHtmlChildren(metadata, parents);
                    break;
                case MULTIPLE_MAPPING:
                case THEN_MAPPING:
                    toHtmlChildren(metadata, parents);
                    break;
                case ELSE_MAPPING:
                    elseMapping(metadata, parents);
                    break;
                case TYPE_CONVERTER:
                    typeConverter(metadata, parents);
                    break;
                case WHEN:
                    writeBeginUl();
                    writeBeginLi();
                    writeBeginI();
                    write(metadata.toString());
                    writeEndI();
                    break;
                default:
                    break;
            }
        } finally {
            parents.pop();
        }
    }

    public void singleMapping(Metadata metadata, ArrayDeque<Metadata> parents) {
        write(headArray);
        writeBeginTr();
        writeBeginTd();
        write(getLeafFromSingleMapping(metadata).readable());
        writeEndTd();

        writeBeginTd();
        toHtmlChildren(metadata, parents);
        writeEndTd();

        writeBeginTd();
        write(metadata.childAt(1).readable());
        writeEndTd();

        writeEndTr();
        writeEndTable();
    }

    private void typeConverter(Metadata metadata, ArrayDeque<Metadata> parents) {
        write(metadata.readable());
    }

    private void elseMapping(Metadata metadata, ArrayDeque<Metadata> parents) {
        writeEndLi();
        if (metadata.children().count() == 0) {
            writeEndUl();
            return;
        }
        writeBeginLi();
        writeBeginI();
        write("else");
        writeEndI();

        toHtmlChildren(metadata, parents);

        if (metadata.children().count() != 0) {
            writeEndLi();
            writeEndUl();
        }
    }

    public void toHtmlChildren(Metadata metadata, ArrayDeque<Metadata> parents) {
        metadata.children().forEach(chld -> toHtml(chld, parents));
    }

}
