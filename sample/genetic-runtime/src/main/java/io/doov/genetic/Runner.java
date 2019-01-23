/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic;

import java.io.File;
import java.util.Random;

import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.DefaultOperator;
import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.predicate.BinaryPredicateMetadata;
import io.doov.core.dsl.meta.predicate.LeafPredicateMetadata;
import io.doov.genetic.field.dsl.DslPixel;
import io.doov.genetic.model.*;

public class Runner {

    public static final int NB_GENERATIONS = 10;

    public static void main(String[] args) {
        try {
            File input = new File("./sample/genetic-runtime/src/main/resources/bansky.png");

            Image source = new Image(input, Color.WHITE);

            new File("./genetic_images").mkdirs();

            final int descendants = 15;
            final int retention = 10;

            //final long nbRules = (long) descendants * retention * source.getHeight() * source.getWidth();

            SampleMutator mutator = new SampleMutator(source.getWidth(),source.getHeight(),50);

            Generation generation = new Generation(descendants, retention);
            generation.populate(source,10);

            System.out.println("====================================");

            for(int i = 0; i < NB_GENERATIONS; i++) {

                generation = generation.computeNext(source, Color.BLACK, mutator);

                //if(i % 10 == 0) {

                    System.out.println(generation.infos(i));

                    Image canvas = new Image(source.getWidth(), source.getHeight(), Color.WHITE);
                    DslPixel.PixelRule bestRule = DslPixel.when(generation.getBestRule().getStepCondition()).validate();
                    canvas.drawRule(bestRule, Color.BLUE);

                    canvas.snapshot(new File(String.format("./genetic_images/gen_%03d.png", i)));

                    System.out.println(bestRule.readable());
                //}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

