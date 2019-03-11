/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.temporal;

import java.time.temporal.Temporal;

import io.doov.core.grammar.application.Apply2;
import io.doov.core.grammar.value.Value;

public class AgeAt<T extends Temporal> extends Apply2<T,T,T> {

    public AgeAt(Value<T> lhs, Value<T> rhs) {
        super((Class<T>) lhs.getClass(), lhs, rhs);
    }

    @Override
    public String js(String obj) {
        return "Math.round(Math.abs(moment(" + lhs.js(obj) + ").diff(moment(" + rhs.js(obj) + "), \'years\')))";
    }
}
