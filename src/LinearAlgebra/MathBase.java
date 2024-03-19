package LinearAlgebra;

import OtherThings.*;

public class MathBase {
    protected String pathToParametersFile;
    protected final static double epsilon = 1E-10;
    public static double getEpsilon() { return epsilon; }
    public static double[] setArguments(String pathToArgumentsFile)
    {


        return new double[]{};
    }
    @Override
    public boolean equals(Object obj)
    { return super.equals(obj); }
    @Override
    protected Object clone() throws CloneNotSupportedException
    { return super.clone(); }
    @Override
    public String toString()
    { return super.toString(); }
    public static boolean isNumeric(String string)
    {
        if(string == null || string.isEmpty()) {
            System.out.println(PrettyOutput.ERROR + "String cannot be parsed, it is null or empty." + PrettyOutput.RESET);
            return false;
        }
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(PrettyOutput.ERROR + "Input String cannot be parsed to Integer." + PrettyOutput.RESET);
        }
        return false;
    }
    public static boolean severalNumeric(String string)
    {
        String[] strArr = string.trim().split("\\s+");
        for (String s : strArr)
            if (!isNumeric(s)) return false;
        return true;
    }
}
