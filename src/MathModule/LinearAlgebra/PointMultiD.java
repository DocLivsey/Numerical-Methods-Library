package MathModule.LinearAlgebra;

import MathModule.NumericalBase;
import MathModule.Vector;
import OtherThings.PrettyOutput;

import java.io.IOException;

public class PointMultiD extends NumericalBase {
    protected MathModule.Vector x;
    protected double y;
    public PointMultiD(MathModule.Vector x, double y)
    { this.x = x; this.y = y; }
    public PointMultiD(String point, int pointDimension) throws ReflectiveOperationException, IOException {
        this.x = new MathModule.Vector();
        this.setPointFromString(point, pointDimension);
    }
    public PointMultiD() throws ReflectiveOperationException, IOException {
        this.x = new Vector();
        this.y = Double.NaN;
    }
    public MathModule.Vector getVectorX()
    { return x; }
    public double getX(int index)
    { return x.getElementAt(index); }
    public double getY()
    { return y; }
    public void setVectorX(MathModule.Vector x)
    { this.x = x; }
    public void setX(int index, double x)
    { this.x.setElementAt(x, index); }
    public void setY(double y)
    { this.y = y; }
    @Override
    public String toString()
    { return this.x + ";" + this.y; }
    @Override
    public boolean equals(Object obj)
    { return super.equals(obj); }
    public PointMultiD copy() throws ReflectiveOperationException, IOException {
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
        System.out.println(PrettyOutput.HEADER_OUTPUT + "Точка размерностью: " +
                (this.getVectorX().getVectorSize() + 1) + PrettyOutput.RESET);
        System.out.println("(" + this + ")");
    }
}
