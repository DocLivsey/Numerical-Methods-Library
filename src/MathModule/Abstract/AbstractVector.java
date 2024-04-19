package MathModule.Abstract;

import MathModule.*;

import java.io.*;
import java.util.*;

public abstract class AbstractVector<T> extends NumericalBase {
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

    public T getElementAt(int index) {
        return vector.get(index);
    }
    public void setElementAt(T element, int index) {
        vector.set(index, element);
    }
    public void addElementAt(T element, int index) {
        LinkedList<T> list = new LinkedList<>(this.vector);
        list.add(index, element);
        this.vector = new ArrayList<>(list);
    }
    public void addElement(T element) {
        LinkedList<T> list = new LinkedList<>(this.vector);
        list.add(element);
        this.vector = new ArrayList<>(list);
    }
    public void removeElementAt(int index) {
        LinkedList<T> list = new LinkedList<>(this.vector);
        list.remove(index);
        this.vector = new ArrayList<>(list);
    }
    public void removeElement(T element) {
        LinkedList<T> list = new LinkedList<>(this.vector);
        list.remove(element);
        this.vector = new ArrayList<>(list);
    }

    public abstract AbstractVector<? extends Number> add(Object... arguments)
            throws ReflectiveOperationException, IOException;
    public abstract AbstractVector<? extends Number> subtraction(Object... arguments)
            throws ReflectiveOperationException, IOException;
    public abstract double scalarMultiply(Object... arguments) throws IOException, ReflectiveOperationException;

    public abstract AbstractVector<? extends Number> constMultiply(Object... arguments)
            throws ReflectiveOperationException, IOException;
}
