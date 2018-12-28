/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import io.doov.core.dsl.DOOV;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.lang.ValidationRule;
import io.doov.genetic.field.dsl.DslPixel;
import io.doov.genetic.model.*;

public class Runner {

    public static StepCondition inRectangle(int xmin, int xmax, int ymin, int ymax) {
        return DslPixel.x.greaterThan(xmin)
                        .and(DslPixel.x.lesserThan(xmax))
                        .and(DslPixel.y.greaterThan(ymin))
                        .and(DslPixel.y.lesserThan(ymax));
    }

    public static int evaluateRule(Image image, DslPixel.PixelRule rule) {

        int acc = 0;

        for(int i = 0; i < image.getWidth(); i++ ) {
            for (int j = 0; j < image.getHeight(); j++) {
                Pixel pix = image.pixelAt(i, j);
                if (rule.executeOn(pix).value()) {
                    acc += 0xFF - pix.getColor().getRed();
                    acc += 0xFF - pix.getColor().getGreen();
                    acc += 0xFF - pix.getColor().getBlue();
                }
            }
        }
        return acc;
    }

    public static void main(String[] args) {

        String inputPath = "./sample/genetic-runtime/src/main/resources/bansky.png";

        try {
            Image source = new Image(new File(inputPath));

            DslPixel.PixelRule rectangle = DslPixel.when(inRectangle(0,source.getWidth(),0,source.getHeight())).validate();

            System.out.println(evaluateRule(source,rectangle));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
