/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.number;

import io.doov.core.grammar.application.Apply2;
import io.doov.core.grammar.value.Value;

public class Divide extends Apply2<Integer,Integer,Integer> {

    public Divide(Value<Integer> lhs, Value<Integer> rhs) {
        super(Integer.class, lhs, rhs);
    }

    @Override
    public String js(String obj) {
        return String.format("(%s) / (%s)", lhs.js(obj), rhs.js(obj));
    }
}
