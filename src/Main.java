import java.io.*;
import java.util.*;
import java.nio.file.*;
import LinearAlgebra.*;
import Parsers.FileParser;

public class Main {
    public static void main(String[] args) throws IOException {
        String pathToFile = "input.txt";
        System.out.println(FileParser.ParseSettings.getParametersTable(pathToFile));
    }
    public static void functionExamplesForMain()
    {
        MathFunction function = arguments -> new Point2D(arguments.get(0), Math.pow(arguments.get(0), 2));
        ArrayList<Point2D> points = new ArrayList<>(List.of(new Point2D(10, Double.NaN),
                new Point2D(5, Double.NaN)));
        ArrayList<Double> arguments = new ArrayList<>(List.of(Double.NaN));
        MathFunctionOperations mathFunction = new MathFunctionOperations(points, arguments, function);
        mathFunction.printPoints();
    }
}