package org.samly.solvers;

public class NumericalIntegrator {
    public static double[] trapezoidMethod(Evaluable f, double a, double b, int n) {
        double[] parts = new double[n];
        final double height = (b - a) / n;

        double ai = a, bi = a + height;
        for (int i = 0; i < n; i++) {
            parts[i] = height * (f.evaluate(ai) + f.evaluate(bi)) / 2;
            ai = bi;
            bi += height;
        }

        return parts;
    }

    public static double[][] rombergMethod(Evaluable f, double a, double b, int n) {
        double[][] out = new double[n+1][n+1];

        double h = b - a;
        out[0][0] = h * (f.evaluate(a) + f.evaluate(b)) / 2;

        for (int i = 1; i <= n; i++) {
            h /= 2;
            double sum = 0;
            for (int k = 1; k <= Math.pow(2, i) - 1; k+=2) {
                sum += f.evaluate(a + k * h);
            }
            out[i][0] = out[i-1][0]/2 + sum * h;
            for (int j = 1; j <= i; j++) {
                out[i][j] = out[i][j-1] + (out[i][j-1] - out[i-1][j-1]) / (Math.pow(4, j) - 1);
            }
        }

        return out;
    }
}
