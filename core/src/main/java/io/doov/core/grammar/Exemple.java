/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar;

import io.doov.core.grammar.application.*;

public class Exemple {
    static Node ast = new Validation(
            new Apply1<>(Boolean.class,new Plus(
                    new Constant<>(1),
                    new Constant<>(2)
            ))
    );
}
