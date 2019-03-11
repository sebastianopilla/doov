/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.temporal;

import java.time.temporal.Temporal;

import io.doov.core.grammar.application.Apply2;
import io.doov.core.grammar.value.Value;

public class YearsBetween extends Apply2<Integer,Temporal,Temporal> {

    public YearsBetween(Value<Temporal> lhs, Value<Temporal> rhs) {
        super(Integer.class, lhs, rhs);
    }

    @Override
    public String js(String obj) {
        return null;
    }
}
