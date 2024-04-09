package MathModule;

import MathModule.LinearAlgebra.Point2D;

import java.util.*;
@FunctionalInterface
public interface MathFunction {
    Point2D function(ArrayList<Double> arguments);
}
