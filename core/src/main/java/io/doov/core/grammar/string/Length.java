/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.string;

import io.doov.core.grammar.application.Apply1;
import io.doov.core.grammar.value.Value;

public class Length extends Apply1<Integer,String> {

    public Length(Value<String> input) {
        super(Integer.class, input);
    }

    @Override
    public String js(String obj) {
        return input.js(obj) + ".length";
    }
}
