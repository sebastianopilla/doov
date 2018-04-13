package io.doov.core.dsl.lang;

import java.util.Locale;

import io.doov.core.FieldModel;
import io.doov.core.dsl.DslField;
import io.doov.core.dsl.meta.SyntaxTree;

/**
 * Generic Type converter
 *
 * @param <O> out type
 */
public interface NaryTypeConverter<O> extends Readable, SyntaxTree {
    @Override
    default String readable() {
        return readable(Locale.getDefault());
    }

    /**
     * Returns the human readable version of this object.
     *
     * @return the readable string
     */
    String readable(Locale locale);

    /**
     * Convert the given in fields in the model to the value in type {@link O}
     *
     * @param fieldModel in model
     * @param ins        in fields
     * @return out value
     */
    O convert(FieldModel fieldModel, DslField... ins);
}
