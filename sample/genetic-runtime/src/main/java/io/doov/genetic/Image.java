/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import com.google.common.collect.Streams;

import io.doov.core.dsl.lang.StepCondition;
import io.doov.genetic.field.dsl.DslPixel;
import io.doov.genetic.model.*;

public class Image {

    private ArrayList<Pixel> pixels;
    private int width;
    private int height;

    private Color fill;

    public Image(ArrayList<Pixel> pixels, Color fill, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.fill = fill;
    }

    public ArrayList<Pixel> getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getFill() {
        return this.fill;
    }

    public Image(File file, Color fill) throws IOException {

        this.fill = fill;

        BufferedImage buffer = ImageIO.read(file);
        this.height = buffer.getHeight();
        this.width  = buffer.getWidth();

        System.out.println(buffer.getColorModel().toString());

        ArrayList<Pixel> pixels = new ArrayList<>(height * width);

        for(int i = 0; i < buffer.getHeight(); i++) {
            for(int j = 0; j < buffer.getWidth(); j++) {
                int rgb = buffer.getRGB(j,i);
                int red   = rgb >>> 16 & 0xFF;
                int green = rgb >>>  8 & 0xFF;
                int blue  = rgb        & 0xFF;
                pixels.add(new Pixel(new Position(i,j), new Color(red,green,blue)));
            }
        }

        this.pixels = pixels;
    }

    public Image(int width, int height, Color fill) {
        this.height = height;
        this.width  = width;
        this.fill = fill;
        this.pixels = new ArrayList<>(height * width);

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                this.pixels.add(new Pixel(new Position(i,j),this.fill));
            }
        }
    }

    public void snapshot(File file) throws IOException {
        BufferedImage buffer = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        pixels.stream().forEach(pixel -> buffer.setRGB(
                    pixel.getPosition().getY(),
                    pixel.getPosition().getX(),
                    pixel.getColor().toRGBInt()
        ));
        ImageIO.write(buffer,"png",file);
    }

    public int evaluate(DslPixel.PixelRule rule, Color reference) {

        int signal = 0;
        int noise = 0;

        for (Pixel pix : this.getPixels()) {
            if (rule.executeOn(pix).value()) {
                if (pix.getColor().equals(reference)) {
                    signal++;
                } else {
                    noise++;
                }
            }
        }
        return signal - noise;
    }

    public void drawRule(DslPixel.PixelRule rule, Color fill) {
        for (Pixel pix : this.getPixels()) {
            if (rule.executeOn(pix).value()) {
                pix.setColor(fill);
            }
        }
    }
}
