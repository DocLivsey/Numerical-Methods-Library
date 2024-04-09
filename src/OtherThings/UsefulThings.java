package OtherThings;

import java.lang.reflect.Type;
import java.util.*;

public class UsefulThings {
    public static <T, F> Collection<T> map(Collection<F> iterable, Procedure<T, F> procedure)
    {
        Collection<T> collection = new ArrayList<>();
        for (var item : iterable)
            collection.add(procedure.apply(item));
        return collection;
    }
    public static<T, F> T convertInstanceOfObject(F f) {
        try {
            return (T) f;
        } catch(ClassCastException e) {
            return null;
        }
    }
    public static<T> Collection<T> convertArrayToCollection(T[] array) {
        return Arrays.asList(array);
    }
    public static<T> T[] convertCollectionToArray(Collection<T> collection) {
        return (T[]) collection.stream().toArray();
    }
}
