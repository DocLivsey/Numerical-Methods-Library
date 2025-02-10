package MathModule.LinearAlgebra.AlgebraicSystem;

import MathModule.LinearAlgebra.Matrix;
import MathModule.LinearAlgebra.Vector;
import OtherThings.PrettyOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class TridiagonalSystemSolver extends AlgebraicSystemSolver {

    protected TridiagonalSystemSolver(String pathToSettingsFile, String pathToMatrixInputTxt, String pathToVectorInputTxt,
                                      ArrayList<Vector> matrixFromVectors, Collection<Collection<Double>> matrixFromCollections,
                                      Double[][] matrixFromArrays, ArrayList<Double> vector, ArrayList<Double> solutionFromList,
                                      Matrix coefficientMatrix, Vector adjoinVector, Vector solution)
            throws ReflectiveOperationException, IOException {
        super(pathToSettingsFile, pathToMatrixInputTxt, pathToVectorInputTxt, matrixFromVectors, matrixFromCollections,
                matrixFromArrays, vector, solutionFromList, coefficientMatrix, adjoinVector, solution);
    }
    public TridiagonalSystemSolver(String pathToSettingsFile, String pathToMatrixInputTxt, String pathToVectorInputTxt)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, pathToMatrixInputTxt, pathToVectorInputTxt, null,
                null, null, null, null,
                null, null, null);
    }
    public TridiagonalSystemSolver(String pathToMatrixInputTxt, String pathToVectorInputTxt)
            throws ReflectiveOperationException, IOException {
        this(null, pathToMatrixInputTxt, pathToVectorInputTxt);
    }
    public TridiagonalSystemSolver(String pathToSettingsFile, Matrix coefficientMatrix, Vector adjoinVector)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, null, null, null,
                null, null, null, null,
                coefficientMatrix, adjoinVector, null);
    }
    public TridiagonalSystemSolver(Matrix coefficientMatrix, Vector adjoinVector)
            throws ReflectiveOperationException, IOException {
        this(null, coefficientMatrix, adjoinVector);
    }
    public TridiagonalSystemSolver(String pathToSettingsFile, ArrayList<Vector> matrixFromVectors, ArrayList<Double> vector)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, null, null, matrixFromVectors,
                null, null, vector, null,
                null, null, null);
    }
    public TridiagonalSystemSolver(ArrayList<Vector> matrixFromVectors, ArrayList<Double> vector)
            throws ReflectiveOperationException, IOException {
        this(null, matrixFromVectors, vector);
    }

    public boolean isTridiagonalMatrix() {
        if (getCoefficientMatrix().getRows() != getCoefficientMatrix().getColumns())
            return false;
        for (int row = 0; row < getCoefficientMatrix().getRows(); row++) {
            for (int col = 0; col < getCoefficientMatrix().getColumns(); col++) {
                if (Math.abs(row - col) > 1 && getCoefficientMatrix().getElementAt(row, col) != 0)
                    return false;
            }
        }
        return true;
    }

    public HashMap<String, double[]> getDiagonals() {
        if (!isTridiagonalMatrix())
            throw new RuntimeException(PrettyOutput.ERROR + "\nЗаданная матрица:\n" + coefficientMatrix +
                    "не является трехдиагональной" + PrettyOutput.RESET);
        int n = getCoefficientMatrix().getRows();
        double[] a = new double[n];
        double[] b = new double[n];
        double[] c = new double[n];
        double[] d = new double[n];
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                a[i] = getCoefficientMatrix().getElementAt(i, i - 1);
            } else {
                a[i] = 0;
            }
            b[i] = getCoefficientMatrix().getElementAt(i, i);
            if (i < n - 1) {
                c[i] = getCoefficientMatrix().getElementAt(i, i + 1);
            } else {
                c[i] = 0;
            }
            d[i] = getAdjoinVector().getElementAt(i);
        }
        return new HashMap<>(){{
            put("a", a);
            put("b", b);
            put("c", c);
            put("d", d);
        }};
    }

    public void solve() throws ReflectiveOperationException, IOException {
        HashMap<String, double[]> diagonals = getDiagonals();
        double[] a = diagonals.get("a");
        double[] b = diagonals.get("b");
        double[] c = diagonals.get("c");
        double[] d = diagonals.get("d");
        double[] x = TridiagonalSystemSolver.solve(a, b, c, d);
        solution = new Vector(new ArrayList<>(){{
            for (var coordinate : x)
                add(coordinate);
        }});
    }

    public static double[] solve(double[] a, double[] b, double[] c, double[] d) {
        int n = d.length;
        double[] alpha = new double[n];
        double[] beta = new double[n];
        double[] x = new double[n];

        alpha[0] = -c[0] / b[0];
        beta[0] = d[0] / b[0];
        for (int i = 1; i < n; i++) {
            double m = 1.0 / (b[i] + a[i] * alpha[i - 1]);
            alpha[i] = -c[i] * m;
            beta[i] = (d[i] - a[i] * beta[i - 1]) * m;
        }

        x[n - 1] = beta[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            x[i] = alpha[i] * x[i + 1] + beta[i];
        }

        return x;
    }

    public static void main(String[] args) {
        // Пример использования
        double[] a = {1, 0, 1};  // Диагональ под главной
        double[] b = {2, 2, 2};  // Главная диагональ
        double[] c = {1, 0, 1};  // Диагональ над главной
        double[] d = {1, 2, 1};  // Правая часть

        double[] x = solve(a, b, c, d);

        // Вывод решения
        System.out.println("Solution:");
        for (int i = 0; i < x.length; i++) {
            System.out.println("x[" + i + "] = " + x[i]);
        }
    }
}

