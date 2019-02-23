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
package io.doov.core.dsl.meta;

public class ComposeOperator implements Operator {
    private final Operator operator;
    private final LeafMetadata<?> other;

    public ComposeOperator(Operator operator, LeafMetadata<?> other) {
        this.operator = operator;
        this.other = other;
    }

    @Override
    public String readable() {
        return  operator.readable() + " " + other.readable();
    }

    @Override
    public String name() {
        return operator.name();
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.OTHER;
    }
}
