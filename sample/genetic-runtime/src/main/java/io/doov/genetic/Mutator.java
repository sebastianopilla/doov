/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic;

import java.util.*;
import java.util.function.BiPredicate;

import io.doov.compiler.Compiler;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.*;
import io.doov.core.dsl.meta.predicate.*;

public class Mutator {

    public static Metadata mutate(Metadata metadata, IMutator mutator) {
        return mutate(metadata,mutator,new Random(), 0);
    }

    public static Metadata mutate(Metadata metadata, IMutator mutator, Random random, int depth) {
        MetadataType type = metadata.type();
        switch (type) {
            case BINARY_PREDICATE:

                BinaryPredicateMetadata binary = (BinaryPredicateMetadata) metadata;

                Metadata lhs = mutate(binary.getLeft(),mutator);
                Metadata rhs = mutate(binary.getRight(),mutator);
                Operator ops = binary.getOperator();

                BinaryPredicateMetadata result = new BinaryPredicateMetadata(lhs, ops, rhs);
                return mutator.onNode(result,depth + 1);

            case FIELD_PREDICATE:

                LeafPredicateMetadata leaf = (LeafPredicateMetadata) metadata;
                return mutator.onLeaf(leaf,depth + 1);

            default:
                return metadata;
        }
    }

    public static StepCondition mutate(StepCondition from, IMutator mutator) throws Exception {
        Metadata mutated = mutate(from.metadata(), mutator);
        BiPredicate<DslModel, Context> compiled = Compiler.compile(mutated).compile();
        return new StepCondition() {
            @Override
            public BiPredicate<DslModel, Context> predicate() {
                return compiled;
            }

            @Override
            public Metadata metadata() {
                return mutated;
            }

            @Override
            public String toString() {
                return metadata().toString();
            }
        };
    }

    public static List<StepCondition> mutate(StepCondition from, IMutator mutator, int descendants) throws Exception {
        List<StepCondition> steps = new ArrayList<>(descendants);
        for(int i = 0; i < descendants; i++) {
            steps.add(mutate(from, mutator));
        }
        return steps;
    }
}
