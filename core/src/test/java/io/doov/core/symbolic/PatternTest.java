/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.symbolic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.doov.core.dsl.DOOV;
import io.doov.core.dsl.meta.Metadata;

public class PatternTest {

    @Test
    void treeTest() {
        Pattern pattern = Pattern.and(Pattern.alwaysTrue(),Pattern.alwaysTrue());

        Metadata metadata = DOOV.alwaysTrue().and(DOOV.alwaysTrue()).metadata();

        Assertions.assertThat(pattern.validate(metadata)).isEqualTo(true);
    }
}
