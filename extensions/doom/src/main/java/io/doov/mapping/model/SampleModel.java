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
import io.doov.core.dsl.field.types.*;
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
    private final MappingRule mappingRuleFieldByField;

    private final GenericModel modelLF;
    private final GenericModel modelI;

    public SampleModel() {
        modelLF = new GenericModel();
        IntegerFieldInfo tauxLF = modelLF.intField(100, "tauxModeleFurets");
        IntegerFieldInfo tauxLF2 = modelLF.intField(100, "taux2ModeleFurets");
        IntegerFieldInfo tauxLF3 = modelLF.intField(100, "taux3ModeleFurets");
        StringFieldInfo nomConducteur = modelLF.stringField("Pierre", "driver nom");
        StringFieldInfo sexeConducteur = modelLF.stringField("H", "driver sexe");
        StringFieldInfo dateNaissConducteur = modelLF.stringField("22/01/1980", "driver birthday");
        StringFieldInfo datePermisConducteur = modelLF.stringField("22/01/1992", "driver date permis");
        StringFieldInfo professionConducteur = modelLF.stringField("Forain", "driver profession");
        StringFieldInfo typeAdresse = modelLF.stringField("150 rue", "driver adresse");
        StringFieldInfo vehiculeMarque = modelLF.stringField("BMW", "voiture marque");
        StringFieldInfo vehiculeEtat = modelLF.stringField("Neuf", "voiture état");
        StringFieldInfo vehiculeDateAchat = modelLF.stringField("Neuf", "voiture date achat");
        StringFieldInfo vehiculeGarage = modelLF.stringField("Nangis", "voiture garage");
        BooleanFieldInfo controlePositifStupefiant = modelLF.booleanField(false, "controle positif stupefiant");
        BooleanFieldInfo controlePositifAlcoolemie = modelLF.booleanField(false, "controle positif alcoolemie");
        IntegerFieldInfo nombreCondamnation = modelLF.intField(100, "nombre condamnation");
        IntegerFieldInfo nombreEnfant = modelLF.intField(100, "driver nombre enfant");
        StringFieldInfo dateNaissEnfant1 = modelLF.stringField("22/01/2000", "driver enfant 1 birthday");
        StringFieldInfo dateNaissEnfant2 = modelLF.stringField("22/01/2001", "driver enfant 2 birthday");
        StringFieldInfo dateNaissEnfant3 = modelLF.stringField("22/01/2002", "driver enfant 3 birthday");
        StringFieldInfo dateNaissEnfant4 = modelLF.stringField("22/01/2003", "driver enfant 4 birthday");
        StringFieldInfo dateNaissEnfant5 = modelLF.stringField("22/01/2004", "driver enfant 5 birthday");
        StringFieldInfo sinistreType1 = modelLF.stringField("tiers 1", "sinistre type 1");
        StringFieldInfo sinistreType2 = modelLF.stringField("tiers 2", "sinistre type 2");
        StringFieldInfo sinistreType3 = modelLF.stringField("tiers 3", "sinistre type 3");
        StringFieldInfo sinistreType4 = modelLF.stringField("tiers 4", "sinistre type 4");
        StringFieldInfo sinistreType5 = modelLF.stringField("tiers 5", "sinistre type 5");

        modelI = new GenericModel();
        IntegerFieldInfo tauxI = modelI.intField(100, "Insurer tauxModeleInsurer");
        StringFieldInfo nomConducteurI = modelI.stringField("Pierre", "Insurer conducteur nom");
        //
        StringFieldInfo sexeConducteurI = modelI.stringField("H", "Insurer driver sexe");
        StringFieldInfo dateNaissConducteurI = modelI.stringField("22/01/1980", "Insurer driver birthday");
        StringFieldInfo datePermisConducteurI = modelI.stringField("22/01/1992", "Insurer driver date permis");
        StringFieldInfo professionConducteurI = modelI.stringField("Forain", "Insurer driver profession");
        StringFieldInfo typeAdresseI = modelI.stringField("150 rue", "Insurer driver adresse");

        IntegerFieldInfo nombreCondamnationI = modelI.intField(100, "Insurer nombre condamnation");
        IntegerFieldInfo nombreEnfantI = modelI.intField(100, "Insurer driver nombre enfant");
        StringFieldInfo dateNaissEnfant1I = modelI.stringField("22/01/2000", "Insurer driver enfant 1 birthday");
        StringFieldInfo dateNaissEnfant2I = modelI.stringField("22/01/2001", "Insurer driver enfant 2 birthday");
        StringFieldInfo dateNaissEnfant3I = modelI.stringField("22/01/2002", "Insurer driver enfant 3 birthday");
        StringFieldInfo dateNaissEnfant4I = modelI.stringField("22/01/2003", "Insurer driver enfant 4 birthday");
        StringFieldInfo dateNaissEnfant5I = modelI.stringField("22/01/2004", "Insurer driver enfant 5 birthday");
        StringFieldInfo sinistreType1I = modelI.stringField("tiers 1", "Insurer sinistre type 1");
        StringFieldInfo sinistreType2I = modelI.stringField("tiers 2", "Insurer sinistre type 2");
        StringFieldInfo sinistreType3I = modelI.stringField("tiers 3", "Insurer sinistre type 3");
        StringFieldInfo sinistreType4I = modelI.stringField("tiers 4", "Insurer sinistre type 4");
        StringFieldInfo sinistreType5I = modelI.stringField("tiers 5", "Insurer sinistre type 5");

        StringFieldInfo vehiculeMarqueI = modelI.stringField("BMW", "Insurer voiture marque");
        StringFieldInfo vehiculeEtatI = modelI.stringField("Neuf", "Insurer voiture état");
        StringFieldInfo vehiculeDateAchatI = modelI.stringField("Neuf", "Insurer voiture date achat");
        StringFieldInfo vehiculeGarageI = modelI.stringField("Nangis", "Insurer voiture garage");
        BooleanFieldInfo controlePositifStupefiantI = modelI.booleanField(false, "Insurer controle positif " +
                "stupefiant");
        BooleanFieldInfo controlePositifAlcoolemieI = modelI.booleanField(false, "Insurer controle positif " +
                "alcoolemie");

        TypeConverter<Integer, Integer> integerToIntegerTypeConverter = converter(x -> x + 20, "taux converter");
        TypeConverter<Integer, Integer> integerToIntegerTypeConverter2 = converter(x -> x + 40, "taux converter 2");
        TypeConverter<String, Integer> stringToIntegerTypeConverter3 = converter((Integer::parseInt), "nom to taux");

        mappingRule1 = map(tauxLF).using(integerToIntegerTypeConverter).using(integerToIntegerTypeConverter2).to(tauxI);

        mappingRule2 = map(nomConducteur).to(nomConducteurI);

        mappingRule5 = map(nomConducteur).using(stringToIntegerTypeConverter3).to(tauxI);

        mappingRule4 = map(tauxLF, tauxLF2, tauxLF3).using(counter("count taux converter")).to(tauxI);

        mappingRule3 = DOOV.when(tauxLF.isNull().and(nomConducteur.isNull())).then(
                mappings(
                        mappingRule2,
                        DOOV.when(nomConducteur.allMatch("BUBU", "DUMM")).then(
                                mappingRule1
                        ).otherwise(
                                mappingRule4
                        )
                )
        ).otherwise(
                mappingRule5
        );

        /*TODO Voir si pertinent
        map(map(tauxLF).to(tauxI))
                .using(converter(mappingRule -> 100, "hack"))
                .to(tauxLF);*/

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

        mappingRule9 = DOOV.when(tauxLF.isNull().and(nomConducteur.isNull())).then(
                mappingRule2
        ).otherwise(
                DOOV.when(nomConducteur.isNotNull()).then(
                        mappingRule5,
                        mappingRule8
                )
        );

        mappingRule10 = DOOV.when(tauxLF.isNull().and(nomConducteur.isNull())).then(
                mappingRule2
        ).otherwise(
                DOOV.when(nomConducteur.isNotNull()).then(
                        mappingRule5
                )
        );

        mappingRuleFieldByField = mappings(
                mappingRule1,
                mappingRule2,
                mappingRule3,
                map(sexeConducteur).to(sexeConducteurI),
                map(dateNaissConducteur).to(dateNaissConducteurI),
                map(datePermisConducteur).to(datePermisConducteurI),
                map(professionConducteur).to(professionConducteurI),
                map(typeAdresse).to(typeAdresseI),
                map(vehiculeMarque).to(vehiculeMarqueI),
                map(vehiculeEtat).to(vehiculeEtatI),
                map(vehiculeDateAchat).to(vehiculeDateAchatI),
                map(vehiculeGarage).to(vehiculeGarageI),
                map(controlePositifStupefiant).to(controlePositifStupefiantI),
                map(controlePositifAlcoolemie).to(controlePositifAlcoolemieI),
                map(nombreCondamnation).to(nombreCondamnationI),
                map(nombreEnfant).to(nombreEnfantI),
                map(dateNaissEnfant1).to(dateNaissEnfant1I),
                map(dateNaissEnfant2).to(dateNaissEnfant2I),
                map(dateNaissEnfant3).to(dateNaissEnfant3I),
                map(dateNaissEnfant4).to(dateNaissEnfant4I),
                map(dateNaissEnfant5).to(dateNaissEnfant5I),
                map(sinistreType1).to(sinistreType1I),
                map(sinistreType2).to(sinistreType2I),
                map(sinistreType3).to(sinistreType3I),
                map(sinistreType4).to(sinistreType4I),
                map(sinistreType5).to(sinistreType5I)
        );
    }

    //TODO a remove
    public static void main(String[] args) {
        Stream.of("sexeConducteur",
                "dateNaissConducteur",
                "datePermisConducteur",
                "professionConducteur",
                "typeAdresse",
                "vehiculeMarque",
                "vehiculeEtat",
                "vehiculeDateAchat",
                "vehiculeGarage",
                "controlePositifStupefiant",
                "controlePositifAlcoolemie",
                "nombreCondamnation",
                "nombreEnfant",
                "dateNaissEnfant1",
                "dateNaissEnfant2",
                "dateNaissEnfant3",
                "dateNaissEnfant4",
                "dateNaissEnfant5",
                "sinistreType1",
                "sinistreType2",
                "sinistreType3",
                "sinistreType4",
                "sinistreType5").forEach(s -> System.out.println("map(" + s + ")" + ".to(" + s + "I),"));
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
                mappingRule10,
                mappingRuleFieldByField
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

    public MappingRule getMappingRuleFieldByField() {
        return mappingRuleFieldByField;
    }

    public GenericModel getModelLF() {
        return modelLF;
    }

    public GenericModel getModelI() {
        return modelI;
    }

}
