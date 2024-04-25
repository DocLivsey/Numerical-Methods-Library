package MathModule.Abstract;

import MathModule.NumericalBase;

public abstract class AbstractPoint<X, Y> extends NumericalBase {
    protected X x;
    protected Y y;

    public X getX() {
        return x;
    }
    public Y getY() {
        return y;
    }
    public void setX(X x) {
        this.x = x;
    }
    public void setY(Y y) {
        this.y = y;
    }


}
