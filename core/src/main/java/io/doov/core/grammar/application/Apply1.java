/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.application;

import io.doov.core.grammar.value.Value;

public abstract class Apply1<T,S> extends Value<T> {
    public final Value<S> input;
    public final Class<T> output;

    public Apply1(Class<T> output, Value<S> input) {
        super(output);
        this.input = input;
        this.output = output;
    }
}
