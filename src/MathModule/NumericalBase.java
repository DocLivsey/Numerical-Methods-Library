package MathModule;

import OtherThings.*;
import Parsers.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class NumericalBase {
    protected final TreeSet<String> variablesList = new TreeSet<>(List.of("epsilon"));
    protected final HashMap<String, Double> parametersTable = new HashMap<>();
    protected double epsilon = 1E-10;
    public TreeSet<String> getVariablesList() {
        return variablesList;
    }
    public HashMap<String, Double> getParametersTable() {
        return parametersTable;
    }
    public double getEpsilon() {
        return epsilon;
    }
    public static double getEpsilon(double epsilon) {
        return epsilon;
    }
    public void setParametersTable(String pathToParametersFile) throws IOException {
        this.parametersTable.putAll(getVariablesTable(pathToParametersFile));
    }
    public void setEpsilon(String pathToSettingsFile) throws IOException {
        this.epsilon = this.getVariablesTable(pathToSettingsFile).get("epsilon");
    }
    public void setFields(String pathToSettingsFile) throws IOException, ReflectiveOperationException {
        HashMap<String, Object> fields = FileParser.SettingsParser.getFieldsTable(pathToSettingsFile, this);
        List<String> actualFields = (List<String>) UsefulThings.map(
                Arrays.asList(this.getClass().getDeclaredFields()), Field::getName);
        for (var field : fields.keySet())
        {
            if (InputStreamParser.stringMatchesAnyItemOfList(actualFields, field, "\\s+"))
            {
                Field actualField = this.getClass().getDeclaredField(field);
                actualField.set(this, fields.get(field));
            }
        }
    }
    public HashMap<String, Double> getVariablesTable(String pathToSettingsFile) throws IOException {
        HashMap<String, Double> variablesTable = new HashMap<>();
        HashMap<String, Double> parametersTable = FileParser.SettingsParser.getParametersTable(pathToSettingsFile);
        String replaceRegex = "[_\\s+]";
        for (var parameter : parametersTable.keySet())
            if (InputStreamParser.stringMatchesAnyItemOfList(this.variablesList, parameter, replaceRegex))
                variablesTable.put(parameter.replaceAll(replaceRegex, ""), parametersTable.get(parameter));
        return variablesTable;
    }
    public boolean isParameterOfMethodUpload(Collection<String> parameters)
    {
        for (String parameter : parameters)
        {
            if (!this.parametersTable.containsKey(parameter))
                return false;
            else
            if (this.parametersTable.get(parameter) == null)
                return false;
        }
        return true;
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
    public void updateVariablesList(Collection<String> collection)
    { this.variablesList.addAll(collection); }
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
