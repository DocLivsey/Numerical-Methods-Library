package MathModule.LinearAlgebra;

import MathModule.NumericalBase;
import OtherThings.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Point2D extends NumericalBase {
    protected double x;
    protected double y;
    public Point2D()
    { this.x = Double.NaN; this.y = Double.NaN; }
    public Point2D(double x, double y)
    { this.x = x; this.y = y; }
    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x)
    { this.x = x; }
    public void setY(double y)
    { this.y = y; }
    @Override
    public String toString() { return this.getX() + ";" + this.getY(); }
    @Override
    public int hashCode() {
        return (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Point2D point2D = (Point2D) obj;
        return point2D.getY() == this.getY() && point2D.getX() == this.getX();
    }
    public Point2D copy() { return new Point2D(this.getX(), this.getY()); }
    public static Point2D setXFromString(String xStr) {
        double x;
        if (NumericalBase.isNumeric(xStr))
        {
            x = Double.parseDouble(xStr.trim());
            return new Point2D(x, Double.NaN);
        }
        return new Point2D();
    }
    public static Point2D setPairFromString(String pair) {
        double x, y;
        if (NumericalBase.severalNumeric(pair))
        {
            String[] splitPair = pair.trim().split("\\s+");
            x = Double.parseDouble(splitPair[0]);
            y = Double.parseDouble(splitPair[1]);
            return new Point2D(x, y);
        }
        return new Point2D();
    }
    public static Point2D readPointFromFile(String pathToFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
        String pair = reader.readLine();
        return Point2D.setPairFromString(pair);
    }
    public Vector toVector() throws ReflectiveOperationException, IOException {
        return new Vector(new ArrayList<>(List.of(this.x, this.y)));
    }
    public boolean isNanPoint() { return Double.isNaN(this.getX()) || Double.isNaN(this.getY()); }
    public void print() { System.out.println(PrettyOutput.OUTPUT + "(" + this + ")" + PrettyOutput.RESET); }
}
