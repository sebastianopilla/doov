/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.doov.core.dsl.meta.function;

import static io.doov.core.dsl.meta.DefaultOperator.*;
import static io.doov.core.dsl.meta.ElementType.TEMPORAL_UNIT;
import static io.doov.core.dsl.meta.MetadataType.LEAF_VALUE;

import java.util.concurrent.atomic.AtomicInteger;

import io.doov.core.dsl.meta.Element;
import io.doov.core.dsl.meta.LeafMetadata;
import io.doov.core.dsl.meta.MetadataType;
import io.doov.core.dsl.meta.predicate.PredicateMetadata;

public class TemporalFunctionMetadata extends LeafMetadata<TemporalFunctionMetadata> implements PredicateMetadata {

    private final AtomicInteger evalTrue = new AtomicInteger();
    private final AtomicInteger evalFalse = new AtomicInteger();

    public TemporalFunctionMetadata(MetadataType type) {
        super(type);
    }

    @Override
    public AtomicInteger evalTrue() {
        return evalTrue;
    }

    @Override
    public AtomicInteger evalFalse() {
        return evalFalse;
    }

    TemporalFunctionMetadata temporalUnit(Object unit) {
        return add(unit == null ? null : new Element(() -> unit.toString().toLowerCase(), TEMPORAL_UNIT));
    }

    // local date suppliers

    public static TemporalFunctionMetadata todayMetadata() {
        return new TemporalFunctionMetadata(LEAF_VALUE).operator(today);
    }

    public static TemporalFunctionMetadata todayPlusMetadata(int value, Object unit) {
        return new TemporalFunctionMetadata(LEAF_VALUE).operator(today_plus).valueObject(value).temporalUnit(unit);
    }

    public static TemporalFunctionMetadata todayMinusMetadata(int value, Object unit) {
        return new TemporalFunctionMetadata(LEAF_VALUE).operator(today_minus).valueObject(value).temporalUnit(unit);
    }

    public static TemporalFunctionMetadata firstDayOfThisMonthMetadata() {
        return new TemporalFunctionMetadata(LEAF_VALUE).operator(first_day_of_this_month);
    }

    public static TemporalFunctionMetadata firstDayOfThisYearMetadata() {
        return new TemporalFunctionMetadata(LEAF_VALUE).operator(first_day_of_this_year);
    }

    public static TemporalFunctionMetadata lastDayOfThisMonthMetadata() {
        return new TemporalFunctionMetadata(LEAF_VALUE).operator(last_day_of_this_month);
    }

    public static TemporalFunctionMetadata lastDayOfThisYearMetadata() {
        return new TemporalFunctionMetadata(LEAF_VALUE).operator(last_day_of_this_year);
    }

    public static TemporalFunctionMetadata dateMetadata(Object date) {
        return new TemporalFunctionMetadata(LEAF_VALUE).valueString(date.toString());
    }

}
