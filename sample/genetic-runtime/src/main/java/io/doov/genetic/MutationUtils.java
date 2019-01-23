/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic;

import java.util.Optional;
import java.util.Random;

import io.doov.compiler.Extractors;
import io.doov.core.FieldInfo;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.predicate.BinaryPredicateMetadata;
import io.doov.core.dsl.meta.predicate.LeafPredicateMetadata;
import io.doov.genetic.field.dsl.DslPixel;

public class MutationUtils {

    public static StepCondition randomConst(int maxX, int maxY) {

        Random random = new Random();

        int constX = random.nextInt(maxX);
        int constY = random.nextInt(maxY);

        if(random.nextBoolean()) {
            if(random.nextBoolean()) {
                return DslPixel.x.greaterThan(constX);
            }
            else {
                return DslPixel.x.lesserThan(constX);
            }
        } else {
            if(random.nextBoolean()) {
                return DslPixel.y.greaterThan(constY);
            }
            else {
                return DslPixel.y.lesserThan(constY);
            }
        }
    }

    public static StepCondition randomSquare(int maxX, int maxY, int side) {

        Random random = new Random();

        int constX = random.nextInt(maxX - side);
        int constY = random.nextInt(maxY - side);

        int yUBound = constY + side / 2;
        int yLBound = constY - side / 2;

        int xUBound = constX + side / 2;
        int xLBound = constX - side / 2;

        return DslPixel.x.between(xLBound,xUBound).and(DslPixel.y.between(yLBound,yUBound));
    }

    public static boolean validateAll(boolean... args) {
        for(boolean b : args) {
            if (!b) return false;
        }
        return true;
    }

    // And that's why, kids, we invented Scala
    public static boolean isSquare(Metadata metadata) {

        if(!(metadata instanceof BinaryPredicateMetadata)) {
            return false;
        }

        BinaryPredicateMetadata node = (BinaryPredicateMetadata) metadata;

        if(!validateAll(
                node.getRight() instanceof BinaryPredicateMetadata,
                node.getLeft()  instanceof BinaryPredicateMetadata
        )) return false;

        BinaryPredicateMetadata lhs = (BinaryPredicateMetadata) node.getLeft();
        BinaryPredicateMetadata rhs = (BinaryPredicateMetadata) node.getRight();

        if(!validateAll(
                lhs.getLeft()  instanceof LeafPredicateMetadata,
                lhs.getRight() instanceof LeafPredicateMetadata,
                rhs.getLeft()  instanceof LeafPredicateMetadata,
                rhs.getRight() instanceof LeafPredicateMetadata
        )) return false;

        Optional<FieldInfo> fieldLL = Extractors.extractFieldInfo(((LeafPredicateMetadata) lhs.getLeft()).elementsAsList());
        Optional<FieldInfo> fieldLR = Extractors.extractFieldInfo(((LeafPredicateMetadata) lhs.getRight()).elementsAsList());
        Optional<FieldInfo> fieldRL = Extractors.extractFieldInfo(((LeafPredicateMetadata) rhs.getLeft()).elementsAsList());
        Optional<FieldInfo> fieldRR = Extractors.extractFieldInfo(((LeafPredicateMetadata) rhs.getRight()).elementsAsList());

        if(!validateAll(
                fieldLL.get().id().code().equals(fieldLR.get().id().code()),
                fieldRL.get().id().code().equals(fieldRR.get().id().code())
        )) return false;

        return true;
    }

}
