/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.number;

import io.doov.core.grammar.application.Apply2;
import io.doov.core.grammar.value.Value;

public class LessThan extends Apply2<Boolean, Integer, Integer> {

    public LessThan(Value<Integer> lhs, Value<Integer> rhs) {
        super(Boolean.class, lhs, rhs);
    }

    @Override
    public String js(String obj) {
        return lhs.js(obj) + " < " + rhs.js(obj);
    }
}
