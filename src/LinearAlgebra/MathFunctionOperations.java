package LinearAlgebra;

import java.io.*;
import java.util.*;
import OtherThings.*;
public class MathFunctionOperations extends MathBase {
    protected ArrayList<Double> arguments;
    protected ArrayList<Point2D> points;
    protected MathFunction mathFunction;
    public MathFunctionOperations(String pathToPoints, ArrayList<Double> arguments, MathFunction mathFunction) throws FileNotFoundException
    {
        this.arguments = arguments;
        this.points = new ArrayList<>();
        this.readPointsFromFile(pathToPoints);
        this.mathFunction = mathFunction;
        this.expandPointsArea();
        if (this.isAnyNullValuesInPoints())
            this.calculateNullValues();
    }
    public MathFunctionOperations(String pathToPoints, ArrayList<Double> arguments) throws FileNotFoundException
    {
        this.arguments = arguments;
        this.points = new ArrayList<>();
        this.readPointsFromFile(pathToPoints);
        this.expandPointsArea();
    }
    public MathFunctionOperations(ArrayList<Point2D> points, ArrayList<Double> arguments, MathFunction mathFunction)
    {
        this.arguments = arguments;
        this.points = points;
        this.mathFunction = mathFunction;
        this.expandPointsArea();
        if (this.isAnyNullValuesInPoints())
            this.calculateNullValues();
    }
    public MathFunctionOperations(ArrayList<Point2D> points)
    { this.points = points; this.expandPointsArea(); }
    public MathFunctionOperations()
    {
        this.points = new ArrayList<>();
        this.mathFunction = x -> new Point2D(Double.NaN, Double.NaN);
    }
    public ArrayList<Double> getArguments()
    { return arguments; }
    public ArrayList<Point2D> getPoints()
    { return this.points; }
    public MathFunction getMathFunction()
    { return mathFunction; }
    public double getArgument(int index)
    { return this.arguments.get(index); }
    public Point2D getPoint(int index)
    { return this.points.get(index); }
    public void setArguments(ArrayList<Double> arguments)
    { this.arguments = arguments; }
    public void setPoints(ArrayList<Point2D> points)
    { this.points = points; }
    public void setMathFunction(MathFunction mathFunction)
    { this.mathFunction = mathFunction; }
    public void setArgument(int index, double argument)
    { this.arguments.set(index, argument); }
    public void setPoint(int index, Point2D point2D)
    { this.points.set(index, point2D); }
    public void addPoint(Point2D point)
    { this.points.add(point); }
    public void addPoints(ArrayList<Point2D> points)
    {
        for (Point2D point : points)
            this.addPoint(point);
        this.sortPoints();
    }
    @Override
    public boolean equals(Object obj)
    { return super.equals(obj); }
    public MathFunctionOperations cloneMathFunction()
    { return new MathFunctionOperations(this.points, this.arguments, this.mathFunction); }
    public void sortPoints()
    { this.points.sort(Comparator.comparingDouble(Point2D::getX)); }
    public void printPoints()
    {
        System.out.println(PrettyOutput.HEADER_OUTPUT + "Точки Функции:" + PrettyOutput.RESET);
        for (Point2D point : this.points)
            point.print();
    }
    public void printDifferentials()
    {
        System.out.println(PrettyOutput.HEADER_OUTPUT + "Производная Функции в точках:" + PrettyOutput.RESET);
        for (Point2D point : this.points)
            new Point2D(point.getX(), this.differential(point)).print();
    }
    public void printFunction()
    { System.out.println(PrettyOutput.HEADER_OUTPUT + "Функция\n" + PrettyOutput.OUTPUT + super.toString() + PrettyOutput.RESET); }
    public Point2D calculatePoint(double x)
    {
        this.setArgument(0, x);
        if (Math.abs(this.mathFunction.function(this.arguments).getY()) < getEpsilon())
            return new Point2D(x, 0);
        if (Math.abs(this.mathFunction.function(this.arguments).getY()) == Double.POSITIVE_INFINITY
                || Double.isNaN(this.mathFunction.function(this.arguments).getY()))
            return  new Point2D(x, 1 / getEpsilon());
        return this.mathFunction.function(this.arguments);
    }
    public double differential(Point2D point)
    {
        double dx = point.getX() + getEpsilon();
        double dy = this.calculatePoint(dx).getY() - point.getY();
        return dy / getEpsilon();
    }
    public double calculateStep(double leftBorder, double rightBorder)
    { return (rightBorder - leftBorder) / 2; }
    protected void expandPointsArea()
    {
        double leftBorder = this.getPoint(0).getX();
        double rightBorder = this.getPoint(this.points.size() - 1).getX();
        while (this.points.size() < (rightBorder - leftBorder))
        {
            ArrayList<Point2D> addedPoints = new ArrayList<>();
            for (int i = 0; i < this.points.size() - 1; i++)
            {
                double varLeft = this.getPoint(i).getX();
                double varRight = this.getPoint(i + 1).getX();
                double step = this.calculateStep(varLeft, varRight);
                addedPoints.add(this.calculatePoint(varLeft + step));
            }
            this.addPoints(addedPoints);
        }
    }
    public boolean isAnyNullValuesInPoints()
    {
        for (Point2D point : this.points)
            if (Double.isNaN(point.getY()))
                return true;
        return false;
    }
    public void calculateNullValues()
    {
        for (int i = 0; i < this.points.size(); i++)
        {
            double x = this.getPoint(i).getX();
            double y = this.calculatePoint(x).getY();
            this.setPoint(i, new Point2D(x, y));
        }
    }
    public void readPointsFromFile(String pathToFile) throws FileNotFoundException {
        File input = new File(pathToFile);
        Scanner fileScan = new Scanner(input);
        while (fileScan.hasNextLine())
        {
            String pairLine = fileScan.nextLine();
            String[] pair = pairLine.trim().split("\\s+");
            if (pair.length == 1)
            {
                Point2D point2D = Point2D.setXFromString(pairLine);
                this.points.add(point2D);
            }
            else if (pair.length == 2)
            {
                Point2D point2D = Point2D.setPairFromString(pairLine);
                this.points.add(point2D);
            }
        }
    }
    public void writePointsInFile(String pathToFile) throws IOException {
        File pointsOutput = new File(pathToFile);
        if (pointsOutput.createNewFile())
        {
            try
            {
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(pointsOutput));
                for (Point2D point : this.points)
                    fileWriter.write(point + "\n");
            } catch (IOException e) { System.out.println(e.getMessage()); }
        } else if (pointsOutput.exists()) {
            try
            {
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(pointsOutput));
                for (Point2D point : this.points)
                    fileWriter.write(point + "\n");
            } catch (IOException e) { System.out.println(e.getMessage()); }
        } else
            throw new RuntimeException(PrettyOutput.ERROR + "Ошибка. Невозможно создать файл по заданному пути: " +
                   PrettyOutput.COMMENT + pathToFile + PrettyOutput.RESET);
    }
}