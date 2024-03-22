package LinearAlgebra;

import java.io.*;
import java.text.*;
import java.util.*;
import OtherThings.*;

public class Vector {
    protected double[] vector;
    protected int vectorSize;
    public Vector(String pathToFile) throws FileNotFoundException
    {
        File input = new File(pathToFile);
        Scanner scan = new Scanner(input);
        String line = scan.nextLine();
        String[] strArr = line.trim().split("\\s+");

        this.vectorSize = strArr.length;
        this.vector = new double[this.vectorSize];
        for (int i = 0; i < strArr.length; i++)
            this.vector[i] = Double.parseDouble(strArr[i]);
    }
    public Vector(double[] vector, int vectorSize)
    {
        this.vector = vector;
        this.vectorSize = vectorSize;
    }
    public Vector(int vectorSize)
    {
        this.vectorSize = vectorSize;
        this.vector = new double[vectorSize];
        for (int i = 0; i < this.vectorSize; i++)
            this.setItem(i, Double.NaN);
    }
    public Vector()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println(PrettyOutput.INPUT + "Введите размер вектора:" + PrettyOutput.RESET);
        int vectorSize = scan.nextInt();

        this.vectorSize = vectorSize;
        this.vector = new double[vectorSize];
    }
    protected void initializeVector()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println(PrettyOutput.INPUT + "Введите элементы вектора размерностью " + vectorSize + ":" + PrettyOutput.RESET);
        for (int i = 0; i < vectorSize; i++)
        {
            int a = scan.nextInt();
            this.vector[i] = a;
        }
    }
    protected void initializeRandomVector(double from, double to)
    {
        Random random = new Random();
        for (int i = 0; i < this.vectorSize; i++)
            this.vector[i] = random.nextDouble(from, to);
    }
    protected void initializeRandomIntVector(int from, int to)
    {
        Random random = new Random();
        for (int i = 0; i < this.vectorSize; i++)
            this.vector[i] = random.nextInt(from, to);
    }
    public int getVectorSize() { return this.vectorSize; }
    public double[] getVector() { return this.vector; }
    public double getItem(int index) { return this.vector[index]; }
    public void setItem(int index, double replaceItem)
    { this.vector[index] = replaceItem; }
    public void setVector(double[] vector)
    {
        System.out.println(PrettyOutput.CHOOSE + "Вы уверены, что хотите заменить вектор?" + PrettyOutput.RESET);
        this.vector = vector;
    }
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    Vector cloneVector()
    {
        Vector cloneVector = new Vector(this.vectorSize);
        for (int i = 0; i < this.vectorSize; i++)
            cloneVector.setItem(i, this.getItem(i));
        return cloneVector;
    }
    public static Vector createNewVector()
    {
        Vector vector = new Vector();
        vector.initializeVector();
        return vector;
    }
    public static Vector createNewVector(int vectorSize)
    {
        Vector vector = new Vector(vectorSize);
        vector.initializeVector();
        return vector;
    }
    public static Vector createNewRandomIntVector(int from, int to)
    {
        Vector vector = new Vector();
        vector.initializeRandomIntVector(from, to);
        return vector;
    }
    public static Vector createNewRandomIntVector(int vectorSize, int from, int to)
    {
        Vector vector = new Vector(vectorSize);
        vector.initializeRandomIntVector(from, to);
        return vector;
    }
    public static Vector createNewRandomVector(double from, double to)
    {
        Vector vector = new Vector();
        vector.initializeRandomVector(from, to);
        return vector;
    }
    public static Vector createNewRandomVector(int vectorSize, double from, double to)
    {
        Vector vector = new Vector(vectorSize);
        vector.initializeRandomVector(from, to);
        return vector;
    }
    public void printVector()
    {
        System.out.print(PrettyOutput.HEADER_OUTPUT + "\nВектор размерностью " + vectorSize + PrettyOutput.OUTPUT + ": \n { ");
        for (int i = 0; i < vectorSize; i++)
        {
            System.out.print(this.getItem(i) + "; ");
        }
        System.out.println("}" + PrettyOutput.RESET);
    }
    public void printFormattedVector()
    {
        System.out.print(PrettyOutput.HEADER_OUTPUT + "\nВектор размерностью " + vectorSize + PrettyOutput.OUTPUT + ": \n { ");
        for (int i = 0; i < vectorSize; i++)
        {
            DecimalFormat shortOut = new DecimalFormat("#.##");
            String result = shortOut.format(this.getItem(i));
            System.out.print(result + "; ");
        }
        System.out.println("}" + PrettyOutput.RESET);
    }
    public void writeInFile(String pathToFile) throws IOException {
        File output = new File(pathToFile);
        FileWriter fileWriter = new FileWriter(output);
        if (output.exists())
        {
            for (int i = 0; i < this.vectorSize; i++)
                fileWriter.write(this.getItem(i) + " ");
        } else {
            boolean created = output.createNewFile();
            if (created)
            {
                for (int i = 0; i < this.vectorSize; i++)
                    fileWriter.write(this.getItem(i) + " ");
            }
            else
            { throw new RuntimeException(PrettyOutput.ERROR + "Файл не создан по указанному пути: " +
                    PrettyOutput.COMMENT + pathToFile + PrettyOutput.RESET); }
        }
        fileWriter.close();
    }
    public void writeFormattedInFile(String pathToFile) throws IOException {
        File output = new File(pathToFile);
        FileWriter fileWriter = new FileWriter(output);
        if (output.exists())
        {
            for (int i = 0; i < this.vectorSize; i++)
            {
                DecimalFormat shortOut = new DecimalFormat("#.##");
                String result = shortOut.format(this.getItem(i));
                fileWriter.write(result + " ");
            }
        } else {
            boolean created = output.createNewFile();
            if (created)
            {
                for (int i = 0; i < this.vectorSize; i++)
                {
                    DecimalFormat shortOut = new DecimalFormat("#.##");
                    String result = shortOut.format(this.getItem(i));
                    fileWriter.write(result + " ");
                }
            }
            else
            { throw new RuntimeException(PrettyOutput.ERROR + "Файл не создан по указанному пути: " +
                    PrettyOutput.COMMENT + pathToFile + PrettyOutput.RESET); }
        }
        fileWriter.close();
    }
    public void writeInDesiredFolder(String pathToFolder) throws IOException {
        String pathToFile = pathToFolder + "/output.txt";
        this.writeInFile(pathToFile);
    }
    public void writeInDesiredFolder(String pathToFolder, String fileName) throws IOException {
        String pathToFile = pathToFolder + "/" + fileName;
        this.writeInFile(pathToFile);
    }
    public void writeFormattedInDesiredFolder(String pathToFolder) throws IOException {
        String pathToFile = pathToFolder + "/output.txt";
        this.writeFormattedInFile(pathToFile);
    }
    public void writeFormattedInDesiredFolder(String pathToFolder, String fileName) throws IOException {
        String pathToFile = pathToFolder + "/" + fileName;
        this.writeFormattedInFile(pathToFile);
    }
    public void addItem(double item)
    {
        this.vectorSize ++;
        double[] newVector = new double[this.vectorSize];
        for (int i = 0; i < this.vectorSize; i++)
        {
            if (i != this.vectorSize - 1)
                newVector[i] = this.getItem(i);
            else
                newVector[i] = item;
        }
        this.vector = newVector;
    }
    public void addItemBefore(double item, int index)
    {
        this.vectorSize ++;
        double[] newVector = new double[this.vectorSize];
        newVector[index] = item;
        for (int i = 0; i < this.vectorSize; i++)
        {
            if (i < index)
                newVector[i] = this.getItem(i);
            else if (i > index)
                newVector[i] = this.getItem(i - 1);
        }
        this.vector = newVector;
    }
    public void addItemAfter(double item, int index)
    {
        this.vectorSize ++;
        double[] newVector = new double[this.vectorSize];
        newVector[index + 1] = item;
        for (int i = 0; i < this.vectorSize; i++)
        {
            if (i < index + 1)
                newVector[i] = this.getItem(i);
            else if (i > index + 1)
                newVector[i] = this.getItem(i - 1);
        }
        this.vector = newVector;
    }
    public Vector partOfVector(int leftBorder, int rightBorder)
    {
        int newVectorSize = rightBorder - leftBorder + 1;
        double[] vectorPart = new double[newVectorSize];
        for (int oldIndex = leftBorder, newIndex = 0; oldIndex < rightBorder + 1; oldIndex++, newIndex++)
            vectorPart[newIndex] = this.getItem(oldIndex);
        return new Vector(vectorPart, newVectorSize);
    }
    public Vector constantMultiplication(double constant)
    {
        double[] newVector = this.vector;
        for (int i = 0; i < this.vectorSize; i++)
            newVector[i] *= constant;
        return new Vector(newVector,this.vectorSize);
    }
    public Vector vectorAddition(Vector addVector)
    {
        if (this.vectorSize != addVector.getVectorSize())
            throw new RuntimeException(PrettyOutput.ERROR + "Размеры векторов разные \n" + PrettyOutput.COMMENT + "Пожалуйста, введите вектора одного размера" + PrettyOutput.RESET);
        else
        {
            double[] newVector = this.vector;
            for (int i = 0; i < this.vectorSize; i++)
                newVector[i] = this.getItem(i) + addVector.getItem(i);
            return new Vector(newVector,this.vectorSize);
        }
    }
    public Vector vectorDifference(Vector subtractVector)
    {
        subtractVector = subtractVector.constantMultiplication(-1);
        Vector resultVector;
        resultVector = this.vectorAddition(subtractVector);
        return resultVector;
    }
    public boolean isInVector(double item)
    {
        for (double i : this.vector)
            if (i == item) return true;
        return false;
    }
    public Matrix vectorToMatrix()
    {
        double[][] convertMatrix = new double[this.vectorSize][1];
        for (int i = 0; i < this.vectorSize; i++)
            convertMatrix[i][0] = this.getItem(i);
        return new Matrix(convertMatrix, this.vectorSize, 1);
    }
    public double ChebyshevNorm()
    {
        double result = 0;
        for (double i : this.vector)
            result = Math.max(Math.abs(i), Math.abs(result));
        return result;
    }
    public void sort()
    { Arrays.sort(this.vector); }
    public boolean isZeroVector()
    {
        for (double item : this.vector)
            if (item != 0 && Math.abs(item) > 1E-10)
                return false;
        return true;
    }
}
