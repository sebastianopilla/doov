/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.mapping.model;

import static io.doov.core.dsl.DOOV.map;
import static io.doov.core.dsl.DOOV.mappings;
import static io.doov.core.dsl.mapping.TypeConverters.converter;
import static io.doov.core.dsl.mapping.TypeConverters.counter;

import java.util.stream.Stream;

import io.doov.core.dsl.DOOV;
import io.doov.core.dsl.field.types.IntegerFieldInfo;
import io.doov.core.dsl.field.types.StringFieldInfo;
import io.doov.core.dsl.lang.MappingRule;
import io.doov.core.dsl.lang.TypeConverter;
import io.doov.core.dsl.runtime.GenericModel;

public class SampleModel {

    private final MappingRule mappingRule1;
    private final MappingRule mappingRule2;
    private final MappingRule mappingRule5;
    private final MappingRule mappingRule3;
    private final MappingRule mappingRule4;
    private final MappingRule mappingRule6;
    private final MappingRule mappingRule7;
    private final MappingRule mappingRule8;
    private final MappingRule mappingRule9;
    private final MappingRule mappingRule10;

    public SampleModel() {
        GenericModel modelLF = new GenericModel();
        IntegerFieldInfo tauxLF = modelLF.intField(100, "tauxModeleFurets");
        IntegerFieldInfo tauxLF2 = modelLF.intField(100, "taux2ModeleFurets");
        IntegerFieldInfo tauxLF3 = modelLF.intField(100, "taux3ModeleFurets");
        StringFieldInfo nomConducteur = modelLF.stringField("Pierre", "driver nom");

        GenericModel modelI = new GenericModel();
        IntegerFieldInfo tauxI = modelI.intField(100, "tauxModeleInsurer");
        StringFieldInfo nomConducteurI = modelLF.stringField("Pierre", "conducteur nom");

        TypeConverter<Integer, Integer> integerToIntegerTypeConverter = converter(x -> x + 20, "taux converter");
        TypeConverter<Integer, Integer> integerToIntegerTypeConverter2 = converter(x -> x + 40, "taux converter 2");
        TypeConverter<String, Integer> stringToIntegerTypeConverter3 = converter((Integer::parseInt), "nom to taux");

        mappingRule1 = map(tauxLF).using(integerToIntegerTypeConverter).using(integerToIntegerTypeConverter2).to(tauxI);

        mappingRule2 = map(nomConducteur).to(nomConducteurI);

        mappingRule5 = map(nomConducteur).using(stringToIntegerTypeConverter3).to(tauxI);

        mappingRule3 = DOOV.when(tauxLF.isNull().and(nomConducteur.isNull())).then(
                mappings(
                        mappingRule2,
                        DOOV.when(nomConducteur.allMatch("BUBU", "DUMM")).then(
                                mappingRule2
                        ).otherwise(
                                mappingRule5
                        )
                )
        ).otherwise(
                mappingRule5
        );

        /*TODO Voir si pertinent
        map(map(tauxLF).to(tauxI))
                .using(converter(mappingRule -> 100, "hack"))
                .to(tauxLF);*/

        mappingRule4 = map(tauxLF, tauxLF2, tauxLF3).using(counter("count taux converter")).to(tauxI);

        mappingRule6 = DOOV.when(tauxLF.isNull().and(nomConducteur.isNull())).then(
                mappings(
                        mappingRule2,
                        DOOV.when(nomConducteur.allMatch("BUBU", "DUMM")).then(
                                mappingRule2
                        )
                )
        );

        mappingRule7 = DOOV.when(tauxLF.isNull().and(nomConducteur.isNull())).then(
                DOOV.when(nomConducteur.allMatch("BUBU", "DUMM")).then(
                        mappingRule2
                ).otherwise(
                        mappingRule5,
                        mappingRule5
                )
        ).otherwise(
                mappingRule5
        );

        mappingRule8 = DOOV.when(tauxLF.isNull().and(nomConducteur.isNull())).then(
                mappingRule2
        ).otherwise(
                mappingRule5,
                mappingRule5
        );

        mappingRule10 = DOOV.when(tauxLF.isNull().and(nomConducteur.isNull())).then(
                mappingRule2
        ).otherwise(
                DOOV.when(nomConducteur.isNotNull()).then(
                        mappingRule5
                )
        );

        mappingRule9 = DOOV.when(tauxLF.isNull().and(nomConducteur.isNull())).then(
                mappingRule2
        ).otherwise(
                DOOV.when(nomConducteur.isNotNull()).then(
                        mappingRule5,
                        mappingRule8
                )
        );
    }

    public Stream<MappingRule> mappingRules() {
        return Stream.of(
                mappingRule1,
                mappingRule2,
                mappingRule3,
                mappingRule4,
                mappingRule5,
                mappingRule6,
                mappingRule7,
                mappingRule8,
                mappingRule9,
                mappingRule10

        );
    }

    public MappingRule getMappingRule1() {
        return mappingRule1;
    }

    public MappingRule getMappingRule2() {
        return mappingRule2;
    }

    public MappingRule getMappingRule5() {
        return mappingRule5;
    }

    public MappingRule getMappingRule3() {
        return mappingRule3;
    }

    public MappingRule getMappingRule4() {
        return mappingRule4;
    }

    public MappingRule getMappingRule6() {
        return mappingRule6;
    }

    public MappingRule getMappingRule7() {
        return mappingRule7;
    }

    public MappingRule getMappingRule8() {
        return mappingRule8;
    }

    public MappingRule getMappingRule9() {
        return mappingRule9;
    }

    public MappingRule getMappingRule10() {
        return mappingRule10;
    }
}
