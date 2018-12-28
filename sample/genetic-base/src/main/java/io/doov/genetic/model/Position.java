/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic.model;

import io.doov.genetic.field.GeneticFieldId;
import io.doov.genetic.field.GeneticPath;

public class Position {

    @GeneticPath(field = GeneticFieldId.X, readable = "x")
    private int x;

    @GeneticPath(field = GeneticFieldId.Y, readable = "y")
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(){}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
