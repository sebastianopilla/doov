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

import io.doov.core.FieldModel;
import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.ast.HtmlWriter;
import io.doov.core.dsl.meta.i18n.ResourceProvider;

public class AstDoomHtmlRenderer2 extends HtmlWriter {

    private Map<String, Integer> mapIn = new HashMap<>();
    private Map<String, Integer> mapOut = new HashMap<>();
    private Map<Integer, Tuple<Integer, String, String>> mapping = new HashMap<>();
    private Deque<String> nestedWhen = new ArrayDeque<>();
    private String currentTypeConverter = "";

    public AstDoomHtmlRenderer2(Locale locale, OutputStream os, ResourceProvider resources) {
        super(locale, os, resources);
    }

    public static String toHtml(FieldModel inputModel, FieldModel outputModel, Metadata metadata, Locale locale) {
        final ByteArrayOutputStream ops = new ByteArrayOutputStream();
        AstDoomHtmlRenderer2 astrenderer = new AstDoomHtmlRenderer2(locale, ops, BUNDLE);
        astrenderer.fieldToHtml(inputModel, outputModel);
        astrenderer.toHtml(metadata);
        String head = astrenderer.getHeadHtmlWithScriptJs();
        return head + new String(ops.toByteArray(), UTF_8) + "\n</body>\n</html>";
    }

    private String getHeadHtmlWithScriptJs() {
        StringBuilder js = new StringBuilder();
        js.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "<style>\n" +
                "table,td,tr{\n" +
                "border: 1px solid black;\n" +
                "}\n" +
                ".line_selected {\n" +
                "  stroke-dashoffset: 0;\n" +
                "  stroke-width: 6;\n" +
                "  stroke : #949382;\n" +
                "  cursor: pointer;\n" +
                "}" +
                "line:hover {\n" +
                "  stroke-dashoffset: 0;\n" +
                "  stroke-width: 6;\n" +
                "  cursor:pointer;\n" +
                "}\n" +
                ".line_hover {\n" +
                "  stroke-dashoffset: 0;\n" +
                "  stroke-width: 6;\n" +
                "  cursor: pointer;\n" +
                "}" +
                "</style>\n");
        js.append("<script  type=\"text/javascript\">\n");
        js.append("var mappings = [");

        mapping.forEach((indIn, indOut) -> js.append("[").append(indIn).append(",").append(indOut.x).append(",").append(indOut.y).append(",").append(indOut.z).append("],"));

        if (mapping.size() > 0) {
            js.setLength(js.length() - 1);
        }
        js.append("];\n" +
                "\n" +
                "function main(){\n" +
                "\n" +
                "function getLine(input, out) {\n" +
                "  return document.getElementById(\"line-\" + input + \"-\" + out);\n" +
                "}\n" +
                "\n" +
                "function updateYaxis(triplet) {\n" +
                "  let line = getLine(triplet[0], triplet[1]);\n" +
                "  let y1 = document.getElementById(`td-field-${triplet[0]}-in`).getBoundingClientRect().y + window" +
                ".scrollY;\n" +
                "  let y2 = document.getElementById(`td-field-${triplet[1]}-out`).getBoundingClientRect().y + window" +
                ".scrollY;\n" +
                "  line.setAttribute('y1', y1);\n" +
                "  line.setAttribute('y2', y2);\n" +
                "}\n" +
                "\n" +
                "function highlightIn(mappings, id) {\n" +
                "  let selection = mappings.filter(m =>\n" +
                "    m[0] == id\n" +
                "  );\n" +
                "  selection.forEach(m => {\n" +
                "    let line = getLine(m[0], m[1]);\n" +
                "    if (line.classList.contains(\"line_selected\")) {\n" +
                "      line.classList.remove(\"line_selected\");\n" +
                "    } else {\n" +
                "      line.classList.add(\"line_selected\");\n" +
                "    }\n" +
                "  });\n" +
                "}\n" +
                "\n" +
                "function highlightOut(mappings, id) {\n" +
                "  let selection = mappings.filter(m =>\n" +
                "    m[1] == id\n" +
                "  );\n" +
                "  selection.forEach(m => {\n" +
                "    let line = getLine(m[0], m[1]);\n" +
                "    if (line.classList.contains(\"line_selected\")) {\n" +
                "      line.classList.remove(\"line_selected\");\n" +
                "    } else {\n" +
                "      line.classList.add(\"line_selected\");\n" +
                "    }\n" +
                "  });\n" +
                "}\n" +
                "\n" +
                "function drawLine(triplet) {\n" +
                "  let offset = 2;\n" +
                "  let x1 = 0;\n" +
                "  let inTdElement = document.getElementById(`td-field-${triplet[0]}-in`);\n" +
                "  let inRect = inTdElement.getBoundingClientRect();\n" +
                "  let outTdElement = document.getElementById(`td-field-${triplet[1]}-out`);\n" +
                "  let outRect = outTdElement.getBoundingClientRect();\n" +
                "\n" +
                "\tinTdElement.style.cursor = \"pointer\";\n" +
                "\toutTdElement.style.cursor = \"pointer\";\n" +
                "\n" +
                "  inTdElement.onclick = function() {\n" +
                "    highlightIn(mappings, triplet[0])\n" +
                "  };\n" +
                "  outTdElement.onclick = function() {\n" +
                "    highlightOut(mappings, triplet[1])\n" +
                "  };\n" +
                "\n" +
                "  let y1 = inRect.y  + window.scrollY;\n" +
                "  let top = inRect.top + window.scrollY;\n" +
                "  let x2 = \"100%\";\n" +
                "  let y2 = outRect.y  + window.scrollY;\n" +
                "\n" +
                "  let svg = document.getElementsByTagName('svg')[0];\n" +
                "  let newLine = document.createElementNS('http://www.w3.org/2000/svg', 'line');\n" +
                "\n" +
                "  newLine.setAttribute('x1', x1);\n" +
                "  newLine.setAttribute('y1', y1);\n" +
                "  newLine.setAttribute('x2', x2);\n" +
                "  newLine.setAttribute('y2', y2);\n" +
                "  newLine.setAttribute(\"stroke\", \"black\");\n" +
                "  newLine.setAttribute(\"stroke-width\", \"4\");\n" +
                "  newLine.setAttribute(\"id\", \"line-\" + triplet[0] + \"-\" + triplet[1]);\n" +
                "\n" +
                "  svg.insertAdjacentElement('beforeend', newLine);\n" +
                "\n" +
                "\n" +
                "  if ((triplet[2]).length != 0) {\n" +
                "\n" +
                "    let text = document.createElementNS('http://www.w3.org/2000/svg', 'text');\n" +
                "    let group = document.createElementNS('http://www.w3.org/2000/svg', 'g');\n" +
                "    let rect = document.createElementNS('http://www.w3.org/2000/svg', 'rect');\n" +
                "    text.setAttribute('y', top);\n" +
                "\n" +
                "    svg.insertAdjacentElement('afterbegin', group);\n" +
                "    group.insertAdjacentElement('afterbegin', rect);\n" +
                "    group.insertAdjacentElement('beforeend', text);\n" +
                "\n" +
                "    triplet[2].forEach(textConverter => {\n" +
                "      let newText = document.createElementNS('http://www.w3.org/2000/svg', 'tspan');\n" +
                "      newText.setAttribute('x', \"40%\");\n" +
                "      newText.setAttribute('dy', \"1.0em\");\n" +
                "      \n" +
                "      newText.innerHTML = textConverter;\n" +
                "      group.style.visibility = 'hidden';\n" +
                "      newLine.addEventListener(\"mouseenter\", function() {\n" +
                "        group.style.visibility = 'visible';\n" +
                "      });\n" +
                "      newLine.addEventListener(\"mouseleave\", function() {\n" +
                "        group.style.visibility = 'hidden';\n" +
                "      });\n" +
                "      text.insertAdjacentElement('afterbegin', newText);\n" +
                "    });\n" +
                "    let title = document.createElementNS('http://www.w3.org/2000/svg', 'title');\n" +
                "    title.innerHTML = triplet[2].join(\"\\n\");\n" +
                "    newLine.insertAdjacentElement('afterbegin', title);\n" +
                "\n" +
                "    let tspanElement = document.getElementsByTagName('tspan')[0];\n" +
                "    let fontSize = parseInt(window.getComputedStyle(tspanElement, null).getPropertyValue" +
                "(\"font-size\"), 10);\n" +
                "    rect.setAttribute('x', \"40%\");\n" +
                "    rect.setAttribute('y', text.getBoundingClientRect().top + window.scrollY - fontSize);\n" +
                "    rect.setAttribute('width', text.getBoundingClientRect().width);\n" +
                "    rect.setAttribute('height', text.getBoundingClientRect().height + fontSize);\n" +
                "    rect.setAttribute('fill', \"none\");\n" +
                "    rect.setAttribute('stroke-width', \"2px\");\n" +
                "    rect.setAttribute('stroke', \"rgb(0,0,0)\");\n" +
                "  }\n" +
                "\n" +
                "  if (triplet[3].length != 0) {\n" +
                "    let newText = document.createElementNS('http://www.w3.org/2000/svg', 'text');\n" +
                "    let group = document.createElementNS('http://www.w3.org/2000/svg', 'g');\n" +
                "    group.addEventListener(\"mouseenter\", function() {\n" +
                "        newLine.classList.add(\"line_hover\");\n" +
                "      });\n" +
                "    group.addEventListener(\"mouseleave\", function() {\n" +
                "        newLine.classList.remove(\"line_hover\");\n" +
                "      });\n" +
                "      \n" +
                "    triplet[3].forEach((condition, i)=>{\n" +
                "      let tspan = document.createElementNS('http://www.w3.org/2000/svg', 'tspan');\n" +
                "    \ttspan.innerHTML = condition;\n" +
                "     \ttspan.setAttribute(\"x\", i+\"em\" );\n" +
                "      if(i>0){\n" +
                "      \ttspan.setAttribute('dy', \"1em\");\n" +
                "      }\n" +
                "     \tnewText.insertAdjacentElement('beforeend', tspan);\n" +
                "    })\n" +
                "    svg.insertAdjacentElement('afterbegin', group);\n" +
                "\n" +
                "    newText.setAttribute('x', \"10%\");\n" +
                "    newText.setAttribute('dy', \"1.0em\");\n" +
                "    newText.setAttribute('y', top);\n" +
                "\n" +
                "    group.insertAdjacentElement(\"afterbegin\", newText);\n" +
                "  }\n" +
                "}\n" +
                "var resizeTimeout;\n" +
                "\n" +
                "function resizeThrottler(mapping) {\n" +
                "  if (!resizeTimeout) {\n" +
                "    resizeTimeout = setTimeout(function() {\n" +
                "      resizeTimeout = null;\n" +
                "      mapping.forEach(m => {\n" +
                "        updateYaxis(m);\n" +
                "      })\n" +
                "    }, 150);\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "mappings.forEach(mapping => {\n" +
                "  drawLine(mapping);\n" +
                "});\n" +
                "\n" +
                "window.addEventListener('resize', function(event) {\n" +
                "  resizeThrottler(mappings);\n" +
                "}, false);"
                + "}\n"
        );
        js.append("</script>\n");
        js.append("</head>\n<body onload=\"main();\">\n");

        return js.toString();
    }

    private void fieldToHtml(FieldModel inmodel, FieldModel outmodel) {

        writeBeginDivWithStyle("display:flex; justify-content: space-between");

        writeBeginTable();
        writeBeginTr();
        writeBeginTd();
        write("In Fields");
        writeEndTd();
        writeEndTr();
        writeModelField(inmodel, true);

        write("<svg width=\"100%\"></svg>");

        writeBeginTable();
        writeBeginTr();
        writeBeginTd();
        write("Out Fields");
        writeEndTd();
        writeEndTr();
        writeModelField(outmodel, false);

        writeEndDiv();
    }

    private void writeModelField(FieldModel model, boolean val) {
        for (int i = 0; i < model.getFieldInfos().size(); i++) {
            writeBeginTr();
            writeBeginTdWithId("td-field-" + i + "-" + (val ? "in" : "out"));
            write(model.getFieldInfos().get(i).readable());
            if (val) {
                mapIn.put(model.getFieldInfos().get(i).readable(), i);
            } else {
                mapOut.put(model.getFieldInfos().get(i).readable(), i);
            }
            writeEndTd();
            writeEndTr();
        }
        writeEndTable();
    }

    public void toHtml(Metadata metadata) {
        toHtml(metadata, new ArrayDeque<>());
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
                case WHEN:
                    nestedWhen.push(metadata.readable());
                    toHtmlChildren(metadata, parents);
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
        }
    }

    protected void thenMapping(Metadata metadata, ArrayDeque<Metadata> parents) {
        toHtmlChildren(metadata, parents);
    }

    protected void multipleMapping(Metadata metadata, ArrayDeque<Metadata> parents) {
        toHtmlChildren(metadata, parents);
    }

    protected void typeConverter(Metadata metadata, ArrayDeque<Metadata> parents) {
        currentTypeConverter += "\"" + metadata.readable() + "\",";
    }

    protected void singleMapping(Metadata metadata, ArrayDeque<Metadata> parents) {
        Metadata inMeta = getLeafFromSingleMapping(metadata);
        Metadata outMeta = metadata.childAt(1);
        toHtmlChildren(metadata, parents);
        if (!currentTypeConverter.isEmpty()) {
            currentTypeConverter = currentTypeConverter.substring(0, currentTypeConverter.length() - 1);
        }

        StringBuilder res = new StringBuilder().append("[");
        Iterator<String> it = nestedWhen.descendingIterator();
        while (it.hasNext()) {
            String when = it.next();
            res.append("\"").append(when).append("\",");
        }
        if (nestedWhen.size() > 0) {
            res.setLength(res.length() - 1);
        }
        res.append("]");

        mapping.put(mapIn.get(inMeta.readable()), new Tuple<>(mapOut.get(outMeta.readable()),
                "[" + currentTypeConverter + "]", res.toString()));
        currentTypeConverter = "";
    }

    protected void elseMapping(Metadata metadata, ArrayDeque<Metadata> parents) {
        if (metadata.children().count() == 0) {
            return;
        }
        nestedWhen.push("not( " + nestedWhen.getLast() + " )");
        toHtmlChildren(metadata, parents);
        nestedWhen.pop();
        nestedWhen.pop();
    }

    private void toHtmlChildren(Metadata metadata, ArrayDeque<Metadata> parents) {
        metadata.children().forEach(chld -> toHtml(chld, parents));
    }

    protected Metadata getLeafFromSingleMapping(Metadata metadata) {
        if (metadata.type() == MAPPING_LEAF) {
            return metadata;
        }
        return getLeafFromSingleMapping(metadata.childAt(0));
    }

    private static class Tuple<X, Y, Z> {

        public final X x;
        public final Y y;
        public final Z z;

        Tuple(X x, Y y, Z z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}

