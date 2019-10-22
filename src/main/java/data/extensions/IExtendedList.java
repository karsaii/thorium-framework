package data.extensions;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public interface IExtendedList<T> extends List<T> {
    static <T> boolean addAllCondition(List<T> list, Collection<? extends T> c) {
        return (
            isNotNull(list) &&
            !Objects.isNull(c) &&
            !c.isEmpty() &&
            (c.stream().filter(Objects::nonNull).count() == c.size())
        );
    }
    static <T> T first(List<T> list) {
        return list.get(0);
    }

    static <T> int lastIndex(List<T> list) {
        final var size = list.size();
        return size > 0 ? size - 1 : -1;
    }

    static <T> T last(List<T> list) {
        return list.get(lastIndex(list));
    }

    static <T> List<T> tail(List<T> list) {
        return list.size() == 2 ? Collections.singletonList(last(list)) : list.subList(1, lastIndex(list));
    }

    static <T> List<T> initials(List<T> list) {
        return list.size() > 2 ? list.subList(0, lastIndex(list) - 1) : Collections.singletonList(first(list));
    }

    static <T> boolean isNull(List<T> list) {
        return Objects.isNull(list);
    }

    static <T> boolean isNotNull(List<T> list) {
        return !Objects.isNull(list);
    }

    static <T> boolean isEmpty(List<T> list) {
        return isNotNull(list) && list.isEmpty();
    }

    static <T> boolean isNullOrEmpty(List<T> list) {
        return isNull(list) || list.isEmpty();
    }

    static <T> boolean isNotNullAndNonEmpty(List<T> list) {
        return !(isNull(list) || list.isEmpty());
    }
}
