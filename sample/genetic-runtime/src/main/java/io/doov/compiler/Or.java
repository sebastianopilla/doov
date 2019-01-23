/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.compiler;

import java.util.function.BiPredicate;

import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.Context;

public class Or implements CompileTree {

    private CompileTree lhs;
    private CompileTree rhs;

    public Or(CompileTree lhs, CompileTree rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public BiPredicate<DslModel, Context> compile() {
        return lhs.compile().or(rhs.compile());
    }
}

