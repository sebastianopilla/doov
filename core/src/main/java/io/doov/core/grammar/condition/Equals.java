/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.condition;

import io.doov.core.grammar.application.Apply2;
import io.doov.core.grammar.value.Value;

public class Equals<T> extends Apply2<Boolean,T,T> {

    public Equals(Value<T> lhs, Value<T> rhs) {
        super(Boolean.class, lhs, rhs);
    }

    @Override
    public String js(String obj) {
        return String.format("(%s) === (%s)",lhs.js(obj),rhs.js(obj));
    }
}
