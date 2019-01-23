/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.compiler;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import io.doov.core.FieldInfo;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.field.types.IntegerFieldInfo;
import io.doov.core.dsl.meta.*;
import io.doov.core.dsl.meta.predicate.BinaryPredicateMetadata;
import io.doov.core.dsl.meta.predicate.LeafPredicateMetadata;

public class Compiler {

    public static CompileTree compileBinaryPredicate(BinaryPredicateMetadata binary) throws Exception {
        CompileTree lhs = compile(binary.getLeft());
        CompileTree rhs = compile(binary.getRight());
        switch (binary.getOperator().toString()) {
            case "and":
                return new And(lhs, rhs);
            case "or":
                return new Or(lhs, rhs);
            default:
                throw new Exception("Incorrect binary operator");
        }
    }

    public static CompileTree compileLeafPredicate(LeafPredicateMetadata leaf) throws Exception {
        List<Element> elements = leaf.elementsAsList();

        Optional<FieldInfo> infos = Extractors.extractFieldInfo(elements);
        Optional<DefaultOperator> operator = Extractors.extractOperator(elements);
        Optional<String> value = Extractors.extractValue(elements);

        if (infos.isPresent() && operator.isPresent() && value.isPresent()) {
            if (infos.get().getClass().equals(IntegerFieldInfo.class)) {
                Function<DslModel, Integer> extractor = x -> x.get(infos.get().id());
                switch (operator.get()) {
                    case equals:
                        return new SimpleNode(x -> extractor.apply(x) == Integer.valueOf(value.get()));
                    case not_equals:
                        return new SimpleNode(x -> extractor.apply(x) != Integer.valueOf(value.get()));
                    case is_null:
                        return new SimpleNode(x -> extractor.apply(x) == null);
                    case is_not_null:
                        return new SimpleNode(x -> extractor.apply(x) != null);
                    case greater_than:
                        return new SimpleNode(x -> extractor.apply(x) > Integer.valueOf(value.get()));
                    case greater_or_equals:
                        return new SimpleNode(x -> extractor.apply(x) >= Integer.valueOf(value.get()));
                    case lesser_than:
                        return new SimpleNode(x -> extractor.apply(x) < Integer.valueOf(value.get()));
                    case lesser_or_equals:
                        return new SimpleNode(x -> extractor.apply(x) <= Integer.valueOf(value.get()));
                    default:
                        throw new Exception(String.format("Can't match operator %s with field %s", operator.get(), infos.get()));
                }
            }
            throw new Exception("Unexpected data type " + infos.get().getClass().toString());
        }
        throw new Exception("Operator, fieldinfo or value not present");
    }

    public static CompileTree compile(Metadata metadata) throws Exception {
        switch (metadata.type()) {
            case BINARY_PREDICATE:
                return compileBinaryPredicate((BinaryPredicateMetadata) metadata);
            case FIELD_PREDICATE:
                return compileLeafPredicate((LeafPredicateMetadata) metadata);
            default:
                throw new Exception("Incorrect metadata type");
        }
    }

}
