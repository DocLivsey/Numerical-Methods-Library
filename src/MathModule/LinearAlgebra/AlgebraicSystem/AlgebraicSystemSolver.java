package MathModule.LinearAlgebra.AlgebraicSystem;

import MathModule.LinearAlgebra.Matrix;
import MathModule.LinearAlgebra.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class AlgebraicSystemSolver extends AlgebraicSystem {
    protected AlgebraicSystemSolver(String pathToSettingsFile, String pathToMatrixInputTxt, String pathToVectorInputTxt,
                                    ArrayList<Vector> matrixFromVectors, Collection<Collection<Double>> matrixFromCollections,
                                    Double[][] matrixFromArrays, ArrayList<Double> vector, ArrayList<Double> solutionFromList,
                                    Matrix coefficientMatrix, Vector adjoinVector, Vector solution)
            throws ReflectiveOperationException, IOException {
        super(pathToSettingsFile, pathToMatrixInputTxt, pathToVectorInputTxt, matrixFromVectors, matrixFromCollections,
                matrixFromArrays, vector, solutionFromList, coefficientMatrix, adjoinVector, solution);
    }
    public AlgebraicSystemSolver(String pathToSettingsFile, String pathToMatrixInputTxt, String pathToVectorInputTxt)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, pathToMatrixInputTxt, pathToVectorInputTxt, null,
                null, null, null, null,
                null, null, null);
    }
    public AlgebraicSystemSolver(String pathToMatrixInputTxt, String pathToVectorInputTxt)
            throws ReflectiveOperationException, IOException {
        this(null, pathToMatrixInputTxt, pathToVectorInputTxt);
    }
    public AlgebraicSystemSolver(String pathToSettingsFile, Matrix coefficientMatrix, Vector adjoinVector)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, null, null, null,
                null, null, null, null,
                coefficientMatrix, adjoinVector, null);
    }
    public AlgebraicSystemSolver(Matrix coefficientMatrix, Vector adjoinVector)
            throws ReflectiveOperationException, IOException {
        this(null, coefficientMatrix, adjoinVector);
    }
    public AlgebraicSystemSolver(String pathToSettingsFile, ArrayList<Vector> matrixFromVectors, ArrayList<Double> vector)
            throws ReflectiveOperationException, IOException {
        this(pathToSettingsFile, null, null, matrixFromVectors,
                null, null, vector, null,
                null, null, null);
    }
    public AlgebraicSystemSolver(ArrayList<Vector> matrixFromVectors, ArrayList<Double> vector)
            throws ReflectiveOperationException, IOException {
        this(null, matrixFromVectors, vector);
    }

    public Matrix setExtendedMatrix() throws ReflectiveOperationException, IOException {
        Matrix extendedMatrix = this.coefficientMatrix.copy();
        extendedMatrix.addColumn(this.adjoinVector.getVector());
        return extendedMatrix;
    }
    public void gaussianMethod() throws ReflectiveOperationException, IOException {
        Matrix extendedMatrix = this.setExtendedMatrix();
        extendedMatrix = extendedMatrix.gaussianTransform();
        double Bk = extendedMatrix.getElementAt(extendedMatrix.getRows() - 1, extendedMatrix.getRows());
        double Ak = extendedMatrix.getElementAt(extendedMatrix.getRows() - 1, extendedMatrix.getRows() - 1);
        this.setSolutionCoordinate(extendedMatrix.getRows() - 1, Bk / Ak);
        for (int k = extendedMatrix.getRows() - 2; k >= 0; k--)
        {
            double sum = 0.0;
            for(int j = k + 1; j < extendedMatrix.getRows(); j++)
            {
                sum += extendedMatrix.getElementAt(k, j) * this.getSolutionCoordinate(j);
            }
            Bk = extendedMatrix.getElementAt(k, extendedMatrix.getRows());
            Ak = extendedMatrix.getElementAt(k, k);
            this.setSolutionCoordinate(k, (Bk - sum) / Ak);
        }
    }
}
