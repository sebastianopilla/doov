/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.temporal;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

import io.doov.core.grammar.application.Apply2;
import io.doov.core.grammar.value.Value;

public class TimeBetween extends Apply2<Long,Temporal, ChronoUnit> {

    public TimeBetween(Value<Temporal> lhs, Value<ChronoUnit> rhs) {
        super(Long.class, lhs, rhs);
    }

    @Override
    public String js(String obj) {
        return null;
    }
}
