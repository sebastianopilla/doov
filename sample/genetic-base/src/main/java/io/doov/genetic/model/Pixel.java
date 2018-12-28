/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic.model;

import io.doov.genetic.field.GeneticFieldId;
import io.doov.genetic.field.GeneticPath;

public class Pixel {

    @GeneticPath(field = GeneticFieldId.POSITION, readable = "position")
    private Position position;

    @GeneticPath(field = GeneticFieldId.COLOR, readable = "color")
    private Color color;

    public Pixel(Position position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Pixel() {}

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Pixel{" +
                "position=" + position +
                ", color=" + color +
                '}';
    }
}
