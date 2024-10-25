package org.samly.solvers;

import java.util.Arrays;

public final class Polynomial implements Evaluable {
    private final int degree;
    private final double[] coefficients;

    public Polynomial(int degree, double[] coefficients) {
        this.degree = degree;
        this.coefficients = coefficients.clone();
    }

    public int getDegree() {
        return degree;
    }

    public double evaluate(double x) {
        double out = 0.0F;
        for (int i = 0; i < coefficients.length; i++) {
            out += (coefficients[i] * Math.pow(x, degree-i));
        }
        return out;
    }

    public Polynomial derivative() {
        int newDegree = degree - 1;
        double[] newCoefficients = new double[newDegree + 1];

        for (int i = 0; i < newCoefficients.length; i++) {
            newCoefficients[i] = (degree - i) * coefficients[i];
        }

        return new Polynomial(newDegree, newCoefficients);
    }

    public Polynomial integral() {
        int newDegree = degree + 1;
        double[] newCoefficients = new double[newDegree + 1];

        for (int i = 0; i < coefficients.length; i++) {
            newCoefficients[i] = coefficients[i] / (degree - i + 1);
        }

        return new Polynomial(newDegree, newCoefficients);
    }
}