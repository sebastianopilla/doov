/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.temporal;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

import io.doov.core.grammar.application.Apply3;
import io.doov.core.grammar.value.Value;

public class TemporalMinus<T extends Temporal> extends Apply3<T,T,Integer,TemporalUnit> {

    protected TemporalMinus(Value<T> param1, Value<Integer> param2, Value<TemporalUnit> param3) {
        super(param1, param2, param3, (Class<T>) param1.getClass());
    }

    @Override
    public String js(String obj) {
        return null;
    }
}
