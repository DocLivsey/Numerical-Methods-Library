package OtherThings;

public class Pair<T1, T2> {
    protected T1 first;
    protected T2 second;
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public String toString() {
        return this.first.toString() + ", " + this.second.toString();
    }
}
