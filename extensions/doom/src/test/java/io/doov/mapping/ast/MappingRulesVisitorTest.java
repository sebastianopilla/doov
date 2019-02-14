/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.mapping.ast;

import static io.doov.mapping.ast.AstDoomHtmlRenderer.toHtml;
import static java.util.Locale.FRANCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jsoup.Jsoup.parseBodyFragment;

import java.util.Locale;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import io.doov.core.dsl.meta.Metadata;
import io.doov.mapping.model.SampleModel;

public class MappingRulesVisitorTest {

    private SampleModel model = new SampleModel();
    private static final Locale LOCALE = Locale.US;

    @Test
    public void testHtml() {
        model.mappingRules().forEach(mappingRule -> {
            System.out.println(
                    AstDoomVisitorSimple.astToHtml(mappingRule.metadata(), FRANCE)
            );
            System.out.println("--------------");
        });
    }

    @Test
    public void testHtmlRenderer() {
        model.mappingRules().forEach(mappingRule -> {
            System.out.println(
                    toHtml(mappingRule.metadata(), FRANCE)
            );
            System.out.println("--------------");
        });
    }

    @Test
    public void testHtmlRendererTable() {
        System.out.println(
                AstDoomHtmlRendererTable.toHtml(model.getMappingRule9().metadata()
                        , FRANCE)
        );
    }

    @Test
    public void testHtmlRenderer2() {
        System.out.println(
                AstDoomHtmlRenderer2.toHtml(model.getModelLF(), model.getModelI(),
                        model.getMappingRuleFieldByField().metadata()
                        , FRANCE)
        );
    }

    static Document documentOf(Metadata metadata) {
        return parseBodyFragment(toHtml(metadata, LOCALE));
    }

    @Test
    public void testRuleSimple1() {
        Document doc = documentOf(model.getMappingRule1().metadata());
        assertThat(doc.body().select("table")).hasSize(1);

        assertThat(doc.body().select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(0)).extracting(Element::text).containsExactly("tauxModeleFurets");
        assertThat(doc.body().select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(1)).extracting(Element::text).containsExactly("using 'taux converter'using 'taux converter 2'");
        assertThat(doc.body().select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(2)).extracting(Element::text).containsExactly("tauxModeleInsurer");
    }

    @Test
    public void testRuleSimple2() {
        Document doc = documentOf(model.getMappingRule2().metadata());
        assertThat(doc.body().select("table")).hasSize(1);

        assertThat(doc.body().select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(0)).extracting(Element::text).containsExactly("driver nom");
        assertThat(doc.body().select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(1)).extracting(Element::text).containsExactly("");
        assertThat(doc.body().select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(2)).extracting(Element::text).containsExactly("conducteur nom");
    }

    @Test
    public void testRuleSimple3() {
        Document doc = documentOf(model.getMappingRule3().metadata());
        assertThat(doc.body().select("table")).hasSize(4);
        assertThat(doc.body().select("ul")).hasSize(2);

        assertThat(doc.body().select("ul>li").get(0)
                .select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(0)).extracting(Element::text).containsExactly("driver nom");

        assertThat(doc.body().select("ul>li").get(0)
                .select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(1)).extracting(Element::text).containsExactly("");

        assertThat(doc.body().select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(2)).extracting(Element::text).containsExactly("conducteur nom");
        //nested
        assertThat(doc.body().select("ul>li").get(0)
                .select("ul>li").get(0)
                .select("table")).hasSize(1);

        assertThat(doc.body().select("ul>li").get(0)
                .select("ul>li").get(0)
                .select("table").get(0)
                .select("tr")
                .get(1).select("td")
                .get(0)).extracting(Element::text).containsExactly("driver nom");

        assertThat(doc.body().select("ul>li").get(0)
                .select("ul>li").get(0)
                .select("table").get(0)
                .select("tr")
                .get(1).select("td")
                .get(1)).extracting(Element::text).containsExactly("");

        assertThat(doc.body().select("ul>li").get(0)
                .select("ul>li").get(0)
                .select("table").get(0)
                .select("tr")
                .get(1).select("td")
                .get(2)).extracting(Element::text).containsExactly("conducteur nom");

        //else
        assertThat(doc.body().select("ul>li").get(0)
                .select("ul>li").get(1)
                .select("table")).hasSize(1);

        assertThat(doc.body().select("ul>li").get(0)
                .select("ul>li").get(1)
                .select("table").get(0)
                .select("tr").get(1)
                .select("td").get(0))
                .extracting(Element::text).containsExactly("driver nom");

        assertThat(doc.body().select("ul>li").get(0)
                .select("ul>li").get(1)
                .select("table").get(0)
                .select("tr").get(1)
                .select("td").get(1))
                .extracting(Element::text).containsExactly("using 'nom to taux'");

        assertThat(doc.body().select("ul>li").get(0)
                .select("ul>li").get(1)
                .select("table").get(0)
                .select("tr").get(1)
                .select("td").get(2))
                .extracting(Element::text).containsExactly("tauxModeleInsurer");

        // //else
        assertThat(doc.body().select("ul").get(0).select("li").last()
                .select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(0)).extracting(Element::text).containsExactly("driver nom");

        assertThat(doc.body().select("ul").get(0).select("li").last()
                .select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(1)).extracting(Element::text).containsExactly("using 'nom to taux'");

        assertThat(doc.body().select("ul").get(0).select("li").last()
                .select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(2)).extracting(Element::text).containsExactly("tauxModeleInsurer");
    }

    @Test
    public void testRuleSimple4() {
        Document doc = documentOf(model.getMappingRule4().metadata());

        assertThat(doc.body().select("table tr").get(1)
                .select("td").get(0))
                .extracting(Element::text).containsExactly("tauxModeleFurets et taux2ModeleFurets et " +
                "taux3ModeleFurets");

        assertThat(doc.body().select("table tr").get(1)
                .select("td").get(1))
                .extracting(Element::text).containsExactly("using 'count taux " +
                "converter'");

        assertThat(doc.body().select("table tr").get(1)
                .select("td").get(2))
                .extracting(Element::text).containsExactly("tauxModeleInsurer");
    }

    @Test
    public void testRuleSimple10() {
        Document doc = documentOf(model.getMappingRule10().metadata());

        assertThat(doc.body().select("ul>li").get(0)
                .select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(0)).extracting(Element::text).containsExactly("driver nom");

        assertThat(doc.body().select("ul>li").get(0)
                .select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(1)).extracting(Element::text).containsExactly("");

        assertThat(doc.body().select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(2)).extracting(Element::text).containsExactly("conducteur nom");

        assertThat(doc.body().select("ul>li").get(1)
                .select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(0)).extracting(Element::text).containsExactly("driver nom");

        assertThat(doc.body().select("ul>li").get(1)
                .select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(1)).extracting(Element::text).containsExactly("using 'nom to taux'");

        assertThat(doc.body().select("ul>li").get(1)
                .select("table")
                .get(0).select("tr")
                .get(1).select("td")
                .get(2)).extracting(Element::text).containsExactly("tauxModeleInsurer");
    }

}
