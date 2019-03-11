/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar;

import io.doov.core.grammar.value.Value;

public class Validation {

    public final Value<Boolean> value;

    public Validation(Value<Boolean> value) {
        this.value = value;
    }

    public String js() {
        return "input => " + value.js("input");
    }
}
