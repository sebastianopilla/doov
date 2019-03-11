/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.value;

import io.doov.core.FieldInfo;
import io.doov.core.dsl.DslField;

public class FieldValue<S,T extends DslField<S> & FieldInfo> extends Value<S> {
    public final FieldInfo info;

    public FieldValue(T field) {
        super((Class<S>) field.type());
        info = field;
    }

    @Override
    public String js(String obj) {
        return obj + "." + info.readable();
    }
}
