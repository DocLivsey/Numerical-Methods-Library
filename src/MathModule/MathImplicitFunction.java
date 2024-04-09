package MathModule;

import MathModule.LinearAlgebra.PointMultiD;
import MathModule.LinearAlgebra.Vector;

@FunctionalInterface
public interface MathImplicitFunction {
    PointMultiD function(Vector x);
}
