/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import javax.imageio.ImageIO;

import io.doov.genetic.model.*;

public class Image {

    private ArrayList<Pixel> pixels;
    private int width;
    private int height;

    public Image(ArrayList<Pixel> pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
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

    public Image(File file) throws IOException {

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

    public Pixel pixelAt(int w, int h) {
        return pixels.get(w + h * width);
    }
}
