/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.condition;

import io.doov.core.grammar.Node;
import io.doov.core.grammar.Value;

public class Not extends Node {
    public final Value<Boolean> input;

    public Not(Value<Boolean> input) {
        this.input = input;
    }
}
