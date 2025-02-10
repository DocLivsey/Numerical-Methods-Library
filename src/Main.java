import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import MathModule.*;
import MathModule.LinearAlgebra.*;
import MathModule.LinearAlgebra.AlgebraicSystem.AlgebraicSystem;
import MathModule.LinearAlgebra.AlgebraicSystem.AlgebraicSystemSolver;
import MathModule.LinearAlgebra.AlgebraicSystem.TridiagonalSystemSolver;
import MathModule.LinearAlgebra.Vector;
import MathModule.LinearAlgebra.Matrix;
import OtherThings.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Matrix matrix = new Matrix(new Double[][]{
                {10.0, 3.0, 0.0},
                {-12.0, 2.0, 1.0},
                {0.0, 1.0, -4.0}});
        System.out.println(matrix);
        System.out.println(matrix.gaussianDeterminant());
        Vector vector = new Vector(new ArrayList<>(List.of(1.0, 2.0, 1.0)));

        TridiagonalSystemSolver solver = new TridiagonalSystemSolver(matrix, vector);
        solver.solve();
        System.out.println(solver.getSolution());

        //System.out.println(matrix.getClass().getClassLoader());
    }
    public static class ConsoleTest {
        public static void consoleTest() throws IOException, ReflectiveOperationException {
            String pathToInputFile = "input.txt";
            NumericalBase base = new NumericalBase();
            base.setFields(pathToInputFile);
            System.out.println(base.getEpsilon());

            //Vector vector = Vector.createNewRandomIntVector(3, -10, 10);
            Vector vector = new Vector();
            ArrayList<Double> list = UsefulThings.convertInstanceOfObject(vector);
            System.out.println(list);
        }

        public static void txtFileMatch() {
            String[] test = {"fnnfwf123__mwkf_mw.txt", ".txtwjflfwa", "kfkwf.txtekfmkwmf", "wfjwjf.tzt"};
            Pattern pattern = Pattern.compile("\\w+\\.txt$");
            for (var file : test)
                System.out.println(pattern.matcher(file).matches());
        }

        public static void treeSetTest() {
            TreeSet<Point2D> pointsTreeSet = new TreeSet<>(Comparator.comparing(Point2D::getX));
            pointsTreeSet.addAll(List.of(new Point2D(0, 0), new Point2D(0, 1), new Point2D(1, 0),
                    new Point2D(12, 13), new Point2D(13, 12)));
            System.out.println(pointsTreeSet);
        }
    }
    public static void functionExamplesForMain() throws IOException {
        String pathToInputFile = "input.txt";
        String pathToOutputFile = "output.txt";
        MathFunction function = arguments -> new Point2D(arguments.get(0), Math.pow(arguments.get(0), 2));
        ArrayList<Point2D> points = new ArrayList<>(List.of(new Point2D(10, Double.NaN),
                new Point2D(5, Double.NaN)));
        ArrayList<Double> arguments = new ArrayList<>(List.of(Double.NaN));
        MathFunctionOperations mathFunction = new MathFunctionOperations(pathToInputFile, points, arguments, function);
        mathFunction.writePointsInFile(pathToOutputFile);
        System.out.println(mathFunction.getEpsilon());
    }
}