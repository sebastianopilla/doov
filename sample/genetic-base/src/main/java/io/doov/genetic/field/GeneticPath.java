package io.doov.genetic.field;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.doov.core.Path;

@Path
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneticPath {
    GeneticFieldId field();
    GeneticConstraint constraint() default GeneticConstraint.NONE;
    String readable() default "";
}