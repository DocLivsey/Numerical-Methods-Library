package MathModule.Abstract;

import MathModule.NumericalBase;
import OtherThings.PrettyOutput;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractMatrix<T> extends NumericalBase {
    protected AbstractVector<AbstractVector<T>> matrix;
    protected int rows;
    protected int columns;

    public AbstractVector<AbstractVector<T>> getMatrix() {
        return matrix;
    }
    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }
    public void setMatrix(AbstractVector<AbstractVector<T>> matrix) {
        this.matrix = matrix;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public void setColumns(int columns) {
        this.columns = columns;
    }

    public T getElementAt(int rowIndex, int colIndex) {
        return this.matrix.getElementAt(rowIndex).getElementAt(colIndex);
    }
    public AbstractVector<T> getRowAt(int rowIndex) {
        return this.matrix.getElementAt(rowIndex);
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
        this.matrix.getVector().forEach(row -> {
            vector.addElement(row.getElementAt(colIndex));
        });
        return vector;
    }
    public void setElementAt(T element, int rowIndex, int colIndex) {
        this.matrix.getElementAt(rowIndex).setElementAt(element, colIndex);
    }
    public void setRowAt(AbstractVector<T> row, int rowIndex) {
        this.matrix.setElementAt(row, rowIndex);
    }
    public void setColumnAt(AbstractVector<T> column, int colIndex) {
        AtomicInteger index = new AtomicInteger();
        this.matrix.getVector().forEach(row -> row.setElementAt(column.getElementAt(index.getAndIncrement()), colIndex));
    }
    public void addRowAt(AbstractVector<T> row, int rowIndex) {
        if (row.getVectorSize() != this.columns)
            throw new RuntimeException(PrettyOutput.ERROR + "Невозможно добавить строку в исходную матрицу " +
                    "из-за несоответсвия количества столбцов" + PrettyOutput.RESET);
        this.matrix.addElementAt(row, rowIndex);
    }
    public void addRow(AbstractVector<T> row) {
        if (row.getVectorSize() != this.columns)
            throw new RuntimeException(PrettyOutput.ERROR + "Невозможно добавить строку в исходную матрицу " +
                    "из-за несоответсвия количества столбцов" + PrettyOutput.RESET);
        this.matrix.addElement(row);
    }
    public void addColumnAt(AbstractVector<T> column, int colIndex) {
        if (column.getVectorSize() != this.rows)
            throw new RuntimeException(PrettyOutput.ERROR + "Невозможно добавить столбец в исходную матрицу " +
                    "из-за несоответсвия количества строк" + PrettyOutput.RESET);
        AtomicInteger index = new AtomicInteger();
        this.matrix.getVector().forEach(row -> row.addElementAt(column.getElementAt(index.incrementAndGet()), colIndex));
    }
    public void addColumn(AbstractVector<T> column) {
        if (column.getVectorSize() != this.rows)
            throw new RuntimeException(PrettyOutput.ERROR + "Невозможно добавить столбец в исходную матрицу " +
                    "из-за несоответсвия количества строк" + PrettyOutput.RESET);
        AtomicInteger index = new AtomicInteger();
        this.matrix.getVector().forEach(row -> row.addElement(column.getElementAt(index.incrementAndGet())));
    }
    public void removeRowAt(int rowIndex) {
        this.matrix.removeElementAt(rowIndex);
    }
    public void removeRow(AbstractVector<T> row) {
        this.matrix.removeElement(row);
    }
    public void removeColumnAt(int colIndex) {
        this.matrix.getVector().forEach(row -> row.removeElementAt(colIndex));
    }
    public void removeColumn(AbstractVector<T> column) {
        AtomicInteger index = new AtomicInteger();
        this.matrix.getVector().forEach(row -> row.removeElement(column.getElementAt(index.getAndIncrement())));
    }

    public abstract AbstractMatrix<? extends Number> add(Object... arguments);
    public abstract AbstractMatrix<? extends Number> multiply(Object... arguments);
    public abstract AbstractMatrix<? extends Number> constMultiply(Object... arguments);
}
