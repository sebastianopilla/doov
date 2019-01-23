/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic;

import java.util.Random;

import io.doov.core.dsl.meta.DefaultOperator;
import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.predicate.BinaryPredicateMetadata;
import io.doov.core.dsl.meta.predicate.LeafPredicateMetadata;

public class SampleMutator implements IMutator {

    private int maxX;
    private int maxY;
    private int weight;
    private Random random;

    public SampleMutator(int maxX, int maxY, int weight) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.weight = weight;
        this.random = new Random();
    }

    // random number between 1 and 100
    public int diceCast() {
        return 1 + random.nextInt(99);
    }

    @Override
    public Metadata onLeaf(LeafPredicateMetadata from, int depth) {
        return from;
    }

    @Override
    public Metadata onNode(BinaryPredicateMetadata from, int depth) {
        if(MutationUtils.isSquare(from) && diceCast() < weight) {

            Metadata leaf = MutationUtils.randomSquare(this.maxX,this.maxY,50).metadata();

            // Balance the tree
            if(random.nextBoolean()) {
                return new BinaryPredicateMetadata(from, DefaultOperator.or, leaf);
            } else {
                return new BinaryPredicateMetadata(leaf, DefaultOperator.or, from);
            }
        } else {
            return from;
        }
    }
}
