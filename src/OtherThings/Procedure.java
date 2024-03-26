package OtherThings;

@FunctionalInterface
public interface Procedure<T, F> {
    T apply(F from);
}
