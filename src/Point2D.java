import java.util.Comparator;

public class Point2D {
    protected double x;
    protected double y;
    Point2D()
    { this.x = Double.NaN; this.y = Double.NaN; }
    Point2D(double x, double y)
    { this.x = x; this.y = y; }
    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x)
    { this.x = x; }
    public void setY(double y)
    { this.y = y; }
    @Override
    public String toString()
    { return super.toString(); }
    @Override
    public boolean equals(Object obj)
    { return super.equals(obj); }
    public Point2D clone() throws CloneNotSupportedException
    { return (Point2D) super.clone(); }
    public static  Point2D setXFromString(String xStr)
    {
        double x;
        if (MathBase.isNumeric(xStr))
        {
            x = Double.parseDouble(xStr.trim());
            return new Point2D(x, Double.NaN);
        }
        return new Point2D();
    }
    public static Point2D setPairFromString(String pair)
    {
        double x, y;
        if (MathBase.severalNumeric(pair))
        {
            String[] splitPair = pair.trim().split("\\s+");
            x = Double.parseDouble(splitPair[0]);
            y = Double.parseDouble(splitPair[1]);
            return new Point2D(x, y);
        }
        return new Point2D();
    }
    public String pointToString()
    { return "[" + this.getX() + ";" + this.getY() + "]"; }
    public void print()
    { System.out.println(PrettyOutput.OUTPUT + "{ " + this.x + "; " + this.y + " }" + PrettyOutput.RESET); }
}
