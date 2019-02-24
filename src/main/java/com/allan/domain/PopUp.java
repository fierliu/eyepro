package com.allan.domain;

public class PopUp {

    private double X;
    private double Y;
    private double width;
    private double height;

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "PopUp{" +
                "X=" + X +
                ", Y=" + Y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
