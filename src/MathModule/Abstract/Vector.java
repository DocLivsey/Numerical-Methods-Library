package MathModule.Abstract;

import MathModule.*;

import java.io.*;
import java.util.*;

public abstract class Vector<T> extends NumericalBase {
    protected ArrayList<T> vector;
    protected int vectorSize;
    public ArrayList<T> getVector() {
        return vector;
    }
    public int getVectorSize() {
        return vectorSize;
    }
    public void setVector(Collection<T> vector) {
        this.vector = new ArrayList<>(vector);
    }
    public void setVectorSize(int vectorSize) {
        this.vectorSize = vectorSize;
    }

    public Vector<T> add(Vector<T> addVector) {
        return null;
    }
    public Vector<T> subtraction(Vector<T> subVector) {
        return null;
    }
    public Vector<T> multiply(Vector<T> multiplyVector) {
        return null;
    }
    public Vector<T> div(Vector<T> divVector) {
        return null;
    }

}
