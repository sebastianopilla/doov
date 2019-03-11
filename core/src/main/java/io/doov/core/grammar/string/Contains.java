/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.string;

import static io.doov.core.grammar.string.RegexUtils.formatRegexp;

import io.doov.core.grammar.application.Apply2;
import io.doov.core.grammar.value.Constant;
import io.doov.core.grammar.value.Value;

public class Contains extends Apply2<Boolean,String,String> {

    public final Constant<String> pattern;

    public Contains(Value<String> lhs, Constant<String> rhs) {
        super(Boolean.class, lhs, rhs);
        this.pattern = rhs;
    }

    @Override
    public String js(String obj) {
        return "(" + lhs.js(obj) + ").match(/" + formatRegexp(pattern.value) + "/)";
    }
}
