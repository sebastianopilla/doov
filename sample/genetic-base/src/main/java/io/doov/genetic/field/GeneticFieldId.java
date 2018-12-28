/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic.field;

import io.doov.core.FieldId;

public enum GeneticFieldId implements FieldId {

    // Fields for pixel
    POSITION,COLOR,

    // Fields for position
    X, Y,

    // Fields for color
    RED, GREEN, BLUE;

    @Override
    public String code() {
        return this.name();
    }
}
