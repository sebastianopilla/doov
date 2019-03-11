/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.condition;

import io.doov.core.grammar.application.Apply2;
import io.doov.core.grammar.value.Value;

public class And extends Apply2<Boolean,Boolean,Boolean> {

    public And(Value<Boolean> lhs, Value<Boolean> rhs) {
        super(Boolean.class,lhs,rhs);
    }

    @Override
    public String js(String obj) {
        return String.format("(%s) && (%s)",lhs.js(obj),rhs.js(obj));
    }
}
