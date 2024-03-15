package LinearAlgebra;

import java.io.*;
import java.text.*;
import java.util.*;
import OtherThings.*;

public class Matrix {
    protected int rowsCount;
    protected int columnsCount;
    protected double[][] matrix;
    Matrix(String pathToFile) throws FileNotFoundException
    {
        File input = new File(pathToFile);
        Scanner whileScan = new Scanner(input);
        String line = whileScan.nextLine();
        String[] strArr = line.trim().split("\\s+");
        double[] row;

        this.rowsCount = 1;
        this.columnsCount = strArr.length;
        this.matrix = new double[this.rowsCount][this.columnsCount];


        while (whileScan.hasNextLine())
        {
            line = whileScan.nextLine();
            strArr = line.trim().split("\\s+");
            row = new double[strArr.length];
            for (int i = 0; i < strArr.length; i++)
                row[i] = Double.parseDouble(strArr[i]);
            this.addRow(row);
        }
        Scanner finalScan = new Scanner(input);
        line = finalScan.nextLine();
        strArr = line.trim().split("\\s+");
        row = new double[strArr.length];
        for (int i = 0; i < strArr.length; i++)
            row[i] = Double.parseDouble(strArr[i]);
        this.setRow(row, 0);
    }
    Matrix()
    {
        System.out.println(PrettyOutput.INPUT + "Введите количество строк и столбцов в матрице:" + PrettyOutput.RESET);
        Scanner scan = new Scanner(System.in);
        int rowsCount = scan.nextInt();
        int columnsCount = scan.nextInt();
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
        this.matrix = new double[rowsCount][columnsCount];
    }
    Matrix(int rowsCount, int columnsCount)
    {
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
        this.matrix = new double[rowsCount][columnsCount];
        for (int i = 0; i < this.rowsCount; i++)
            for (int j = 0; j < this.columnsCount; j++)
                this.setItem(i, j, Double.NaN);
    }
    Matrix(double[][] matrix, int rowsCount, int columnsCount)
    {
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
        this.matrix = matrix;
    }
    int getRowsCount()
    { return this.rowsCount; }
    int getColumnsCount()
    { return this.columnsCount; }
    double[][] getMatrix()
    { return this.matrix; }
    double getItem (int indexRow, int indexColumn)
    { return this.matrix[indexRow][indexColumn]; }
    void setMatrix(double[][] matrix)
    { this.matrix = matrix; }
    void setItem(int rowIndex, int colIndex, double replaceItem)
    { this.matrix[rowIndex][colIndex] = replaceItem; }
    void setRow(double[] row, int index)
    {
        if (this.columnsCount != row.length)
        { System.out.println(PrettyOutput.ERROR + "Невозможно заменить строку в исходной матрице из-за несоответсвия количества столбцов" + PrettyOutput.RESET); }
        else
            this.matrix[index] = row;
    }
    void setColumn(double[] column, int index)
    {
        if (this.rowsCount != column.length)
        { System.out.println(PrettyOutput.ERROR + "Невозможно заменить столбец в исходной матрице из-за несоответсвия количества столбцов" + PrettyOutput.RESET); }
        else
            for (int i = 0; i < this.rowsCount; i++)
                this.setItem(i, index, column[i]);
    }
    public boolean equals(Object obj)
    { return super.equals(obj); }
    Matrix cloneMatrix()
    {
        Matrix cloneMatrix = new Matrix(this.rowsCount, this.columnsCount);
        for (int i = 0; i < this.rowsCount; i++)
            for (int j = 0; j < this.columnsCount; j++)
                cloneMatrix.setItem(i, j, this.getItem(i, j));
        return cloneMatrix;
    }
    void initializeMatrix()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println(PrettyOutput.INPUT + "Введите элементы матрицы " + rowsCount + " на " + columnsCount + ":" + PrettyOutput.RESET);
        for(int i = 0; i < this.rowsCount; i++)
            for (int j = 0; j < this.columnsCount; j++)
            {
                double a = scan.nextDouble();
                this.setItem(i, j, a);
            }
    }
    void initializeRandomMatrix(double from, double to)
    {
        Random random = new Random();
        for (int i = 0; i < this.rowsCount; i++)
            for (int j = 0; j < this.columnsCount; j++)
                this.setItem(i, j, random.nextDouble(from, to));
    }
    void initializeRandomIntMatrix(int from, int to)
    {
        Random random = new Random();
        for (int i = 0; i < this.rowsCount; i++)
            for (int j = 0; j < this.columnsCount; j++)
                this.setItem(i, j, random.nextInt(from, to));
    }
    public static Matrix createNewMatrix()
    {
        Matrix matrix = new Matrix();
        matrix.initializeMatrix();
        return matrix;
    }
    public static Matrix createNewMatrix(int rowsCount, int columnsCount)
    {
        Matrix matrix = new Matrix(rowsCount, columnsCount);
        matrix.initializeMatrix();
        return matrix;
    }
    public static Matrix createNewRandomIntMatrix(int from, int to)
    {
        Matrix matrix = new Matrix();
        matrix.initializeRandomIntMatrix(from, to);
        return matrix;
    }
    public static Matrix createNewRandomIntMatrix(int rowsCount, int columnsCount, int from, int to)
    {
        Matrix matrix = new Matrix(rowsCount, columnsCount);
        matrix.initializeRandomIntMatrix(from, to);
        return matrix;
    }
    public static Matrix createNewRandomMatrix(double from, double to)
    {
        Matrix matrix = new Matrix();
        matrix.initializeRandomMatrix(from, to);
        return matrix;
    }
    public static Matrix createNewRandomMatrix(int rowsCount, int columnsCount, double from, double to)
    {
        Matrix matrix = new Matrix(rowsCount, columnsCount);
        matrix.initializeRandomMatrix(from, to);
        return matrix;
    }
    void printMatrix()
    {
        System.out.println(PrettyOutput.HEADER_OUTPUT + "Матрица " + rowsCount + " на " + columnsCount + ":" + PrettyOutput.OUTPUT);
        for(int i = 0; i < this.rowsCount; i++)
        {
            for (int j = 0; j < this.columnsCount; j++)
            {
                System.out.print(this.getItem(i, j) + " ");
            }
            System.out.println();
        }
        System.out.print(PrettyOutput.RESET);
    }
    void printFormattedMatrix()
    {
        System.out.println(PrettyOutput.HEADER_OUTPUT + "Матрица " + rowsCount + " на " + columnsCount + ":" + PrettyOutput.OUTPUT);
        for(int i = 0; i < this.rowsCount; i++)
        {
            for (int j = 0; j < this.columnsCount; j++)
            {
                DecimalFormat formattedOut = new DecimalFormat("#.##");
                String result = formattedOut.format(this.getItem(i, j));
                System.out.print(result + " ");
            }
            System.out.println();
        }
        System.out.print(PrettyOutput.RESET);
    }
    // REMAKE write in file methods
    void writeInFile(String pathToFile) throws IOException
    {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(pathToFile));
        for (int i = 0; i < this.rowsCount; i++)
        {
            for (int j = 0; j < this.columnsCount; j++)
            {
                DecimalFormat formattedOut = new DecimalFormat("#.##");
                String result = formattedOut.format(this.getItem(i, j));
                fileWriter.write(result + " ");
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }
    void writeFormattedInFile(String pathToFile) throws IOException
    {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(pathToFile));
        for (int i = 0; i < this.rowsCount; i++)
        {
            for (int j = 0; j < this.columnsCount; j++)
            {
                fileWriter.write(this.getItem(i, j) + " ");
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }
    void addRow(double[] row)
    {
        if (row.length != this.columnsCount)
        { System.out.println(PrettyOutput.ERROR + "Невозможно добавить строку в исходную матрицу из-за несоответсвия количества столбцов" + PrettyOutput.RESET); }
        else
        {
            this.rowsCount ++;
            double[][] newMatrix = new double[this.rowsCount][this.columnsCount];
            for (int i = 0; i < this.rowsCount; i++)
            {
                if (i != this.rowsCount - 1)
                    System.arraycopy(this.matrix[i], 0, newMatrix[i], 0, this.columnsCount);
                else newMatrix[i] = row;
            }
            this.matrix = newMatrix;
        }
    }
    void addRowAfter (double[] row, int index)
    {
        if (row.length != this.columnsCount)
        { System.out.println(PrettyOutput.ERROR + "Невозможно добавить строку в исходную матрицу из-за несоответсвия количества столбцов" + PrettyOutput.RESET); }
        else
        {
            this.rowsCount ++;
            double[][] newMatrix = new double[this.rowsCount][this.columnsCount];
            newMatrix[index + 1] = row;
            for (int i = 0; i < this.rowsCount; i++)
            {
                if (i < index + 1)
                    newMatrix[i] = this.matrix[i];
                else if (i > index + 1)
                    newMatrix[i] = this.matrix[i - 1];
            }
            this.matrix = newMatrix;
        }
    }
    void deleteRow(int index)
    {
        if (index > this.rowsCount || index < 0)
        { System.out.println(PrettyOutput.ERROR + "Номер строки указан неверно" + PrettyOutput.RESET); }
        else
        {
            Matrix newMatrix = new Matrix(this.rowsCount - 1, this.columnsCount);
            int tmpIndex = 0;
            for (int i = 0; i < this.rowsCount; i++)
                if (i != index)
                {
                    newMatrix.setRow(this.matrix[i], tmpIndex);
                    tmpIndex++;
                }
            this.rowsCount --;
            this.matrix = newMatrix.getMatrix();
        }
    }
    void addColumn(double[] column)
    {
        if (column.length != this.rowsCount)
        { System.out.println(PrettyOutput.ERROR + "Невозможно добавить столбец в исходную матрицу из-за несоответсвия количества строк" + PrettyOutput.RESET); }
        else
        {
            this.columnsCount ++;
            double[][] newMatrix = new double[this.rowsCount][this.columnsCount];
            for (int i = 0; i < this.rowsCount; i++)
                for (int j = 0; j < this.columnsCount; j++)
                {
                    if (j != this.columnsCount - 1)
                        newMatrix[i][j] = this.getItem(i, j);
                    else newMatrix[i][j] = column[i];
                }
            this.matrix = newMatrix;
        }
    }
    void addColumnAfter (double[] column, int index)
    {
        if (column.length != this.rowsCount)
        { System.out.println(PrettyOutput.ERROR + "Невозможно добавить столбец в исходную матрицу из-за несоответсвия количества строк" + PrettyOutput.RESET); }
        else
        {
            this.columnsCount ++;
            double[][] newMatrix = new double[this.rowsCount][this.columnsCount];
            for (int i = 0; i < this.rowsCount; i++)
            {
                newMatrix[i][index + 1] = column[i];
                for (int j = 0; j < this.columnsCount; j++)
                {
                    if (j < index + 1)
                        newMatrix[i][j] = this.getItem(i, j);
                    else if (j > index + 1)
                        newMatrix[i][j] = this.getItem(i, j - 1);
                }
            }
            this.matrix = newMatrix;
        }
    }
    void deleteColumn(int index)
    {
        if (index > this.columnsCount || index < 0)
        { System.out.println(PrettyOutput.ERROR + "Номер столбца указан неверно" + PrettyOutput.RESET); }
        else
        {
            Matrix newMatrix = new Matrix(this.rowsCount, this.columnsCount - 1);
            int tmpIndex = 0;
            for (int i = 0; i < this.columnsCount; i++)
                if (i != index)
                {
                    double[] tmpCol = new double[this.rowsCount];
                    for (int j = 0; j < this.rowsCount; j++)
                        tmpCol[j] = this.getItem(j, i);
                    newMatrix.setColumn(tmpCol, tmpIndex);
                    tmpIndex++;
                }
            this.columnsCount --;
            this.matrix = newMatrix.getMatrix();
        }
    }
    Matrix partOfMatrix(int leftBorder, int rightBorder, int upBorder, int downBorder)
    {
        int newRowsCount = downBorder - upBorder + 1;
        int newColumnsCount = rightBorder - leftBorder + 1;
        double[][] matrixPart = new double[newRowsCount][newColumnsCount];
        for (int oldRow = upBorder, newRow = 0; oldRow < downBorder + 1; oldRow++, newRow++)
            for (int oldCol = leftBorder, newCol = 0; oldCol < rightBorder + 1; oldCol++, newCol++)
                matrixPart[newRow][newCol] = this.getItem(oldRow, oldCol);
        return new Matrix(matrixPart, newRowsCount, newColumnsCount);
    }
    Matrix matrixAddition(Matrix addMatrix)
    {
        if (this.rowsCount != addMatrix.rowsCount || this.columnsCount != addMatrix.columnsCount)
        {
            System.out.println(PrettyOutput.ERROR + "Размеры матриц разные \n" + PrettyOutput.COMMENT + "Пожалуйста, введите матрицы одного размера" + PrettyOutput.RESET);
            return null;
        }
        else
        {
            Matrix resultMatrix = new Matrix(this.rowsCount, addMatrix.columnsCount);
            for(int i = 0; i < resultMatrix.getRowsCount(); i++)
                for(int j = 0; j < resultMatrix.getColumnsCount(); j++)
                { resultMatrix.setItem(i, j, this.getItem(i, j) + addMatrix.getItem(i, j)); }
            return resultMatrix;
        }
    }
    Matrix constantMultiplication(double constant)
    {
        double[][] newMatrix = this.matrix;
        for (int i = 0; i < this.rowsCount; i++)
            for (int j = 0; j < this.columnsCount; j++)
            { newMatrix[i][j] *= constant; }
        return new Matrix(newMatrix, this.rowsCount, this.columnsCount);
    }
    Matrix matrixMultiplication(Matrix addMatrix)
    {
        if (this.columnsCount != addMatrix.getRowsCount())
        {
            System.out.println(PrettyOutput.ERROR + "Размеры матриц разные \n" + PrettyOutput.COMMENT +
                    "Пожалуйста, убедитесь, что количество столбцов первой матрицы равно количеству строк второй матрицы" + PrettyOutput.RESET);
            return null;
        }
        else
        {
            Matrix resultMatrix = new Matrix(this.rowsCount, addMatrix.getColumnsCount());
            for (int i = 0; i < this.rowsCount; i++)
            {
                resultMatrix.getMatrix()[i] = new double[addMatrix.getColumnsCount()];
                for (int j = 0; j < addMatrix.getColumnsCount(); j++)
                {
                    resultMatrix.setItem(i, j, 0);

                    for (int k = 0; k < addMatrix.getRowsCount(); k++)
                    { resultMatrix.getMatrix()[i][j] += this.getItem(i, k) * addMatrix.getItem(k, j); }
                }
            }
            return resultMatrix;
        }
    }
    Vector matrixAndVectorMultiplication(Vector addVector)
    {
        Matrix vectorMatrix = addVector.vectorToMatrix();
        Vector resultVector;
        resultVector = this.matrixMultiplication(vectorMatrix).matrixToVector();
        return resultVector;
    }
    Vector matrixToVector()
    {
        if (this.columnsCount != 1)
        {
            System.out.println(PrettyOutput.ERROR + "Преобразование из матрицы в вектор невозможно." + PrettyOutput.RESET);
            return null;
        }
        else
        {
            double[] convertVector = new double[this.rowsCount];
            for (int i = 0; i < this.rowsCount; i++)
                convertVector[i] = this.getItem(i, 0);
            return new Vector(convertVector, this.rowsCount);
        }
    }
    Matrix transposition()
    {
        double[][] transpositionMatrix = new double[this.columnsCount][this.rowsCount];
        for(int i = 0; i < this.columnsCount; i++)
            for (int j = 0; j < this.columnsCount; j++)
            { transpositionMatrix[i][j] = this.getItem(j, i); }
        return new Matrix(transpositionMatrix, this.columnsCount, this.rowsCount);
    }

    boolean isMatrixEqual(Matrix compareMatrix)
    {
        if (this.rowsCount != compareMatrix.getRowsCount() || this.columnsCount != compareMatrix.getColumnsCount())
        {
            System.out.println(PrettyOutput.ERROR + "Размеры матриц разные" + PrettyOutput.RESET);
            return false;
        }
        else
        {
            for (int i = 0; i < this.rowsCount; i++)
                for (int j = 0; j < this.columnsCount; j++)
                    if (this.getItem(i, j) != compareMatrix.getItem(i, j))
                        return false;
            return true;
        }
    }
    void swapRow(int indexChange, int indexChangeWith)
    {
        double[] tmpRow = this.matrix[indexChange];
        this.matrix[indexChange] = this.matrix[indexChangeWith];
        this.matrix[indexChangeWith] = tmpRow;
    }
    void swapColumn(int indexChange, int indexChangeWith)
    {
        for (int i = 0; i < this.rowsCount; i++)
        {
            double tmpCol = this.getItem(i, indexChange);
            this.setItem(i, indexChange, this.getItem(i, indexChangeWith));
            this.setItem(i, indexChangeWith, tmpCol);
        }
    }
    public Matrix gaussianTransform()
    {
        Matrix cloneMatrix = this.cloneMatrix();
        for (int k = 0; k < cloneMatrix.getRowsCount() - 1; k++)
            for (int i = k + 1; i < cloneMatrix.getRowsCount(); i++)
            {
                if (cloneMatrix.getItem(k, k) == 0)
                {
                    for (int l = i; l < cloneMatrix.getRowsCount(); l++)
                    {
                        if (cloneMatrix.getItem(l, l) != 0) {cloneMatrix.swapRow(k, l);}
                        else System.out.println(PrettyOutput.ERROR + "Ошибка! Деление на ноль, невозможно посчитать определитель" + PrettyOutput.RESET);
                    }
                }
                double tmp = cloneMatrix.getItem(i, k) / cloneMatrix.getItem(k, k);
                for (int j = k; j < cloneMatrix.getColumnsCount(); j++)
                { cloneMatrix.getMatrix()[i][j] -= tmp * cloneMatrix.getItem(k, j); }
            }
        return cloneMatrix;
    }
    double gaussianDeterminant()
    {
        double[][] copyMatrix = new double[this.rowsCount][this.columnsCount];
        for (int i = 0; i < this.rowsCount; i++)
            System.arraycopy(this.matrix[i], 0, copyMatrix[i], 0, this.columnsCount);
        Matrix tempMatrix = new Matrix(copyMatrix, this.rowsCount, this.columnsCount);
        double determinant = 1;
        for (int k = 0; k < rowsCount - 1; k++)
            for (int i = k + 1; i < rowsCount; i++)
            {
                if (tempMatrix.getItem(k, k) == 0)
                {
                    for (int l = i; l < this.rowsCount; l++)
                    {
                        if (tempMatrix.getItem(l, l) != 0) {tempMatrix.swapRow(k, l); determinant *= -1;}
                        else if (tempMatrix.getItem(l, l) == 0 && l == this.rowsCount - 1)
                            System.out.println(PrettyOutput.ERROR + "Ошибка! Деление на ноль, невозможно посчитать определитель" + PrettyOutput.RESET);
                    }
                }
                double tmp = tempMatrix.getItem(i, k) / tempMatrix.getItem(k, k);
                for (int j = k; j < columnsCount; j++)
                { tempMatrix.getMatrix()[i][j] -= tmp * tempMatrix.getItem(k, j); }
            }
        for (int i = 0; i < this.rowsCount; i++)
        { determinant *= tempMatrix.getItem(i, i); }
        return Math.round(determinant);
    }
    double matrix2By2Determinant()
    {
        if (this.rowsCount != 2 || this.columnsCount != 2)
        {
            System.out.println(PrettyOutput.ERROR + "Ошибка! Данный метод вычисления определителя работает только для матриц 2 на 2");
            return  0;
        }
        else
        {
            double firstTerm = this.getItem(0, 0) * this.getItem(1, 1);
            double secondTerm = this.getItem(0, 1) * this.getItem(1, 0);

            return firstTerm - secondTerm;
        }
    }
    double matrix3By3Determinant()
    {
        if (this.rowsCount != 3 || this.columnsCount != 3)
        {
            System.out.println(PrettyOutput.ERROR + "Ошибка! Данный метод вычисления определителя работает только для матриц 3 на 3");
            return  0;
        }
        else
        {
            double firstTerm = 1;
            for (int i = 0; i < this.rowsCount; i++)
                firstTerm *= this.getItem(i, i);
            double secondTerm = this.getItem(0, 1) * this.getItem(1, 2) * this.getItem(2, 0);
            double thirdTerm = this.getItem(0, 2) * this.getItem(1, 0) * this.getItem(2, 1);
            double fourthTerm = this.getItem(0, 0) * this.getItem(1, 2) * this.getItem(2, 1);
            double fifthTerm = this.getItem(0, 1) * this.getItem(1, 0) * this.getItem(2, 2);
            double sixthTerm = this.getItem(0, 2) * this.getItem(1,1) * this.getItem(2, 0);

            return firstTerm + secondTerm + thirdTerm - fourthTerm - fifthTerm - sixthTerm;
        }
    }
    double calculateDeterminant()
    {
        double determinant;
        if (this.rowsCount == 2 && this.columnsCount == 2)
            determinant = this.matrix2By2Determinant();
        else if (this.rowsCount == 3 && this.columnsCount == 3)
            determinant = this.matrix3By3Determinant();
        else
            determinant = this.gaussianDeterminant();
        return determinant;
    }
    Matrix setMinor(int rowIndex, int colIndex)
    {
        Matrix minor = this.cloneMatrix();
        minor.deleteRow(rowIndex);
        minor.deleteColumn(colIndex);
        return minor.cloneMatrix();
    }
    Matrix inversion()
    {
        Matrix inversiveMatrix = new Matrix(this.rowsCount, this.columnsCount);
        if (this.rowsCount != this.columnsCount)
            System.out.println(PrettyOutput.ERROR + "Ошибка! Матрица не квадратичная. Обратную невозможно посчитать" + PrettyOutput.RESET);
        else
        {
            double determinant = this.calculateDeterminant();
            if (determinant == 0)
                System.out.println(PrettyOutput.ERROR + "Заданная матрица Вырождена. Невозможно найти обратную" + PrettyOutput.RESET);
            else
            {
                for (int i = 0; i < this.rowsCount; i++)
                {
                    for (int j = 0; j < this.columnsCount; j++)
                    {
                        Matrix minor = this.setMinor(i, j);
                        double minorDet = minor.calculateDeterminant() * Math.pow(-1, (i + j));
                        inversiveMatrix.setItem(i, j, minorDet);
                    }
                }
                inversiveMatrix = inversiveMatrix.transposition();
                inversiveMatrix = inversiveMatrix.constantMultiplication(1 / determinant);
            }
        }
        return inversiveMatrix;
    }
    public boolean isNanMatrix()
    {
        for (double[] row : this.matrix)
            for (double item : row)
                if (Double.isNaN(item))
                    return true;
        return false;
    }
    public boolean isMatrixSingular()
    { return this.calculateDeterminant() == 0; }
}