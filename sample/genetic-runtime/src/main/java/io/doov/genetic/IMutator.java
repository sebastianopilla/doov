/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic;

import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.predicate.BinaryPredicateMetadata;
import io.doov.core.dsl.meta.predicate.LeafPredicateMetadata;

public interface IMutator {

    Metadata onLeaf( LeafPredicateMetadata from, int depth);
    Metadata onNode( BinaryPredicateMetadata from, int depth);

}
