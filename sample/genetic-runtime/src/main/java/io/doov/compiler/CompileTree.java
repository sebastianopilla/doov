/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.compiler;

import java.util.function.BiPredicate;

import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.Context;

public interface CompileTree {
    BiPredicate<DslModel, Context> compile();
}
