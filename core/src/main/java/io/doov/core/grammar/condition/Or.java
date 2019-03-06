/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.condition;

import io.doov.core.grammar.Value;

public class Or extends Value<Boolean> {

    public final Value<Boolean> lhs;
    public final Value<Boolean> rhs;

    public Or(Value<Boolean> lhs, Value<Boolean> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
