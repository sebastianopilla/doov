/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.temporal;

import java.time.LocalDateTime;

import io.doov.core.dsl.time.TemporalAdjuster;
import io.doov.core.grammar.application.Apply2;
import io.doov.core.grammar.value.Value;

public class WithAdjuster extends Apply2<LocalDateTime,LocalDateTime, TemporalAdjuster> {

    public WithAdjuster(Value<LocalDateTime> lhs, Value<TemporalAdjuster> rhs) {
        super(LocalDateTime.class, lhs, rhs);
    }

    @Override
    public String js(String obj) {
        return null;
    }
}
