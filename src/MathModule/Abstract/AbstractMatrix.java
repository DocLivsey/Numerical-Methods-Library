package MathModule.Abstract;

import MathModule.LinearAlgebra.Vector;
import MathModule.NumericalBase;
import OtherThings.PrettyOutput;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractMatrix<T> extends NumericalBase {
    protected ArrayList<AbstractVector<T>> matrix;
    protected int rows;
    protected int columns;

    protected enum OperationsType {
        ADDITION, MULTIPLY, CONSTANT_MULTIPLY;
    }

    public ArrayList<AbstractVector<T>> getMatrix() {
        return matrix;
    }
    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }
    public void setMatrix(ArrayList<AbstractVector<T>> matrix) {
        this.matrix = matrix;
        this.setRows(getMatrix().size());
        this.setColumns(getRowAt(0).getVector().size());
    }
    public void setMatrix(Collection<Collection<T>> matrix) {
        ArrayList<AbstractVector<T>> collection = new ArrayList<>(){{
            matrix.forEach(row -> {
                AbstractVector<T> vector = AbstractVector.convertFromCollection(row);
                add(vector);
            });
        }};
        this.setMatrix(collection);
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public void setColumns(int columns) {
        this.columns = columns;
    }

    public T getElementAt(int rowIndex, int colIndex) {
        return this.matrix.get(rowIndex).getElementAt(colIndex);
    }
    public AbstractVector<T> getRowAt(int rowIndex) {
        return this.matrix.get(rowIndex);
    }
    public AbstractVector<T> getColumnAt(int colIndex) {
        AbstractVector<T> vector = new AbstractVector<T>() {
            @Override
            public AbstractVector<? extends Number> add(Object... arguments) {
                return null;
            }
            @Override
            public AbstractVector<? extends Number> subtraction(Object... arguments) {
                return null;
            }
            @Override
            public double scalarMultiply(Object... arguments) {
                return 0;
            }
            @Override
            public AbstractVector<? extends Number> constMultiply(Object... arguments) {
                return null;
            }
        };
        this.matrix.forEach(row -> {
            vector.addElement(row.getElementAt(colIndex));
        });
        return vector;
    }

    public void setElementAt(T element, int rowIndex, int colIndex) {
        this.matrix.get(rowIndex).setElementAt(element, colIndex);
    }
    public void setRowAt(AbstractVector<T> row, int rowIndex) {
        this.matrix.set(rowIndex, row);
        this.setRows(this.matrix.size());
        this.setColumns(this.matrix.get(0).getVectorSize());
    }
    public void setColumnAt(AbstractVector<T> column, int colIndex) {
        AtomicInteger index = new AtomicInteger();
        this.matrix.forEach(row -> row.setElementAt(column.getElementAt(index.getAndIncrement()), colIndex));
        this.setRows(this.matrix.size());
        this.setColumns(this.matrix.get(0).getVectorSize());
    }

    public void addRowAt(AbstractVector<T> row, int rowIndex) {
        if (row.getVectorSize() != this.columns)
            throw new RuntimeException(PrettyOutput.ERROR + "Невозможно добавить строку в исходную матрицу " +
                    "из-за несоответсвия количества столбцов" + PrettyOutput.RESET);
        this.matrix.add(rowIndex, row);
        this.setRows(this.matrix.size());
        this.setColumns(this.matrix.get(0).getVectorSize());
    }
    public void addRow(AbstractVector<T> row) {
        if (this.rows == 0)
            this.matrix = new ArrayList<>();
        else if (row.getVectorSize() != this.columns)
            throw new RuntimeException(PrettyOutput.ERROR + "Невозможно добавить строку в исходную матрицу " +
                    "из-за несоответсвия количества столбцов" + PrettyOutput.RESET);
        this.matrix.add(row);
        this.setRows(this.matrix.size());
        this.setColumns(row.getVectorSize());
    }
    public void addRow(Collection<T> row) {
        if (this.rows == 0)
            this.matrix = new ArrayList<>();
        else if (row.size() != this.columns)
            if (this.getMatrix() != null)
                if (!this.getMatrix().isEmpty())
                    throw new RuntimeException(PrettyOutput.ERROR + "Невозможно добавить строку в исходную матрицу " +
                        "из-за несоответсвия количества столбцов: " + this.columns + " " + row.size() + PrettyOutput.RESET);
        AbstractVector<T> vector = AbstractVector.convertFromCollection(row);
        this.matrix.add(vector);
        this.setRows(this.matrix.size());
        this.setColumns(row.size());
    }
    public void addColumnAt(AbstractVector<T> column, int colIndex) {
        if (column.getVectorSize() != this.rows)
            throw new RuntimeException(PrettyOutput.ERROR + "Невозможно добавить столбец в исходную матрицу " +
                    "из-за несоответсвия количества строк" + PrettyOutput.RESET);
        AtomicInteger index = new AtomicInteger();
        this.matrix.forEach(row -> row.addElementAt(column.getElementAt(index.incrementAndGet()), colIndex));
        this.setRows(this.matrix.size());
        this.setColumns(this.matrix.get(0).getVectorSize());
    }
    public void addColumn(AbstractVector<T> column) {
        if (column.getVectorSize() != this.rows)
            throw new RuntimeException(PrettyOutput.ERROR + "Невозможно добавить столбец в исходную матрицу " +
                    "из-за несоответсвия количества строк" + PrettyOutput.RESET);
        AtomicInteger index = new AtomicInteger();
        this.matrix.forEach(row -> row.addElement(column.getElementAt(index.incrementAndGet())));
        this.setRows(this.matrix.size());
        this.setColumns(this.matrix.get(0).getVectorSize());
    }
    public void addColumn(Collection<T> column) {
        if (column.size() != this.rows)
            throw new RuntimeException(PrettyOutput.ERROR + "Невозможно добавить столбец в исходную матрицу " +
                    "из-за несоответсвия количества строк" + PrettyOutput.RESET);
        AbstractVector<T> vector = AbstractVector.convertFromCollection(column);
        AtomicInteger index = new AtomicInteger();
        this.matrix.forEach(row -> row.addElement(vector.getElementAt(index.incrementAndGet())));
        this.setRows(this.matrix.size());
        this.setColumns(this.matrix.get(0).getVectorSize());
    }

    public void removeRowAt(int rowIndex) {
        this.matrix.remove(rowIndex);
        this.setRows(this.matrix.size());
        this.setColumns(this.matrix.get(0).getVectorSize());
    }
    public void removeRow(AbstractVector<T> row) {
        this.matrix.remove(row);
        this.setRows(this.matrix.size());
        this.setColumns(this.matrix.get(0).getVectorSize());
    }
    public void removeColumnAt(int colIndex) {
        this.matrix.forEach(row -> row.removeElementAt(colIndex));
        this.setRows(this.matrix.size());
        this.setColumns(this.matrix.get(0).getVectorSize());
    }
    public void removeColumn(AbstractVector<T> column) {
        AtomicInteger index = new AtomicInteger();
        this.matrix.forEach(row -> row.removeElement(column.getElementAt(index.getAndIncrement())));
        this.setRows(this.matrix.size());
        this.setColumns(this.matrix.get(0).getVectorSize());
    }

    public void swapRow(int indexChange, int indexChangeWith) {
        AbstractVector<T> changeRow = this.matrix.get(indexChange);
        setRowAt(getRowAt(indexChangeWith), indexChange);
        setRowAt(changeRow, indexChangeWith);
    }
    public void swapColumn(int indexChange, int indexChangeWith) {
        for (int i = 0; i < this.rows; i++) {
            T changeElement = getElementAt(i, indexChange);
            setElementAt(getElementAt(i, indexChangeWith), i, indexChange);
            setElementAt(changeElement, i, indexChangeWith);
        }
    }

    public abstract AbstractMatrix<? extends Number> add(Object... arguments);
    public abstract AbstractMatrix<? extends Number> multiply(Object... arguments);
    public abstract AbstractMatrix<? extends Number> constMultiply(Object... arguments);
}
