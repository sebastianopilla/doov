/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.compiler;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.meta.Element;
import io.doov.core.dsl.meta.ElementType;

public class SimpleNode implements CompileTree {

    private BiPredicate<DslModel, Context> predicate;

    public SimpleNode(Predicate<DslModel> predicate) {
        this.predicate = (dsl,ctx) -> predicate.test(dsl);
    }

    @Override
    public BiPredicate<DslModel,Context> compile() {
        return this.predicate;
    }
}
