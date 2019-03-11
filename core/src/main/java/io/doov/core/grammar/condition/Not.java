/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.condition;

import io.doov.core.grammar.application.Apply1;
import io.doov.core.grammar.value.Value;

public class Not extends Apply1<Boolean,Boolean> {

    public Not(Value<Boolean> input) {
        super(Boolean.class,input);
    }

    @Override
    public String js(String obj) {
        return String.format("!(%s)",input.js(obj));
    }
}
