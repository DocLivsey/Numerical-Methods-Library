package MathModule;

import MathModule.Abstract.Vector;
import MathModule.LinearAlgebra.PointMultiD;
import OtherThings.PrettyOutput;
import Parsers.InputStreamParser;

import java.io.*;
import java.util.*;

public class VectorFunction extends Vector<MathImplicitFunctionOperations> {
    public VectorFunction(String pathToSettingsFile, ArrayList<MathImplicitFunctionOperations> mathImplicitFunctions)
            throws ReflectiveOperationException, IOException {
        if (pathToSettingsFile != null && !pathToSettingsFile.isEmpty()) {
            super.setFields(pathToSettingsFile);
            this.setFields(pathToSettingsFile);
        }
        this.vector = Objects.requireNonNullElseGet(mathImplicitFunctions, ArrayList::new);
    }
    public VectorFunction(ArrayList<MathImplicitFunctionOperations> mathImplicitFunctions)
            throws ReflectiveOperationException, IOException {
        this(null, mathImplicitFunctions);
    }
    public VectorFunction()
            throws IOException, ReflectiveOperationException {
        this(null);
    }
    public void validateAbstractMethodsInput(ArrayList<Object> args) throws IOException {
        if (!InputStreamParser.isClassesInListAtOnce(args, MathImplicitFunction.class) &&
                !InputStreamParser.isClassesInListAtOnce(args, PointMultiD.class))
            throw new RuntimeException(PrettyOutput.RED_UNDERLINED + "Ошибка! Неверное количество переданных аргументов\n" +
                    "Ожидалось на вход:\n" + PrettyOutput.CHOOSE + "class: " + PrettyOutput.COMMENT +
                    "MathModule.Vector<MathImplicitFunction>" + PrettyOutput.CHOOSE + " Описание: "
                    + PrettyOutput.COMMENT + "вектор с которым суммируем\n" + PrettyOutput.CHOOSE + "class: " +
                    PrettyOutput.COMMENT + "MathModule.LinearAlgebra.PointMultiD" + PrettyOutput.CHOOSE + " Описание: "
                    + PrettyOutput.COMMENT + "точка в которой суммируем вектора" + PrettyOutput.RESET);
    }
    @Override
    public Vector<? extends Number> add(ArrayList<Object> args)
            throws ReflectiveOperationException, IOException {
        try {
            validateAbstractMethodsInput(args);
        } catch (IOException ignored) {}
        PointMultiD inPoint = new PointMultiD();
        ArrayList<MathImplicitFunctionOperations> mathImplicitFunction = new ArrayList<>();
        for (var arg : args) {
            if (arg.getClass() == PointMultiD.class)
                inPoint = (PointMultiD) arg;
            if (arg.getClass() == ArrayList.class)
                mathImplicitFunction.addAll((ArrayList<MathImplicitFunctionOperations>) arg);
            else if (arg.getClass() == MathImplicitFunctionOperations.class)
                mathImplicitFunction.add((MathImplicitFunctionOperations) arg);
        }
        MathModule.Vector result = new MathModule.Vector();
        for (var functionOperations : mathImplicitFunction) {
            ArrayList<Object> arg = new ArrayList<>();
            arg.add(functionOperations.calculatePoint(inPoint.getVectorX()).getVectorX());
            result.add(arg);
        }
        return result;
    }
    @Override
    public Vector<? extends Number> subtraction(ArrayList<Object> args)
            throws ReflectiveOperationException, IOException {
        return null;
    }
    @Override
    public double scalarMultiply(ArrayList<Object> args) {
        return 0;
    }
    @Override
    public Vector<? extends Number> constMultiply(
            ArrayList<Object> args) {
        return null;
    }

}
