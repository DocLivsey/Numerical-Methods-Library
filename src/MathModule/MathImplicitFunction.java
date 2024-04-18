package MathModule;

import MathModule.LinearAlgebra.PointMultiD;

import java.io.IOException;

@FunctionalInterface
public interface MathImplicitFunction {
    PointMultiD function(Vector x) throws ReflectiveOperationException, IOException;
}
