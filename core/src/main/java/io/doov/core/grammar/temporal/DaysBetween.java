/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.temporal;

import java.time.temporal.Temporal;

import io.doov.core.grammar.application.Apply2;
import io.doov.core.grammar.value.Value;

public class DaysBetween<T extends Temporal> extends Apply2<Integer,T,T> {

    public DaysBetween(Value<T> lhs, Value<T> rhs) {
        super(Integer.class, lhs, rhs);
    }

    @Override
    public String js(String obj) {
        return null;
    }
}
