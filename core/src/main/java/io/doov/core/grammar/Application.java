/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar;

public class Application<T,S> extends Value<T>{
    public final Value<S> subject;

    public Application(Value<S> subject) {
        this.subject = subject;
    }
}
