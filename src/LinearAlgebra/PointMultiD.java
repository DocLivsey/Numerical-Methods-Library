package LinearAlgebra;

import OtherThings.PrettyOutput;

import java.io.*;
import java.util.*;

public class PointMultiD extends NumericalBase {
    protected Vector x;
    protected double y;
    public PointMultiD(String point, int pointDimension)
    {
        this.x = new Vector(pointDimension - 1);
        this.setPointFromString(point, pointDimension);
    }
    public PointMultiD(Vector x, double y)
    { this.x = x; this.y = y; }
    public PointMultiD()
    {
        this.x = new Vector();
        this.y = Double.NaN;
    }
    public Vector getVectorX()
    { return x; }
    public double getX(int index)
    { return x.getItem(index); }
    public double getY()
    { return y; }
    public void setVectorX(Vector x)
    { this.x = x; }
    public void setX(int index, double x)
    { this.x.setItem(index, x); }
    public void setY(double y)
    { this.y = y; }
    @Override
    public String toString()
    { return super.toString(); }
    @Override
    public boolean equals(Object obj)
    { return super.equals(obj); }
    public PointMultiD clonePoint()
    {
        PointMultiD clonePoint = new PointMultiD();
        clonePoint.setVectorX(this.getVectorX());
        clonePoint.setY(this.getY());
        return clonePoint;
    }
    public void setPointFromString(String pointStr, int pointDimension)
    {
        String[] splitPoint = pointStr.trim().split("\\s+");
        if (splitPoint.length == pointDimension)
        {
            for (int i = 0; i < pointDimension - 1; i++)
                this.setX(i, Double.parseDouble(splitPoint[i]));
            this.y = Double.parseDouble(splitPoint[pointDimension - 1]);
        }
        else if (splitPoint.length == pointDimension - 1)
        {
            for (int i = 0; i < splitPoint.length; i++)
                this.setX(i, Double.parseDouble(splitPoint[i]));
            this.y = Double.NaN;
        }
        else
            throw new RuntimeException(PrettyOutput.ERROR +
                    "Ошибка! Неверно введена размерность точки или неверно введена сама точка" + PrettyOutput.RESET);
    }
    public void print()
    {
        System.out.print("{[ ");
        for (int i = 0; i < this.x.getVectorSize(); i++)
            System.out.print(this.getX(i) + "; ");
        System.out.println("] " + this.y + "}");
    }
}
