/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.application;

import io.doov.core.grammar.value.Value;

public abstract class Apply2<T,U,V> extends Value<T> {
    public final Value<U> lhs;
    public final Value<V> rhs;
    public final Class<T> output;

    public Apply2(Class<T> output, Value<U> lhs, Value<V> rhs) {
        super(output);
        this.lhs = lhs;
        this.rhs = rhs;
        this.output = output;
    }
}
