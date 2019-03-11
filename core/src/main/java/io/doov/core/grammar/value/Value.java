/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.value;

public abstract class Value<T> {

    public final Class<T> type;

    protected Value(Class<T> type) {
        this.type = type;
    }

    public abstract String js(String obj);
}
