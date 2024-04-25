package MathModule.LinearAlgebra;

import MathModule.NumericalBase;
import OtherThings.PrettyOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PointMultiD extends NumericalBase {
    protected Vector x;
    protected double y;
    public PointMultiD(String pathToPointMDInputFile, String stringPoint, Vector x, Double y, Integer pointDimension)
            throws IOException, ReflectiveOperationException {
        if (pointDimension != null) {
            if (pathToPointMDInputFile != null && !pathToPointMDInputFile.isBlank()) {
                this.x = readPointMultiDimFromFile(pathToPointMDInputFile, pointDimension).getVectorX();
                this.y = readPointMultiDimFromFile(pathToPointMDInputFile, pointDimension).getY();
            } else if (stringPoint != null && !stringPoint.isBlank() && this.x == null) {
                this.x = new Vector();
                this.setPointFromString(stringPoint, pointDimension);
            }
        } if ((pointDimension == null) && (this.x == null)) {
            this.y = Objects.requireNonNullElse(y, Double.NaN);
            if (x != null) {
                this.x = x;
            } else this.x = new Vector();
        }
    }

    public PointMultiD(Vector x, double y) throws ReflectiveOperationException, IOException {
        this(null, null, x, y, null);
    }
    public PointMultiD(String pathToPointMDInputFile, int pointDimension)
            throws ReflectiveOperationException, IOException {
        this(pathToPointMDInputFile, null, null, null, pointDimension);
    }
    public PointMultiD(int pointDimension, String point) throws ReflectiveOperationException, IOException {
        this(null, point, null, null, pointDimension);
    }
    public PointMultiD() throws ReflectiveOperationException, IOException {
        this(null, null, null, null, null);
    }
    public Vector getVectorX() { return x; }
    public double getX(int index) { return x.getElementAt(index); }
    public double getY() { return y; }
    public void setVectorX(Vector x) { this.x = x; }
    public void setX(int index, double x) { this.x.setElementAt(x, index); }
    public void setY(double y) { this.y = y; }
    public void addX(double x) { this.x.addElement(x); }
    @Override
    public String toString()
    { return this.x + ";" + this.y; }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        PointMultiD point = (PointMultiD) obj;
        return Objects.equals(x, point.x) && Objects.equals(y, point.y);
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    public PointMultiD copy() throws ReflectiveOperationException, IOException {
        PointMultiD clonePoint = new PointMultiD();
        clonePoint.setVectorX(this.getVectorX());
        clonePoint.setY(this.getY());
        return clonePoint;
    }
    public void setPointFromString(String pointStr, int pointDimension) {
        String[] splitPoint = pointStr.trim().split("\\s+");
        if (splitPoint.length == pointDimension)
        {
            for (int i = 0; i < pointDimension - 1; i++)
                this.addX(Double.parseDouble(splitPoint[i]));
            this.y = Double.parseDouble(splitPoint[pointDimension - 1]);
        }
        else if (splitPoint.length == pointDimension - 1)
        {
            for (String string : splitPoint)
                this.addX(Double.parseDouble(string));
            this.y = Double.NaN;
        }
        else
            throw new RuntimeException(PrettyOutput.ERROR +
                    "Ошибка! Неверно введена размерность точки или неверно введена сама точка" + PrettyOutput.RESET);
    }
    public static PointMultiD readPointMultiDimFromFile(String pathToPointMDInputFile, int pointDimension)
            throws IOException, ReflectiveOperationException {
        BufferedReader reader = new BufferedReader(new FileReader(pathToPointMDInputFile));
        return new PointMultiD(pointDimension, reader.readLine());
    }
    public Vector toVector() throws ReflectiveOperationException, IOException {
        return new Vector(new ArrayList<>(){{
            this.addAll(x.getVector());
            this.add(y);
        }});
    }
    public void print() {
        System.out.println(PrettyOutput.HEADER_OUTPUT + "Точка размерностью: " +
                (this.getVectorX().getVectorSize() + 1) + PrettyOutput.RESET);
        System.out.println("(" + this + ")");
    }
}
