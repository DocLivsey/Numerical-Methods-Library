package MathModule;

import MathModule.Abstract.AbstractVector;
import MathModule.LinearAlgebra.*;
import MathModule.LinearAlgebra.Vector;
import OtherThings.PrettyOutput;
import Parsers.InputStreamParser;

import java.io.*;
import java.util.*;

public class VectorFunction extends AbstractVector<MathImplicitFunctionOperations> {
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
    public void validateAbstractMethodsInput(Integer mode, Object... arguments) throws IOException {
        switch (mode) {
            case 1:
                if (!InputStreamParser.isClassesInListAtOnce(Arrays.stream(arguments).toList(), MathImplicitFunction.class) &&
                        !InputStreamParser.isClassesInListAtOnce(Arrays.stream(arguments).toList(), PointMultiD.class))
                    throw new RuntimeException(PrettyOutput.ERROR_UNDERLINED + "Ошибка! Неверное количество переданных аргументов\n" +
                            "Ожидалось на вход:\n" + PrettyOutput.CHOOSE + "class: " + PrettyOutput.COMMENT + VectorFunction.class +
                            PrettyOutput.CHOOSE + " Описание: " + PrettyOutput.COMMENT + "вектор с которым суммируем\n" +
                            PrettyOutput.CHOOSE + "class: " + PrettyOutput.COMMENT + PointMultiD.class + PrettyOutput.CHOOSE +
                            " Описание: " + PrettyOutput.COMMENT + "точка в которой суммируем вектора" + PrettyOutput.RESET);
            case 2:
                if (!InputStreamParser.isClassesInListAtOnce(Arrays.stream(arguments).toList(), PointMultiD.class) &&
                        !InputStreamParser.isClassesInListAtOnce(Arrays.stream(arguments).toList(), Number.class))
                    throw new RuntimeException(PrettyOutput.ERROR_UNDERLINED + "Ошибка! Неверное количество переданных аргументов\n" +
                            "Ожидалось на вход:\n" + PrettyOutput.CHOOSE + "class: " + PrettyOutput.COMMENT + Number.class +
                            PrettyOutput.CHOOSE + " Описание: " + PrettyOutput.COMMENT + "скаляр на который умножаем вектор\n" +
                            PrettyOutput.CHOOSE + "class: " + PrettyOutput.COMMENT + PointMultiD.class + PrettyOutput.CHOOSE +
                            " Описание: " + PrettyOutput.COMMENT + "точка в которой умножаем на скаляр" + PrettyOutput.RESET);
            default:
                throw new RuntimeException(PrettyOutput.RED_BRIGHT +  "Вероятно вы ошиблись с режимом валидации " +
                        "входных данных для абстрактных методов\n" + PrettyOutput.YELLOW_BOLD + "Доступно два режима " +
                        "валидации:\n1 - вектор функция и точка\n2 - скаляр и точка" + PrettyOutput.RESET);
        }
    }
    @Override
    public AbstractVector<? extends Number> add(Object... arguments)
            throws ReflectiveOperationException, IOException {
        validateAbstractMethodsInput(1, arguments);
        PointMultiD inPoint = new PointMultiD();
        ArrayList<MathImplicitFunctionOperations> addFunctions = new ArrayList<>();
        for (var arg : arguments) {
            if (arg.getClass() == PointMultiD.class)
                inPoint = (PointMultiD) arg;
            if (arg.getClass() == ArrayList.class)
                addFunctions.addAll((ArrayList<MathImplicitFunctionOperations>) arg);
            else if (arg.getClass() == MathImplicitFunctionOperations.class)
                addFunctions.add((MathImplicitFunctionOperations) arg);
        }
        Vector thisVector = new Vector();
        Vector addVector = new Vector();
        for (int i = 0; i < addFunctions.size(); i++) {
            thisVector.setElementAt(this.vector.get(i).calculatePoint(inPoint.getVectorX()).getY(), i);
            addVector.setElementAt(addFunctions.get(i).calculatePoint(inPoint.getVectorX()).getY(), i);
        }
        return thisVector.add(addVector);
    }
    @Override
    public AbstractVector<? extends Number> subtraction(Object... arguments)
            throws ReflectiveOperationException, IOException {
        validateAbstractMethodsInput(1, arguments);
        PointMultiD inPoint = new PointMultiD();
        ArrayList<MathImplicitFunctionOperations> subFunctions = new ArrayList<>();
        for (var arg : arguments) {
            if (arg.getClass() == PointMultiD.class)
                inPoint = (PointMultiD) arg;
            if (arg.getClass() == ArrayList.class)
                subFunctions.addAll((ArrayList<MathImplicitFunctionOperations>) arg);
            else if (arg.getClass() == MathImplicitFunctionOperations.class)
                subFunctions.add((MathImplicitFunctionOperations) arg);
        }
        Vector thisVector = new Vector();
        Vector subVector = new Vector();
        for (int i = 0; i < subFunctions.size(); i++) {
            thisVector.setElementAt(this.vector.get(i).calculatePoint(inPoint.getVectorX()).getY(), i);
            subVector.setElementAt(subFunctions.get(i).calculatePoint(inPoint.getVectorX()).getY(), i);
        }
        return thisVector.subtraction(subVector);

    }
    @Override
    public double scalarMultiply(Object... arguments) throws IOException, ReflectiveOperationException {
        validateAbstractMethodsInput(1, arguments);
        PointMultiD inPoint = new PointMultiD();
        ArrayList<MathImplicitFunctionOperations> scalarMultiplyFunctions = new ArrayList<>();
        for (var arg : arguments) {
            if (arg.getClass() == PointMultiD.class)
                inPoint = (PointMultiD) arg;
            if (arg.getClass() == ArrayList.class)
                scalarMultiplyFunctions.addAll((ArrayList<MathImplicitFunctionOperations>) arg);
            else if (arg.getClass() == MathImplicitFunctionOperations.class)
                scalarMultiplyFunctions.add((MathImplicitFunctionOperations) arg);
        }
        Vector thisVector = new Vector();
        Vector scalarMultiplyVector = new Vector();
        for (int i = 0; i < scalarMultiplyFunctions.size(); i++) {
            thisVector.setElementAt(this.vector.get(i).calculatePoint(inPoint.getVectorX()).getY(), i);
            scalarMultiplyVector.setElementAt(scalarMultiplyFunctions.get(i).calculatePoint(inPoint.getVectorX()).getY(), i);
        }
        return thisVector.scalarMultiply(scalarMultiplyVector);
    }
    @Override
    public AbstractVector<? extends Number> constMultiply(Object... arguments)
            throws IOException, ReflectiveOperationException {
        validateAbstractMethodsInput(2, arguments);
        double constant = 1;
        PointMultiD inPoint = new PointMultiD();
        for (var arg : arguments) {
            if (arg.getClass() == Double.class)
                constant = (double) arg;
            if (arg.getClass() == PointMultiD.class)
                inPoint = (PointMultiD) arg;
        }
        Vector thisVector = new Vector();
        for (var function : this.vector)
            thisVector.addElement(function.calculatePoint(inPoint.getVectorX()).getY());
        return thisVector.constMultiply(constant);
    }

}
