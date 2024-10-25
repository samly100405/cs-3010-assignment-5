package org.samly.solvers;

public class NumericalDifferentiator {
    public static double naiveDerivative(Evaluable f, double x, double h) {
        return (f.evaluate(x+h) - f.evaluate(x-h)) / (2 * h);
    }

    public static double[][] richardsonExtrapolation(Evaluable f, double x, double h, int n) {
        double[][] out = new double[n+1][n+1];

        for (int i = 0; i <= n; i++) {
            out[i][0] = (f.evaluate(x+h) - f.evaluate(x-h)) / (2 * h);
            for (int j = 1; j <= i; j++) {
                out[i][j] = out[i][j-1] + (out[i][j-1] - out[i-1][j-1]) / (Math.pow(4, j) - 1);
            }
            h /= 2;
        }

        return out;
    }
}
