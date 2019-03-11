/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.grammar;

import java.time.LocalDateTime;

import io.doov.core.dsl.field.types.*;
import io.doov.core.dsl.runtime.GenericModel;
import io.doov.core.grammar.condition.*;
import io.doov.core.grammar.number.*;
import io.doov.core.grammar.string.Contains;
import io.doov.core.grammar.value.*;

public class Example {

    public static final void main(String... args) {

        GenericModel model = new GenericModel();

        IntegerFieldInfo i1 = model.intField(1,"i1");
        IntegerFieldInfo i2 = model.intField(1,"i2");

        StringFieldInfo str = model.stringField("THE_STRING","thestring");

        LocalDateTimeFieldInfo ldt = model.localDateTimeField(LocalDateTime.now(),"ldt");

        Value<Boolean> ast =
                new And(
                        new Contains(new FieldValue<>(str),new Constant<>("yoloswg")),
                        new Or(
                                new Not(new LessThan(new FieldValue<>(i1),new Constant<>(132))),
                                new Not(new MoreThan(new FieldValue<>(i2),new Constant<>(132)))
                        )
                );
        System.out.println(new Validation(ast).js());
    }
}
