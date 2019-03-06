/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar;

public class Validation extends Node {
    public final Value<Boolean> rule;

    public Validation(Value<Boolean> rule) {
        this.rule = rule;
    }
}
