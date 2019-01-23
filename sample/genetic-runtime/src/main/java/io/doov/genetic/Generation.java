/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic;

import java.util.*;
import java.util.stream.*;

import io.doov.core.dsl.lang.StepCondition;
import io.doov.genetic.field.dsl.DslPixel;
import io.doov.genetic.model.Color;

public class Generation {

    private List<ScoredRule> individuals;

    private int descendants;
    private int retention;
    private int size;

    public Generation(int descendants, int retention) {
        this.descendants = descendants;
        this.retention = retention;
        this.size = descendants * retention;
        this.individuals = new ArrayList<>(size);
    }

    public Generation(int descendants, int retention, List<ScoredRule> individuals) {
        this.descendants = descendants;
        this.retention = retention;
        this.size = descendants * retention;
        this.individuals = individuals;
    }

    public List<ScoredRule> getIndividuals() {
        return individuals;
    }

    public void populate(Image image, int pad) {
        this.individuals = IntStream.range(0,this.size)
                .mapToObj(i -> MutationUtils.randomSquare(image.getWidth(),image.getHeight(),50))
                .map(rule -> new ScoredRule(rule,0))
                .collect(Collectors.toList());
    }

    public double getMeanScore() {
        return (int) individuals.stream().mapToDouble(ScoredRule::getScore).average().orElse(0d);
    }

    public double getBestScore() {
        return individuals.stream().mapToDouble(ScoredRule::getScore).max().orElse(0);
    }

    public ScoredRule getBestRule() {
        individuals.sort(ScoredRule::compareTo);
        return individuals.get(0);
    }

    public String infos(int index) {
        return String.format("Generation %3d : BEST %8.2f MEAN %8.2f",
                index + 1,
                getBestScore(),
                getMeanScore()
        );
    }

    public Generation computeNext(Image image, Color mask, IMutator mutator) throws Exception {

        // Evaluation step
        List<ScoredRule> newScores = individuals.stream().parallel()
                .map(rule -> new ScoredRule(
                        rule.getStepCondition(),
                        image.evaluate(DslPixel.when(rule.getStepCondition()).validate(), mask)))
                .collect(Collectors.toList());

        // Selection Step
        newScores.sort(ScoredRule::compareTo);
        List<ScoredRule> selected = newScores.subList(
                newScores.size() - 1 - retention,
                newScores.size() - 1
        );

        // Mutation Step
        List<StepCondition> candidates = new ArrayList<>(descendants * retention);
        for(ScoredRule rule : selected) {
            candidates.addAll(Mutator.mutate(rule.getStepCondition(),mutator,this.descendants));
        }

        List<ScoredRule> nextgen = candidates.stream()
                .map(it -> new ScoredRule(it,0))
                .collect(Collectors.toList());

        nextgen.addAll(selected);

        return new Generation(this.descendants, this.retention, nextgen);
    }
}
