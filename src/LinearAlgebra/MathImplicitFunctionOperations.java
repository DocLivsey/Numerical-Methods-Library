package LinearAlgebra;

import OtherThings.PrettyOutput;

import java.io.*;
import java.util.*;
public class MathImplicitFunctionOperations extends MathBase {
    protected double epsilon = 1E-10;
    protected int dimension;
    protected ArrayList<PointMultiD> points;
    protected MathImplicitFunction function;
    public MathImplicitFunctionOperations(String pathToParametersFile, String pathToPoints, ArrayList<PointMultiD> points,
                                          int dimension, MathImplicitFunction function) throws IOException {
        this.dimension = dimension;
        if (pathToParametersFile != null)
            this.setEpsilon(pathToParametersFile);
        if (pathToPoints != null) {
            this.points = new ArrayList<>();
            this.readPointsFromFile(pathToPoints);
        } else this.points = Objects.requireNonNullElseGet(points, ArrayList::new);
        if (function != null) {
            this.function = function;
            if (!this.points.isEmpty()) {
                for (int i = 0; i < this.points.size(); i++) {
                    Vector x = this.getPoint(i).getVectorX();
                    this.setPoint(i, this.calculatePoint(x));
                }
            }
        } else this.function = (x) -> new PointMultiD(new Vector(this.dimension), Double.NaN);
    }
    public MathImplicitFunctionOperations(String pathToParametersFile, String pathToPoints, int dimension,
                                          MathImplicitFunction function) throws IOException {
        this(pathToParametersFile, pathToPoints, null, dimension, function);
    }
    public MathImplicitFunctionOperations(String pathToPoints, int dimension,
                                          MathImplicitFunction function) throws IOException {
        this(null, pathToPoints, dimension, function);
    }
    public MathImplicitFunctionOperations(String pathToParametersFile, ArrayList<PointMultiD> points, int dimension,
                                          MathImplicitFunction function) throws IOException {
        this(pathToParametersFile, null, points, dimension, function);
    }
    public MathImplicitFunctionOperations(ArrayList<PointMultiD> points, int dimension,
                                          MathImplicitFunction function) throws IOException {
        this(null, points, dimension, function);
    }
    public  MathImplicitFunctionOperations(String pathToParametersFile, String pathToPoints,
                                           int dimension) throws IOException {
        this(pathToParametersFile, pathToPoints, null, dimension, null);
    }
    public  MathImplicitFunctionOperations(String pathToPoints, int dimension) throws IOException {
        this(null, pathToPoints, dimension);
    }
    public MathImplicitFunctionOperations(String pathToParametersFile, int dimension,
                                          ArrayList<PointMultiD> points) throws IOException {
        this(pathToParametersFile, null, points, dimension, null);
    }
    public MathImplicitFunctionOperations(ArrayList<PointMultiD> points, int dimension) throws IOException {
        this(null, dimension, points);
    }
    public MathImplicitFunctionOperations(String pathToParametersFile) throws IOException {
        this(pathToParametersFile, null, 1);
    }
    public MathImplicitFunctionOperations() throws IOException {
        this(null);
    }
    public double getEpsilon() { return epsilon; }
    public int getDimension() { return this.dimension; }
    public ArrayList<PointMultiD> getPoints() { return this.points; }
    public PointMultiD getPoint(int index) { return this.points.get(index); }
    public MathImplicitFunction getFunction() { return this.function; }

    public void setEpsilon(String pathToParametersFile) throws IOException {
        this.epsilon = super.getVariablesTable(pathToParametersFile).get("epsilon");
    }
    public void setDimension(int dimension) { this.dimension = dimension; }
    public void setPoints(ArrayList<PointMultiD> points) { this.points = points; }
    public void setPoint(int index, PointMultiD point) { this.points.set(index, point); }
    public void setFunction(MathImplicitFunction function) { this.function = function; }
    public void addPoint(PointMultiD point) { this.points.add(point); }
    public void addPoints(ArrayList<PointMultiD> points)
    {
        for (PointMultiD point : points)
            this.addPoint(point);
        this.sortPoints();
    }
    public void sortPoints() { this.points.sort(new PointNormComparator()); }
    @Override
    public boolean equals(Object obj)
    { return super.equals(obj); }
    @Override
    protected MathImplicitFunctionOperations clone() throws CloneNotSupportedException
    { return (MathImplicitFunctionOperations) super.clone(); }
    public void readPointsFromFile(String pathToFile) throws FileNotFoundException
    {
        File input = new File(pathToFile);
        Scanner fileScan = new Scanner(input);
        while (fileScan.hasNextLine())
        {
            String point = fileScan.nextLine();
            PointMultiD pointMultiD = new PointMultiD(point, this.dimension + 1);
            this.addPoint(pointMultiD);
        }
    }
    public void printPoints()
    {
        System.out.println(PrettyOutput.HEADER_OUTPUT + "Точки Функции:" + PrettyOutput.RESET);
        for (PointMultiD point : this.points)
            point.print();
    }
    public void printFunction()
    { System.out.println(PrettyOutput.HEADER_OUTPUT + "Функция\n" + PrettyOutput.OUTPUT
            + this.toString() + PrettyOutput.RESET); }
    public PointMultiD calculatePoint(Vector x)
    {
        if (Math.abs(this.function.function(x).getY()) < epsilon)
            return new PointMultiD(x, 0);
        if (Math.abs(this.function.function(x).getY()) == Double.POSITIVE_INFINITY)
            return  new PointMultiD(x, 1 / epsilon);
        return function.function(x);
    }
    public void printPartialDifferential(int index)
    {
        System.out.println(PrettyOutput.HEADER_OUTPUT + "Частная Производная Функции по переменной x"
                + index + " в каждой точке:" + PrettyOutput.RESET);
        for (PointMultiD point : this.points)
            new PointMultiD(point.getVectorX(), this.partialDifferential(index, point)).print();
    }
    public double partialDifferential(int variableIndex, PointMultiD point)
    {
        Vector dx = point.getVectorX().cloneVector();
        dx.setItem(variableIndex, point.getX(variableIndex) + epsilon);
        double dy;
        if (Double.isNaN(point.getY()))
            dy = this.calculatePoint(dx).getY() - this.calculatePoint(point.getVectorX()).getY();
        else
            dy = this.calculatePoint(dx).getY() - point.getY();
        return dy / epsilon;
    }
    public double fullDifferential()
    { return 0; }
}
