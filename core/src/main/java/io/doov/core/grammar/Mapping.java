/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar;

import io.doov.core.grammar.value.FieldValue;
import io.doov.core.grammar.value.Value;

public class Mapping<T> {
    public final Value<T> input;
    public final FieldValue<T,?> output;

    public Mapping(Value<T> input, FieldValue<T, ?> output) {
        this.input = input;
        this.output = output;
    }

    public String js() {
        return "(in, out) => return " + output.js("in")+ " = " + input.js("out")+ ";";
    }
}
