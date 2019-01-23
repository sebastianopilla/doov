/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic;

import java.util.Objects;

import io.doov.core.dsl.lang.StepCondition;

public class ScoredRule implements Comparable<ScoredRule>{

    private StepCondition stepCondition;
    private Integer score;

    public ScoredRule(StepCondition stepCondition, Integer score) {
        this.stepCondition = stepCondition;
        this.score = score;
    }

    @Override
    public int compareTo(ScoredRule o) {
        return this.score.compareTo(o.score);
    }

    public StepCondition getStepCondition() {
        return stepCondition;
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "ScoredRule{" +
                "stepCondition=" + stepCondition +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ScoredRule))
            return false;
        ScoredRule that = (ScoredRule) o;
        return score == that.score &&
                stepCondition.equals(that.stepCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepCondition, score);
    }
}
