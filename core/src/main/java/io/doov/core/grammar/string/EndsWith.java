/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.string;

import static io.doov.core.grammar.string.RegexUtils.formatRegexp;

import io.doov.core.grammar.application.Apply2;
import io.doov.core.grammar.value.Value;

public class EndsWith extends Apply2<Boolean,String,String> {

    public EndsWith(Value<String> lhs, Value<String> rhs) {
        super(Boolean.class, lhs, rhs);
    }

    @Override
    public String js(String obj) {
        return "(" + lhs.js(obj) + ").match(\\/" + formatRegexp(rhs.js(obj)) + "$\\/)";
    }
}
