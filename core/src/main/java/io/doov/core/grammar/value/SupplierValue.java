/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar.value;

import java.util.function.Supplier;

public class SupplierValue<T> extends Value<T> {
    public final Supplier<T> supplier;

    public SupplierValue(Class<T> type, Supplier<T> supplier) {
        super(type);
        this.supplier = supplier;
    }

    @Override
    public String js(String obj) {
        if(type.equals(String.class)) {
            return "\"" + supplier.get() + "\"";
        } else {
            return supplier.get().toString();
        }
    }
}
