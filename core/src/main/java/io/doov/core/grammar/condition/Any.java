/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.condition;

import java.util.Arrays;
import java.util.stream.Collectors;

import io.doov.core.grammar.application.ApplyN;
import io.doov.core.grammar.value.Value;

public class Any extends ApplyN<Boolean,Boolean> {

    public Any(Value<Boolean>[] inputs) {
        super(Boolean.class,inputs);
    }

    @Override
    public String js(String obj) {
        return Arrays.stream(inputs)
                .map(v -> "(" + v.js(obj) + ")")
                .collect(Collectors.joining(" || "));
    }
}
