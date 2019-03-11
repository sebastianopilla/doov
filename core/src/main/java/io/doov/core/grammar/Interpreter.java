/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar;

import java.util.Optional;
import java.util.function.*;

interface Interpreter<S,E> extends BiFunction<Function<E,S>,E, Optional<S>> {

     default Optional<S> run(Function<E, Optional<S>> callback, E node) {
        return this.apply(e -> callback.apply(e).get(),node);
    }

    static <S,E> Interpreter<S,E> combine(Class<S> tag, Interpreter<S,E> first, Interpreter<S,E> second) {
        return (next,it) -> {
            Optional<S> r1 = first.apply(next,it);
            Optional<S> r2 = second.apply(next,it);
            return r1.isPresent() ? r1 : r2;
        };
    }

    @SafeVarargs
    @SuppressWarnings("Unchecked")
    static <S,E> Interpreter<S,E> combineN(Class<S> tag, Interpreter<S,E>... cases) {
        Interpreter<S,E> folded = (next,it) -> Optional.empty();
        for(Interpreter<S,E> icase : cases) {
            folded = combine(tag,folded,icase);
        }
        return folded;
    }

    static <S,E,T extends E> Interpreter<S,E> caseOf(Class<T> caseClass, BiFunction<Function<E, S>, T, S> handle) {
        return (interpreter,node) -> {
            if(node.getClass().equals(caseClass)) {
                return Optional.of(handle.apply(interpreter,(T)node));
            } else {
                return Optional.empty();
            }
        };
    }

    static <S,E,T extends E> Interpreter<S,E> caseOf(Class<T> caseClass, Predicate<E> pred, BiFunction<Function<E, S>, T, S> handle) {
        return (interpreter,node) -> {
            if(node.getClass().equals(caseClass) && pred.test(node)) {
                return Optional.of(handle.apply(interpreter,(T)node));
            } else {
                return Optional.empty();
            }
        };
    }
}
