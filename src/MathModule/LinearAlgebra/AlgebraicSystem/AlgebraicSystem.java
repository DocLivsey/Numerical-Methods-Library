package MathModule.LinearAlgebra.AlgebraicSystem;
import MathModule.LinearAlgebra.*;
import MathModule.NumericalBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class AlgebraicSystem extends NumericalBase {
    protected Matrix coefficientMatrix;
    protected Vector adjoinVector;
    protected Vector solution;

    protected AlgebraicSystem(String pathToSettingsFile, String pathToMatrixInputTxt, String pathToVectorInputTxt,
                           ArrayList<Vector> matrixFromVectors, Collection<Collection<Double>> matrixFromCollections,
                           Double[][] matrixFromArrays, ArrayList<Double> vector, ArrayList<Double> solutionFromList,
                              Matrix coefficientMatrix, Vector adjoinVector, Vector solution)
            throws ReflectiveOperationException, IOException {
        if (pathToSettingsFile != null) {
            this.setFields(pathToSettingsFile);
            super.setFields(pathToSettingsFile);
        } if (coefficientMatrix != null) {
            this.coefficientMatrix = coefficientMatrix;
        } else {
            this.coefficientMatrix = new Matrix(pathToSettingsFile, pathToMatrixInputTxt,
                    matrixFromVectors, matrixFromCollections, matrixFromArrays);
        } if (adjoinVector != null) {
            this.adjoinVector = adjoinVector;
        } else {
            this.adjoinVector = new Vector(pathToSettingsFile, pathToVectorInputTxt, vector);
        } if (solution != null) {
            this.solution = solution;
        } else {
            this.solution = new Vector(pathToSettingsFile, null, solutionFromList);
        }
    }
    public AlgebraicSystem(String pathToSettingsFile, String pathToMatrixInputTxt, String pathToVectorInputTxt)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, pathToMatrixInputTxt, pathToVectorInputTxt, null,
                null, null, null, null,
                null, null, null);
    }
    public AlgebraicSystem(String pathToMatrixInputTxt, String pathToVectorInputTxt)
            throws ReflectiveOperationException, IOException {
        this(null, pathToMatrixInputTxt, pathToVectorInputTxt);
    }
    public AlgebraicSystem(String pathToSettingsFile, Matrix coefficientMatrix, Vector adjoinVector)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, null, null, null,
                null, null, null, null,
                coefficientMatrix, adjoinVector, null);
    }
    public AlgebraicSystem(Matrix coefficientMatrix, Vector adjoinVector)
            throws ReflectiveOperationException, IOException {
        this(null, coefficientMatrix, adjoinVector);
    }
    public AlgebraicSystem(String pathToSettingsFile, ArrayList<Vector> matrixFromVectors, ArrayList<Double> vector)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, null, null, matrixFromVectors,
                null, null, vector, null,
                null, null, null);
    }
    public AlgebraicSystem(ArrayList<Vector> matrixFromVectors, ArrayList<Double> vector)
            throws ReflectiveOperationException, IOException {
        this(null, matrixFromVectors, vector);
    }
    public AlgebraicSystem copy() throws ReflectiveOperationException, IOException {
        return new AlgebraicSystem(null, null, null, null,
                null, null, null, null,
                coefficientMatrix, adjoinVector, null);
    }

    public Matrix getCoefficientMatrix() {
        return coefficientMatrix;
    }
    public Vector getAdjoinVector() {
        return adjoinVector;
    }
    public Vector getSolution() {
        return solution;
    }
    public double getSolutionCoordinate(int index) {
        return this.solution.getElementAt(index);
    }
    public void setCoefficientMatrix(Matrix coefficientMatrix) {
        this.coefficientMatrix = coefficientMatrix;
    }
    public void setAdjoinVector(Vector adjoinVector) {
        this.adjoinVector = adjoinVector;
    }
    public void setSolution(Vector solution) {
        this.solution = solution;
    }
    public void setSolutionCoordinate(int index, double solutionCoordinate) {
        this.solution.setElementAt(solutionCoordinate, index);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        AlgebraicSystem that = (AlgebraicSystem) object;
        return Objects.equals(coefficientMatrix, that.coefficientMatrix) && Objects.equals(adjoinVector, that.adjoinVector);
    }
    @Override
    public int hashCode() {
        return Objects.hash(coefficientMatrix, adjoinVector);
    }
    @Override
    public String toString() {
        return String.format("coefficient`s matrix:\n%s, adjoin vector:\n%s",
                this.coefficientMatrix, this.adjoinVector);
    }
}
