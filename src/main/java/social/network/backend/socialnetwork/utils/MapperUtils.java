package social.network.backend.socialnetwork.utils;


import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.util.List.of;

public final class MapperUtils {
    private MapperUtils() {

    }

    public static <T, R> List<R> mapCollection(Collection<T> source, Function<T, R> mapper) {
        if (source == null || source.isEmpty()) {
            return of();
        }
        return source.stream()
                .map(mapper)
                .toList();
    }

    public static <T, R> R mapDto(T source, Function<T, R> mapper) {
        if (source == null) {
            throw new NullPointerException("Cannot map null source object");
        }
        return mapper.apply(source);
    }
}
