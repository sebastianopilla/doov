/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.lang;

import io.doov.core.FieldModel;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.mapping.builder.SimpleStepMap;

/**
 * Mapping input
 *
 * @param <T> input value type
 */
public interface MappingInput<T> extends DSLBuilder, SimpleStepMap<T> {

    /**
     * Reads the input value
     *
     * @param inModel model
     * @param context context
     * @return input value
     */
    T read(DslModel inModel, Context context);

    /**
     * Verifies the input for given in model
     *
     * @param inModel in model
     * @return true if the input can read a value from the model
     */
    boolean validate(FieldModel inModel);

    /**
     * Give us access to SimpleStepMap methods
     * @return itself
     */
    default MappingInput<T> input() {
        return this;
    }
}
