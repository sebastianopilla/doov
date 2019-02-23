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
package io.doov.core.dsl.mapping;

import static io.doov.core.dsl.meta.MappingInputMetadata.inputMetadata;
import static io.doov.core.dsl.meta.MappingMetadata.metadataInput;

import io.doov.core.FieldModel;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.*;
import io.doov.core.dsl.meta.MappingInputMetadata;
import io.doov.core.dsl.meta.Metadata;

public class BiConverterInput<U, S, T> extends AbstractDSLBuilder implements MappingInput<T> {

    private final MappingInputMetadata metadata;
    private final MappingInput<U> mappingInput1;
    private final MappingInput<S> mappingInput2;
    private final BiTypeConverter<U, S, T> converter;

    public BiConverterInput(MappingInput<U> mappingInput1, MappingInput<S> mappingInput2,
                    BiTypeConverter<U, S, T> converter) {
        this.mappingInput1 = mappingInput1;
        this.mappingInput2 = mappingInput2;
        this.converter = converter;
        this.metadata = inputMetadata(metadataInput(mappingInput1.metadata(), mappingInput2.metadata()), converter.metadata());
    }

    @Override
    public boolean validate(FieldModel inModel) {
        return mappingInput1.validate(inModel) && mappingInput2.validate(inModel);
    }

    @Override
    public Metadata metadata() {
        return metadata;
    }

    @Override
    public T read(DslModel inModel, Context context) {
        return converter.convert(inModel, context, mappingInput1.read(inModel, context),
                        mappingInput2.read(inModel, context));
    }

}
