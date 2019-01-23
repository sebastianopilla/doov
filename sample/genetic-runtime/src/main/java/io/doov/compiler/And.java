/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.compiler;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.meta.DefaultOperator;
import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.predicate.BinaryPredicateMetadata;

public class And implements CompileTree {

    private CompileTree lhs;
    private CompileTree rhs;

    public And(CompileTree lhs, CompileTree rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public BiPredicate<DslModel, Context> compile() {
        return lhs.compile().and(rhs.compile());
    }
}

