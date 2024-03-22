import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import LinearAlgebra.*;
import Parsers.FileParser;

public class Main {
    public static void main(String[] args) throws IOException {
        functionExamplesForMain();
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