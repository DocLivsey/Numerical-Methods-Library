import java.io.*;
import java.util.*;
public class MathFunctionOperations extends MathBase {
    protected ArrayList<Point2D> points;
    protected MathFunction mathFunction;
    MathFunctionOperations(String pathToPoints, MathFunction mathFunction) throws FileNotFoundException
    {
        this.points = new ArrayList<>();
        this.readPointsFromFile(pathToPoints);
        this.mathFunction = mathFunction;
        this.expandPointsArea();

        for (int i = 0; i < this.points.size(); i++)
        {
            double x = this.getPoint(i).getX();
            double y = this.calculatePoint(this.getPoint(i).getX()).getY();
            this.setPoint(i, new Point2D(x, y));
        }
    }
    MathFunctionOperations(String pathToPoints) throws FileNotFoundException
    {
        this.points = new ArrayList<>();
        this.readPointsFromFile(pathToPoints);
    }
    MathFunctionOperations(ArrayList<Point2D> arguments, MathFunction mathFunction)
    {
        this.points = arguments;
        this.mathFunction = mathFunction;
        this.expandPointsArea();
    }
    MathFunctionOperations(ArrayList<Point2D> points)
    { this.points = points; }
    MathFunctionOperations()
    {
        this.points = new ArrayList<>();
        this.mathFunction = x -> new Point2D(Double.NaN, Double.NaN);
    }
    public ArrayList<Point2D> getPoints()
    { return this.points; }
    public MathFunction getMathFunction()
    { return mathFunction; }
    public Point2D getPoint(int index)
    { return this.points.get(index); }
    public void setPoints(ArrayList<Point2D> points)
    { this.points = points; }
    public void setMathFunction(MathFunction mathFunction)
    { this.mathFunction = mathFunction; }
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
    protected MathFunctionOperations cloneMathFunction()
    { return new MathFunctionOperations(this.points, this.mathFunction); }
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
        if (Math.abs(this.mathFunction.function(x).getY()) < super.getEpsilon())
            return new Point2D(x, 0);
        if (Math.abs(this.mathFunction.function(x).getY()) == Double.POSITIVE_INFINITY || Double.isNaN(this.mathFunction.function(x).getY()))
            return  new Point2D(x, 1 / super.getEpsilon());
        return this.mathFunction.function(x);
    }
    public double differential(Point2D point)
    {
        double dx = point.getX() + super.getEpsilon();
        double dy = this.calculatePoint(dx).getY() - point.getY();
        return dy / super.getEpsilon();
    }
    public double calculateStep(double leftBorder, double rightBorder)
    { return (rightBorder - leftBorder) / 2; }
    public void expandPointsArea()
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
                FileWriter fileWriter = new FileWriter(pointsOutput);
                for (Point2D point : this.points)
                    fileWriter.write(point.pointToString() + "\n");
            } catch (IOException e) { System.out.println(e.getMessage()); }
        }
        else
            System.out.println(PrettyOutput.ERROR + "Ошибка. Невозможно создать файл по заданному пути: " +
                   PrettyOutput.COMMENT + pathToFile + PrettyOutput.RESET);
    }
}