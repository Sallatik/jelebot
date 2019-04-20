package sallat.tgbot;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

    @SafeVarargs
    public static <T> List<T> list(T... elements) {
        return Stream.of(elements).collect(Collectors.toList());
    }

    @SafeVarargs
    public static  <T> Set<T> set(T... elements) {
        return Stream.of(elements).collect(Collectors.toSet());
    }
}
