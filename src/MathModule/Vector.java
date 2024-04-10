package MathModule;

import OtherThings.PrettyOutput;
import OtherThings.Procedure;
import OtherThings.UsefulThings;
import Parsers.FileParser;

import java.io.IOException;
import java.util.*;
//import daler_monkey

public class Vector extends MathModule.Abstract.Vector<Double> {
    public Vector(String pathToSettingsFile, String pathToVectorInputTxt, ArrayList<Double> vector)
            throws ReflectiveOperationException, IOException {
        if (pathToSettingsFile != null) {
            super.setFields(pathToSettingsFile);
            this.setFields(pathToSettingsFile);
        } if (pathToVectorInputTxt != null && (this.vector == null || this.vector.isEmpty())) {
            this.setVectorFromFile(pathToVectorInputTxt);
        } else
            this.vector = Objects.requireNonNullElseGet(vector, ArrayList::new);
        this.vectorSize = this.vector.size();
    }
    public Vector(String pathToSettingsFile, String pathToVectorInputTxt)
            throws IOException, ReflectiveOperationException {
        this(pathToSettingsFile, pathToVectorInputTxt, null);
    }
    public Vector(String pathToVectorInputTxt) throws IOException, ReflectiveOperationException {
        this(null, pathToVectorInputTxt);
    }
    public Vector(String pathToSettingsFile, ArrayList<Double> vector) throws IOException, ReflectiveOperationException {
        this(pathToSettingsFile, null, vector);
    }
    public Vector(ArrayList<Double> vector) throws IOException, ReflectiveOperationException {
        this(null, vector);
    }
    public Vector() throws IOException, ReflectiveOperationException {
        this((String) null);
    }
    public void setVectorFromFile(String pathToFile) throws IOException {
        this.vector = (ArrayList<Double>) FileParser.readDataFromFile(pathToFile, (Procedure<List<Double>, List<String>>)
                from -> new ArrayList<>(UsefulThings.map(from, Double::parseDouble)));
    }
    @Override
    public MathModule.Abstract.Vector<Double> add(MathModule.Abstract.Vector<Double> addVector)
            throws ReflectiveOperationException, IOException {
        if (this.vectorSize != addVector.getVectorSize())
            throw new RuntimeException(PrettyOutput.ERROR + "Размеры векторов разные \n" + PrettyOutput.COMMENT +
                    "Пожалуйста, введите вектора одного размера" + PrettyOutput.RESET);
        Vector sumVector = new Vector();
        for (int i = 0; i < this.vectorSize; i++)
            sumVector.setElementAt(this.getElementAt(i) + addVector.getElementAt(i), i);
        return sumVector;
    }
    @Override
    public MathModule.Abstract.Vector<Double> subtraction(MathModule.Abstract.Vector<Double> subVector)
            throws ReflectiveOperationException, IOException {
        if (this.vectorSize != subVector.getVectorSize())
            throw new RuntimeException(PrettyOutput.ERROR + "Размеры векторов разные \n" + PrettyOutput.COMMENT +
                    "Пожалуйста, введите вектора одного размера" + PrettyOutput.RESET);
        Vector differenceVector = new Vector();
        for (int i = 0; i < this.vectorSize; i++)
            differenceVector.setElementAt(this.getElementAt(i) - subVector.getElementAt(i), i);
        return differenceVector;
    }
    @Override
    public MathModule.Abstract.Vector<Double> scalarMultiply(MathModule.Abstract.Vector<Double> multiplyVector) {
        if (this.vectorSize != multiplyVector.getVectorSize())
            throw new RuntimeException(PrettyOutput.ERROR + "Размеры векторов разные \n" + PrettyOutput.COMMENT +
                    "Пожалуйста, введите вектора одного размера" + PrettyOutput.RESET);
        return null;
    }
    @Override
    public MathModule.Abstract.Vector<Double> vectorMultiply(MathModule.Abstract.Vector<Double> multiplyVector) {
        if (this.vectorSize != multiplyVector.getVectorSize())
            throw new RuntimeException(PrettyOutput.ERROR + "Размеры векторов разные \n" + PrettyOutput.COMMENT +
                    "Пожалуйста, введите вектора одного размера" + PrettyOutput.RESET);
        return null;
    }
}
