/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.application;

import io.doov.core.grammar.value.Value;

public abstract class ApplyN<T,S> extends Value<S> {
    public final Value<T>[] inputs;

    public ApplyN(Class<S> output, Value<T>[] inputs) {
        super(output);
        this.inputs = inputs;
    }
}
