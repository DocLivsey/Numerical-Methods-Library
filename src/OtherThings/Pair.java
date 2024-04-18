package OtherThings;

public class Pair<T1, T2> {
    protected T1 first;
    protected T2 second;
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
    public T1 getFirst() {
        return first;
    }
    public T2 getSecond() {
        return second;
    }
    public void setFirst(T1 first) {
        this.first = first;
    }
    public void setSecond(T2 second) {
        this.second = second;
    }
    @Override
    public String toString() {
        if (first == null)
            return "Undefined " + second.toString();
        else if (second == null)
            return this.first + " Undefined";
        return this.first + ", " + this.second;
    }
}
