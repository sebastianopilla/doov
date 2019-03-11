/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.value;

public class Constant<T> extends Value<T> {

    public final T value;

    public Constant(T value) {
        super((Class<T>) value.getClass());
        this.value = value;
    }

    @Override
    public String js(String obj) {
        if(type.equals(String.class)) {
            return "\'" + value + "\'";
        } else {
            return value.toString();
        }
    }
}
