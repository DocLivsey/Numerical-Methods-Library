package MathModule.LinearAlgebra;

import MathModule.Abstract.AbstractVector;
import OtherThings.PrettyOutput;
import OtherThings.UsefulThings;
import Parsers.FileParser;
import Parsers.InputStreamParser;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
//import daler_monkey

public class Vector extends AbstractVector<Double> {
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
    public static Vector createZeroVector(int vectorSize) throws ReflectiveOperationException, IOException {
        ArrayList<Double> vector = new ArrayList<>(Collections.nCopies(vectorSize, 0.0));
        return new Vector(vector);
    }
    public Vector copy() throws ReflectiveOperationException, IOException {
        return new Vector(this.vector);
    }
    @Override
    public int hashCode() {
        return (int) ChebyshevNorm();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return this.vector.equals(((Vector) obj).vector);
    }
    @Override
    public String toString() {
        return this.toString("");
    }
    public String toString(String outputFormatPattern) {
        StringBuilder toString = new StringBuilder();
        if (outputFormatPattern == null || outputFormatPattern.equals("\\s+") || outputFormatPattern.isEmpty()) {
            for (var element : this.vector)
                toString.append(element).append(" ");
            return toString.toString();
        }
        DecimalFormat outputFormat = new DecimalFormat(outputFormatPattern);
        for (var element : this.vector)
            toString.append(outputFormat.format(element)).append(" ");
        return toString.toString();
    }
    public void setVectorFromFile(String pathToFile) throws IOException {
        this.vector = new ArrayList<>();
        List<List<Double>> inputVector = FileParser.readDataFromFile(pathToFile,
                from -> {
                    List<List<Double>> vectorsList = new ArrayList<>();
                    for (var line : from) {
                        List<String> linesElements = Arrays.asList(line.strip().split("\\s+"));
                        vectorsList.add((List<Double>) UsefulThings.map(linesElements, Double::parseDouble));
                    }
                    return vectorsList;
                });
        if (inputVector.size() > 1) {
            for (List<Double> vector : inputVector) {
                if (vector.size() > 1)
                    throw new RuntimeException(PrettyOutput.ERROR + "Невозможно считать вектор с файла\n" +
                            "Размерность входных данных не соответсвует вектору\n" +
                            "Веорятно в файле записан не вектор" + PrettyOutput.RESET);
            }
            inputVector.forEach(vector -> this.vector.add(vector.get(0)));
        } else {
            this.vector.addAll(inputVector.get(0));
        }
    }
    public void writeVectorInFile(String pathToFile, String outputFormatPattern) throws IOException {
        FileParser.writeDataInFile(pathToFile, this.toString(outputFormatPattern));
    }
    public void writeVectorInDesiredFolder(String pathToFolder, String fileName, String outputFormatPattern)
            throws IOException {
        Pattern pattern = Pattern.compile("\\w+\\.txt$");
        String pathToFile;
        if (pattern.matcher(pathToFolder).matches())
            pathToFile = pathToFolder + fileName;
        else
            pathToFile = pathToFolder + fileName + ".txt";
        FileParser.writeDataInFile(pathToFile, this.toString(outputFormatPattern));
    }
    public void validateAbstractMethodsInput(Object... arguments) {
        if (!InputStreamParser.isClassesInListAtOnce(Arrays.stream(arguments).toList(), Vector.class)) {
            throw new RuntimeException(PrettyOutput.ERROR_UNDERLINED + "\nОшибка! Неверное количество переданных аргументов\n" +
                    "Ожидалось на вход:\n" + PrettyOutput.CHOOSE + "class: " + PrettyOutput.COMMENT + Vector.class +
                    PrettyOutput.CHOOSE + " Описание: " + PrettyOutput.COMMENT + "вектор с которым суммируем\n" + PrettyOutput.RESET);
        } else {
            Vector vector = (Vector) arguments[0];
            if (vector.getVector().size() != this.vector.size())
                throw new RuntimeException(PrettyOutput.ERROR + "Размеры векторов разные \n" + PrettyOutput.COMMENT +
                        "Пожалуйста, введите вектора одного размера" + PrettyOutput.RESET);
        }
    }
    @Override
    public AbstractVector<? extends Number> add(Object... arguments)
            throws ReflectiveOperationException, IOException {
        validateAbstractMethodsInput(arguments);
        Vector sumVector = new Vector();
        Vector addVector = (Vector) arguments[0];
        for (int i = 0; i < this.vectorSize; i++)
            sumVector.addElement(this.getElementAt(i) + addVector.getElementAt(i));
        return sumVector;
    }
    @Override
    public AbstractVector<? extends Number> subtraction(Object... arguments)
            throws ReflectiveOperationException, IOException {
        validateAbstractMethodsInput(arguments);
        Vector differenceVector = new Vector();
        Vector subVector = (Vector) arguments[0];
        for (int i = 0; i < this.vectorSize; i++)
            differenceVector.addElement(this.getElementAt(i) - subVector.getElementAt(i));
        return differenceVector;
    }
    @Override
    public double scalarMultiply(Object... arguments) {
        validateAbstractMethodsInput(arguments);
        double result = 0;
        Vector multiplyVector = (Vector) arguments[0];
        for (int i = 0; i < this.vectorSize; i++)
            result += this.getElementAt(i) + multiplyVector.getElementAt(i);
        return result;
    }
    @Override
    public AbstractVector<? extends Number> constMultiply(Object... arguments)
            throws ReflectiveOperationException, IOException {
        if (!InputStreamParser.isClassesInListAtOnce(Arrays.stream(arguments).toList(), Double.class))
            throw new RuntimeException(PrettyOutput.ERROR_UNDERLINED + "Ошибка! Неверное количество переданных аргументов\n" +
                    "Ожидалось на вход:\n" + PrettyOutput.CHOOSE + "class: " + PrettyOutput.COMMENT + Double.class +
                    PrettyOutput.CHOOSE + " Описание: " + PrettyOutput.COMMENT + "Констаната на которую умножаем " +
                    "вектор\n" + PrettyOutput.RESET);
        Double constant = (Double) arguments[0];
        Vector resultVector = new Vector();
        this.vector.forEach(element -> resultVector.getVector().add(element * constant)) ;
        return resultVector;
    }
    public double ChebyshevNorm() {
        double result = 0;
        for (double element : this.vector)
            result = Math.max(Math.abs(element), Math.abs(result));
        return result;
    }
    /*
     *  ИНЫМИ СЛОВАМИ ТРАНСПОНИРОВАНИЕ
     */
    public Matrix toMatrix() {
        double[][] convertMatrix = new double[this.vectorSize][1];
        for (int i = 0; i < this.vectorSize; i++)
            convertMatrix[i][0] = this.getElementAt(i);
        return new Matrix(convertMatrix, this.vectorSize, 1);
    }
    public Vector partOfVector(int leftBorder, int rightBorder) throws IOException, ReflectiveOperationException {
        ArrayList<Double> vectorPart = new ArrayList<>();
        for (int oldIndex = leftBorder, newIndex = 0; oldIndex < rightBorder + 1; oldIndex++, newIndex++)
            vectorPart.set(newIndex, this.getElementAt(oldIndex));
        return new Vector(vectorPart);
    }
    public void sort() {
        this.vector.sort(Double::compareTo);
    }
    public static Vector sorted(Vector vector) {
        vector.sort();
        return vector;
    }
    public boolean isEmpty() {
        return this.vector.isEmpty();
    }
    public boolean isZeroVector () {
        AtomicBoolean flag = new AtomicBoolean(false);
        this.vector.forEach(element -> {
            if (element != 0 || Math.abs(element) > super.getEpsilon())
                flag.set(true);
        });
        return flag.get();
    }
    public boolean isNanVector () {
        AtomicBoolean flag = new AtomicBoolean(false); // because flag uses in lambda expression
        Arrays.stream(this.vector.toArray()).forEach(item -> {
            if (Double.isNaN((Double) item))
                flag.set(true);
        });
        return flag.get();
    }
    public boolean isInVector(double element) {
        return this.vector.contains(element);
    }
}
