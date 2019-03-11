/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.application;

import io.doov.core.grammar.value.Value;

public abstract class Apply3<T,U,V,W> extends Value<T> {
    public final Value<U> param1;
    public final Value<V> param2;
    public final Value<W> param3;
    public final Class<T> output;

    protected Apply3(Value<U> param1, Value<V> param2, Value<W> param3, Class<T> output) {
        super(output);
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.output = output;
    }
}
