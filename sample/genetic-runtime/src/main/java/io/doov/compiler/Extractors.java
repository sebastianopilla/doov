/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.compiler;

import java.util.List;
import java.util.Optional;

import io.doov.core.FieldInfo;
import io.doov.core.dsl.meta.*;

public class Extractors {

    public static Optional<FieldInfo> extractFieldInfo(List<Element> elements) {
        for(Element e : elements) {
            if(e.getType() == ElementType.FIELD) {
                return Optional.of((FieldInfo) e.getReadable());
            }
        }
        return Optional.empty();
    }

    public static Optional<DefaultOperator> extractOperator(List<Element> elements) {
        for(Element e : elements) {
            if(e.getType() == ElementType.OPERATOR) {
                return Optional.of((DefaultOperator) e.getReadable());
            }
        }
        return Optional.empty();
    }

    public static Optional<String> extractValue(List<Element> elements) {
        for(Element e : elements) {
            if(e.getType() == ElementType.VALUE) {
                return Optional.of(e.getReadable().readable());
            }
        }
        return Optional.empty();
    }

}
