/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.mapping.ast;

import static io.doov.core.dsl.meta.MetadataType.MAPPING_LEAF;
import static io.doov.core.dsl.meta.i18n.ResourceBundleProvider.BUNDLE;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;

import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.ast.HtmlWriter;
import io.doov.core.dsl.meta.i18n.ResourceProvider;

public class AstDoomHtmlRendererTable extends HtmlWriter {

    private Deque<String> nestedWhen = new ArrayDeque<>();

    public AstDoomHtmlRendererTable(Locale locale, OutputStream os, ResourceProvider resources) {
        super(locale, os, resources);
    }

    public static String toHtml(Metadata metadata, Locale locale) {
        final ByteArrayOutputStream ops = new ByteArrayOutputStream();
        (new AstDoomHtmlRendererTable(locale, ops, BUNDLE)).toHtml(metadata);
        return new String(ops.toByteArray(), UTF_8);
    }

    public void toHtml(Metadata metadata) {
        toHtml(metadata, new ArrayDeque<>());
    }

    private void fieldToHtml() {

        writeBeginTable();
        writeBeginTr();
        writeBeginTh();
        write("In Fields");
        writeEndTh();

        writeBeginTh();
        write("Out Fields");
        writeEndTh();

        writeBeginTh();
        write("Conversion/Condition");
        writeEndTh();

        writeEndTr();

    }

    private void toHtml(Metadata metadata, ArrayDeque<Metadata> parents) {
        if (parents.size() == 0) {
            fieldToHtml();
        }
        parents.push(metadata);
        try {
            switch (metadata.type()) {
                case SINGLE_MAPPING:
                    singleMapping(metadata, parents);
                    break;
                case MAPPING_INPUT:
                    toHtmlChildren(metadata, parents);
                    break;
                case WHEN:
                    whenMapping(metadata, parents);
                    break;
                case MULTIPLE_MAPPING:
                    multipleMapping(metadata, parents);
                    break;
                case THEN_MAPPING:
                    thenMapping(metadata, parents);
                    break;
                case ELSE_MAPPING:
                    elseMapping(metadata, parents);
                    break;
                case TYPE_CONVERTER:
                    typeConverter(metadata, parents);
                    break;
                default:
                    break;
            }
        } finally {
            parents.pop();
            if (parents.size() == 0) {
                writeEndTable();
            }
        }
    }

    protected void singleMapping(Metadata metadata, ArrayDeque<Metadata> parents) {
        Metadata inMeta = getLeafFromSingleMapping(metadata);
        Metadata outMeta = metadata.childAt(1);

        writeBeginTr();

        writeBeginTd();
        write(inMeta.readable());
        writeEndTd();

        writeBeginTd();
        write(outMeta.readable());
        writeEndTd();

        writeBeginTd();
        write(getConditionHtml());
        if (metadata.childAt(0).children().count() > 0 && !nestedWhen.isEmpty()) {
            write("<hr>");
        }
        toHtmlChildren(metadata, parents);
        writeEndTd();
        writeEndTr();
    }

    private String getConditionHtml() {
        StringBuilder res = new StringBuilder();
        if (!nestedWhen.isEmpty()) {
            Iterator<String> it = nestedWhen.descendingIterator();
            while (it.hasNext()) {
                String when = it.next();
                res.append("<ul><li>").append(when);
            }
            for (int i = 0; i < nestedWhen.size(); i++) {
                res.append("</li></ul>");
            }
        }
        return res.toString();
    }

    protected Metadata getLeafFromSingleMapping(Metadata metadata) {
        if (metadata.type() == MAPPING_LEAF) {
            return metadata;
        }
        return getLeafFromSingleMapping(metadata.childAt(0));
    }

    protected void whenMapping(Metadata metadata, ArrayDeque<Metadata> parents) {
        nestedWhen.push(metadata.readable());
        toHtmlChildren(metadata, parents);
    }

    protected void typeConverter(Metadata metadata, ArrayDeque<Metadata> parents) {
        write(metadata.readable());
        write("<br>");
    }

    protected void multipleMapping(Metadata metadata, ArrayDeque<Metadata> parents) {
        toHtmlChildren(metadata, parents);
    }

    protected void thenMapping(Metadata metadata, ArrayDeque<Metadata> parents) {
        toHtmlChildren(metadata, parents);
    }

    protected void elseMapping(Metadata metadata, ArrayDeque<Metadata> parents) {
        if (metadata.children().count() == 0) {
            nestedWhen.pop();
            return;
        }
        nestedWhen.push("not( " + nestedWhen.pop() + " )");
        toHtmlChildren(metadata, parents);
        nestedWhen.pop();
    }

    private void toHtmlChildren(Metadata metadata, ArrayDeque<Metadata> parents) {
        metadata.children().forEach(chld -> toHtml(chld, parents));
    }

}
