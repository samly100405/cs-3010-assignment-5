package org.samly;

import com.beust.jcommander.JCommander;
import org.samly.cli.Args;
import org.samly.solvers.Evaluable;
import org.samly.solvers.NumericalDifferentiator;
import org.samly.solvers.NumericalIntegrator;
import org.samly.solvers.Polynomial;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static final double X = 3.5467;
    static final double A = 1;
    static final double B = 5;
    static final double H = 0.0001;
    static final int N = 5;

    public static void main(String[] args) throws FileNotFoundException {
        Args a = new Args();
        JCommander.newBuilder()
                .addObject(a)
                .build()
                .parse(args);

        Polynomial p = generatePolynomial(a.getFile());

        Scanner userScanner = new Scanner(System.in);
        boolean isDerivative = false;
        boolean isReady = false;
        while (true) {
            if (!isReady) {
                System.out.println("Derivative or Integral? (d/i)");
            }
            else {
                System.out.println("Enter a real number. ");
            }
            String s = userScanner.next();

            if (s.equals("q")) break;

            if (!isReady) {
                if (s.equals("i")) {
                    isDerivative = false;
                    isReady = true;
                }
                else if (s.equals("d")) {
                    isDerivative = true;
                    isReady = true;
                }
            }
            else {
                try {
                    double in = Double.parseDouble(s);
                    double res = isDerivative ?
                            NumericalDifferentiator.richardsonExtrapolation(p, in,H, N)[N][N] :
                            NumericalIntegrator.rombergMethod(p, 0, in, N)[N][N];
                    if (isDerivative) System.out.println("Taking derivative.\nf'(" + in + ") = " + res);
                    else System.out.println("Taking integral.\nF(" + in + ") = " + res);
                    isReady = false;
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid number or 'q' to quit.");
                }
            }
        }
    }

    private static Polynomial generatePolynomial(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file);

        int degree = s.nextInt();

        double[] coefficients = new double[degree + 1];
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i] = s.nextDouble();
        }

        return new Polynomial(degree, coefficients);
    }

    private static void exercises() {
        Evaluable[] functions = new Evaluable[3];
        double[] actualDerivatives = new double[3];
        double[] actualIntegrals = new double[3];
        //noinspection Convert2MethodRef
        functions[0] = (double x) -> Math.sin(x);
        actualDerivatives[0] = Math.cos(X);
        actualIntegrals[0] = (-Math.cos(B)) - (-Math.cos(A));


        functions[1] = (double x) -> 1 + Math.log(x);
        actualDerivatives[1] = 1/X;
        actualIntegrals[1] = B * Math.log(B) - A * Math.log(A);

        Polynomial p = new Polynomial(2, new double[]{1, -3, 5});
        functions[2] = p;
        actualDerivatives[2] = p.derivative().evaluate(X);
        actualIntegrals[2] = p.integral().evaluate(B) - p.integral().evaluate(A);

        exercise1(functions, actualDerivatives);
        exercise2(functions, actualIntegrals);
    }

    private static void exercise1(Evaluable[] functions, double[] actuals) {
        for (int n = 0; n < 1; n++) {
            Evaluable f = functions[n];
            double actual = actuals[n];

            for (int i = 0; i < 5; i++) {
                double s = H / Math.pow(2, i);
                double res = NumericalDifferentiator.naiveDerivative(f, X, s);

                System.out.println("f'(x) ~ " + res + ", h = " + s);
            }
            double[][] mat = NumericalDifferentiator.richardsonExtrapolation(f, X, H, 5);

            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j <= i; j++) {
                    System.out.println("D(" + i + ", " + j + ") = " + mat[i][j]);
                }
            }

            System.out.println("actual: " + actual);
            double minDel = Double.MAX_VALUE;
            int minI = 0, minJ = 0;
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j <= i; j++) {
                    double del = Math.abs((actual-mat[i][j])/actual);
                    if (del < minDel) {
                        minDel = del;
                        minI = i;
                        minJ = j;
                    }
                }
            }
            System.out.println("Best: D(" + minI + ", " + minJ + ") = " + mat[minI][minJ]);
        }
    }

    private static void exercise2(Evaluable[] functions, double[] actuals) {
        for (int n = 0; n < functions.length; n++) { // change later
            Evaluable f = functions[n];
            double actual = actuals[n];

            // trapezoid
            int[] numTrapezoids = {5, 10, 20};
            System.out.println("Actual: " + actual);
            for (int traps : numTrapezoids) {
                System.out.println("No. of Trapezoids: " + traps);
                double[] areas = NumericalIntegrator.trapezoidMethod(f, A, B, traps);

                double sum = 0;
                for (int j = 0; j < areas.length; j++) {
                    sum += areas[j];
                    System.out.println("A" + j + " = " + areas[j]);
                }
                System.out.println("A = " + sum);
                System.out.println("%err = " + 100 * Math.abs((actual-sum)/actual));
                System.out.println();
            }

            double[][] mat = NumericalIntegrator.rombergMethod(f, A, B, 5);
            double minDel = Double.MAX_VALUE;
            int minI = 0, minJ = 0;
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j <= i; j++) {
                    System.out.println("R(" + i + ", " + j + ") = " + mat[i][j]);
                    double del = Math.abs((actual-mat[i][j])/actual);
                    if (del < minDel) {
                        minDel = del;
                        minI = i;
                        minJ = j;
                    }
                }
            }
            System.out.println("Best: R(" + minI + ", " + minJ + ") = " + mat[minI][minJ]);
            System.out.println();
        }
    }
}