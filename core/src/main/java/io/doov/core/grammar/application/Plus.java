/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.application;

import io.doov.core.grammar.Value;

public class Plus extends Apply2<Integer,Integer,Integer>{

    public Plus(Value<Integer> lhs, Value<Integer> rhs) {
        super(Integer.class, lhs, rhs);
    }
}
