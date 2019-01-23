/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.symbolic;

import io.doov.core.dsl.meta.*;
import io.doov.core.dsl.meta.predicate.BinaryPredicateMetadata;

public interface Pattern<T extends Metadata> {

    boolean validate(T metadata);

    static Pattern<BinaryPredicateMetadata> ofOperator(Operator op, Pattern<BinaryPredicateMetadata> next) {
        return metadata -> metadata.getOperator().equals(op) && next.validate(metadata);
    }

    static Pattern<BinaryPredicateMetadata> and(Pattern<Metadata> lhs, Pattern<Metadata> rhs) {
        return Pattern.ofOperator(DefaultOperator.and,
                metadata -> lhs.validate(metadata.getLeft()) && rhs.validate(metadata.getRight())
        );
    }

    static Pattern<BinaryPredicateMetadata> or(Pattern<Metadata> lhs, Pattern<Metadata> rhs) {
        return Pattern.ofOperator(DefaultOperator.or,
                metadata -> lhs.validate(metadata.getLeft()) && rhs.validate(metadata.getRight())
        );
    }

    static Pattern<Metadata> alwaysTrue() {
        return metadata -> true;
    }

}
