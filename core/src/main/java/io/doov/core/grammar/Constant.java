/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar;

public class Constant<T> extends Value<T> {

    public final T value;

    public Constant(T value) {
        this.value = value;
    }
}
