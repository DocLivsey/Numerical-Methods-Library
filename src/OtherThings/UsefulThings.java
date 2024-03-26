package OtherThings;

import java.util.*;

public class UsefulThings {
    public static <T, F> Collection<T> map(Collection<F> iterable, Procedure<T, F> procedure)
    {
        Collection<T> collection = new ArrayList<>();
        for (var item : iterable)
            collection.add(procedure.apply(item));
        return collection;
    }
}
