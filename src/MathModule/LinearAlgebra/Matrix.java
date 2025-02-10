package MathModule.LinearAlgebra;

import MathModule.Abstract.AbstractMatrix;
import MathModule.LinearAlgebra.AlgebraicSystem.AlgebraicSystem;
import OtherThings.PrettyOutput;
import OtherThings.UsefulThings;
import Parsers.FileParser;
import Parsers.InputStreamParser;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class Matrix extends AbstractMatrix<Double> {
    public Matrix(String pathToSettingsFile, String pathToMatrixInputTxt, ArrayList<Vector> matrixFromVectors,
                  Collection<Collection<Double>> matrixFromCollections, Double[][] matrixFromArrays)
            throws ReflectiveOperationException, IOException {
        if (pathToSettingsFile != null) {
            super.setFields(pathToSettingsFile);
            this.setFields(pathToSettingsFile);
        } if (pathToMatrixInputTxt != null && (this.matrix == null || this.matrix.isEmpty())) {
            this.setMatrixFromFile(pathToMatrixInputTxt);
        } else {
            if (matrixFromVectors != null) {
                matrixFromVectors.forEach(this::addRow);
                this.setRows(this.matrix.size());
                this.setColumns(this.getRowAt(0).getVector().size());
            } else if (matrixFromCollections != null) {
                this.setMatrix(matrixFromCollections);
            } else if (matrixFromArrays != null) {
                Collection<Double[]> firstTransformStage = Arrays.stream(matrixFromArrays).toList();
                Collection<Collection<Double>> secondTransformStage = new ArrayList<>(){{
                    firstTransformStage.forEach(row -> this.add(Arrays.stream(row).toList()));
                }};
                this.setMatrix(secondTransformStage);
            } else {
                this.matrix = new ArrayList<>();
                this.setRows(0);
                this.setColumns(0);
            }
        }
    }
    public Matrix(String pathToSettingsFile, String pathToMatrixInputTxt)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, pathToMatrixInputTxt, null, null, null);
    }
    public Matrix(String pathToMatrixInputTxt) throws ReflectiveOperationException, IOException {
        this(null, pathToMatrixInputTxt);
    }
    public Matrix(String pathToSettingsFile, ArrayList<Vector> matrixFromVectors)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, null, matrixFromVectors, null, null);
    }
    public Matrix(ArrayList<Vector> matrixFromVectors) throws ReflectiveOperationException, IOException {
        this(null, matrixFromVectors);
    }
    public Matrix(String pathToSettingsFile, Collection<Collection<Double>> matrixFromCollections)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, null, null, matrixFromCollections, null);
    }
    public Matrix(Collection<Collection<Double>> matrixFromCollections) throws ReflectiveOperationException, IOException {
        this(null, matrixFromCollections);
    }
    public Matrix(String pathToSettingsFile, Double[][] matrixFromArrays)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, null, null, null, matrixFromArrays);
    }
    public Matrix(Double[][] matrixFromArrays) throws ReflectiveOperationException, IOException {
        this(null, matrixFromArrays);
    }
    public Matrix() throws ReflectiveOperationException, IOException {
        this((String) null);
    }
    public Matrix createZeroMatrix(int rows, int columns) throws ReflectiveOperationException, IOException {
        return new Matrix(
                new ArrayList<>(Collections.nCopies(rows,
                        new ArrayList<>(Collections.nCopies(columns, 0.0)))
        ));
    }
    public Matrix copy() throws ReflectiveOperationException, IOException {
        Collection<Collection<Double>> copyMatrix = new ArrayList<>();
        this.matrix.forEach(row -> copyMatrix.add(row.getVector()));
        return new Matrix(copyMatrix);
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
        return this.matrix.equals(((Matrix) obj).getMatrix());
    }
    @Override
    public String toString() {
        return this.toString("");
    }
    public String toString(String outputFormatPattern) {
        StringBuilder toString = new StringBuilder();
        this.getMatrix().forEach(row -> {
            Vector vector = null;
            try {
                vector = new Vector(row.getVector());
            } catch (IOException | ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
            toString.append(vector.toString(outputFormatPattern)).append("\n");
        });
        return toString.toString();
    }

    public void setMatrixFromFile(String pathToFile) throws IOException {
        this.matrix = new ArrayList<>();
        List<List<Double>> inputMatrix = FileParser.readDataFromFile(pathToFile,
                from -> {
                    List<List<Double>> vectorsList = new ArrayList<>();
                    for (var line : from) {
                        List<String> linesElements = Arrays.asList(line.strip().split("\\s+"));
                        vectorsList.add((List<Double>) UsefulThings.map(linesElements, Double::parseDouble));
                    }
                    return vectorsList;
                });
        if (inputMatrix.size() > 1) {
            inputMatrix.forEach(row -> {
                try {
                    this.addRow(new Vector((ArrayList<Double>) row));
                } catch (IOException | ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            });
            this.setRows(this.matrix.size());
            this.setColumns(this.getColumnAt(0).getVectorSize());
        } else {
            throw new RuntimeException(PrettyOutput.ERROR + "Невозможно считать матрицу с файла\n" +
                    "Размерность входных данных не соответсвует определению матрицы\n" +
                    "Веорятно в файле записана не матрица" + PrettyOutput.RESET);
        }
    }
    public void writeMatrixInFile(String pathToFile, String outputFormatPattern) throws IOException {
        FileParser.writeDataInFile(pathToFile, this.toString(outputFormatPattern));
    }
    public void writeMatrixInDesiredFolder(String pathToFolder, String fileName, String outputFormatPattern)
            throws IOException {
        Pattern pattern = Pattern.compile("\\w+\\.txt$");
        String pathToFile;
        if (pattern.matcher(pathToFolder).matches())
            pathToFile = pathToFolder + fileName;
        else
            pathToFile = pathToFolder + fileName + ".txt";
        FileParser.writeDataInFile(pathToFile, this.toString(outputFormatPattern));
    }

    public Matrix partOfMatrix(int leftBorder, int rightBorder, int upBorder, int downBorder)
            throws ReflectiveOperationException, IOException {
        Matrix partOfMatrix = new Matrix();
        for (int oldRow = upBorder, newRow = 0; oldRow < downBorder + 1; oldRow++, newRow++) {
            Vector partOfRow = new Vector(this.getRowAt(oldRow).getVector());
            partOfMatrix.addRow(partOfRow.partOfVector(leftBorder, rightBorder));
        }
        return partOfMatrix;
    }
    public Matrix setMinor(int rowIndex, int colIndex) throws ReflectiveOperationException, IOException {
        Matrix minor = this.copy();
        minor.removeRowAt(rowIndex);
        minor.removeColumnAt(colIndex);
        return minor.copy();
    }

    public void validateAbstractMethodsInput(Object... arguments) throws ReflectiveOperationException, IOException {
        if (!InputStreamParser.isClassesInListAtOnce(Arrays.stream(arguments).toList(), Matrix.class)) {
            throw new RuntimeException(PrettyOutput.ERROR_UNDERLINED + "\nОшибка! Неверное количество переданных аргументов\n" +
                    "Ожидалось на вход:\n" + PrettyOutput.CHOOSE + "class: " + PrettyOutput.COMMENT + Matrix.class +
                    PrettyOutput.CHOOSE + " Описание: " + PrettyOutput.COMMENT + "матрица " +
                    "с которой суммируем / на которую умножаем\n" + PrettyOutput.RESET);
        } else if (!InputStreamParser.isClassesInListAtOnce(Arrays.stream(arguments).toList(), Double.class)) {
            throw new RuntimeException(PrettyOutput.ERROR_UNDERLINED + "\nОшибка! Неверное количество переданных аргументов\n" +
                    "Ожидалось на вход:\n" + PrettyOutput.CHOOSE + "class: " + PrettyOutput.COMMENT + Double.class +
                    PrettyOutput.CHOOSE + " Описание: " + PrettyOutput.COMMENT + "константа на которую умножаем\n" + PrettyOutput.RESET);
        }
        double constant = 1;
        Matrix matrix = new Matrix();
        OperationsType type = null;
        for (var argument : arguments) {
            if (argument instanceof Matrix) {
                matrix = (Matrix) argument;
            } if (argument instanceof OperationsType) {
                type = (OperationsType) argument;
            } if (argument instanceof Double) {
                constant = (Double) argument;
            }
        }
        if (type == OperationsType.ADDITION) {
            if (matrix.getRows() != this.getRows() && matrix.getColumns() != this.getColumns())
                throw new RuntimeException(PrettyOutput.ERROR + "Размеры матриц не совпадают \n" + PrettyOutput.COMMENT +
                        "Пожалуйста, введите матрицы одного размера" + PrettyOutput.RESET);
        } else if (type == OperationsType.MULTIPLY) {
            if (this.getColumns() != matrix.getRows())
                throw new RuntimeException(PrettyOutput.ERROR + "Размеры матриц не совпадают \n" + PrettyOutput.COMMENT +
                        "Пожалуйста, убедитесь, что количество столбцов первой матрицы " +
                        "равно количеству строк второй матрицы" + PrettyOutput.RESET);
        }
    }
    @Override
    public AbstractMatrix<? extends Number> add(Object... arguments) {
        return null;
    }
    @Override
    public AbstractMatrix<? extends Number> multiply(Object... arguments) {
        return null;
    }
    @Override
    public AbstractMatrix<? extends Number> constMultiply(Object... arguments) {
        return null;
    }

    public Matrix matrixPow(int pow) throws ReflectiveOperationException, IOException {
        Matrix matrixPow = this.copy();
        for (int i = 0; i < pow - 1; i++)
            matrixPow = ((Matrix) this.multiply(matrixPow)).copy();
        return matrixPow;
    }
    public Matrix multiply(Vector vector) throws ReflectiveOperationException, IOException {
        Matrix vectorToMatrix = vector.toMatrix();
        return (Matrix) this.multiply(vectorToMatrix);
    }
    public Vector toVector() throws ReflectiveOperationException, IOException {
        if (this.columns > 1)
            throw new RuntimeException(PrettyOutput.ERROR +
                    "Преобразование из матрицы в вектор невозможно." + PrettyOutput.RESET);
        Vector convertVector = new Vector();
        this.getMatrix().forEach(row -> convertVector.addElement(row.getElementAt(0)));
        return convertVector;
    }
    public AlgebraicSystem makeAlgebraicSystem(Vector addjoinVector) throws ReflectiveOperationException, IOException {
        return new AlgebraicSystem(this, addjoinVector);
    }
    public Matrix transposition() throws ReflectiveOperationException, IOException {
        Double[][] transposedMatrix = new Double[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++)
                transposedMatrix[i][j] = getElementAt(j, i);
        }
        return new Matrix(transposedMatrix);
    }
    public Matrix gaussianTransform() throws ReflectiveOperationException, IOException {
        Matrix cloneMatrix = this.copy();
        for (int k = 0; k < cloneMatrix.getRows() - 1; k++)
            for (int i = k + 1; i < cloneMatrix.getRows(); i++) {
                if (cloneMatrix.getElementAt(k, k) == 0) {
                    for (int l = i; l < cloneMatrix.getRows(); l++) {
                        if (cloneMatrix.getElementAt(l, l) != 0) {cloneMatrix.swapRow(k, l);}
                        else throw new RuntimeException(PrettyOutput.ERROR +
                                "Ошибка! Деление на ноль, невозможно посчитать определитель" + PrettyOutput.RESET);
                    }
                }
                double tmp = cloneMatrix.getElementAt(i, k) / cloneMatrix.getElementAt(k, k);
                for (int j = k; j < cloneMatrix.getColumns(); j++)
                    cloneMatrix.setElementAt(
                            cloneMatrix.getElementAt(i, j) - tmp * cloneMatrix.getElementAt(k, j), i, j);
            }
        return cloneMatrix;
    }
    public double gaussianDeterminant() throws ReflectiveOperationException, IOException {
        Double[][] copyMatrix = new Double[getRows()][getColumns()];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                copyMatrix[i][j] = getElementAt(i, j);
            }
        }
        Matrix tempMatrix = new Matrix(copyMatrix);
        double determinant = 1;
        for (int k = 0; k < getRows() - 1; k++)
            for (int i = k + 1; i < getRows(); i++)
            {
                if (tempMatrix.getElementAt(k, k) == 0)
                {
                    for (int l = i; l < getRows(); l++)
                    {
                        if (tempMatrix.getElementAt(l, l) != 0) {tempMatrix.swapRow(k, l); determinant *= -1;}
                        else if (tempMatrix.getElementAt(l, l) == 0 && l == getRows() - 1)
                            throw new RuntimeException(PrettyOutput.ERROR + "Ошибка! Деление на ноль, невозможно посчитать определитель" + PrettyOutput.RESET);
                    }
                }
                double tmp = tempMatrix.getElementAt(i, k) / tempMatrix.getElementAt(k, k);
                for (int j = k; j < columns; j++)
                    tempMatrix.setElementAt(
                            tempMatrix.getElementAt(i, j) - tmp * tempMatrix.getElementAt(k, j), i, j);
            }
        for (int i = 0; i < getRows(); i++)
        { determinant *= tempMatrix.getElementAt(i, i); }
        return Math.round(determinant);
    }
    public double ChebyshevNorm() {
        AtomicReference<Double> result = new AtomicReference<>(Double.MIN_VALUE);
        this.matrix.forEach(row -> {
            result.set(Math.min(result.get(), row.getVector().stream().reduce(Double::sum).get()));
        });
        return result.get();
    }
    public boolean isNanMatrix() {
        for (var row : this.matrix)
            for (var element : row.getVector())
                if (Double.isNaN(element))
                    return true;
        return false;
    }
}
