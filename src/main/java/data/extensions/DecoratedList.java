package data.extensions;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class DecoratedList<T> implements IExtendedList<T> {
    private final String type;
    public final List<T> list;

    public DecoratedList() {
        this.list = new ArrayList<>();
        this.type = null;
    }

    private DecoratedList(List<T> list, String type) {
        this.list = list;
        this.type = type;
    }

    public DecoratedList(List<T> list) {
        this(list, Object.class.getTypeName());
    }

    public DecoratedList(List<T> list, Class<T> type) {
        this(list, type.getTypeName());
    }

    public DecoratedList(T[] list, Class<T> type) {
        this(Arrays.asList(list), type.getTypeName());
    }

    public DecoratedList(Set<T> list, Class<T> type) {
        this(new ArrayList<T>(list), type.getTypeName());
    }

    public DecoratedList(Class<T> type) {
        this(new ArrayList<>(), type.getTypeName());
    }

    public DecoratedList(T element) {
        this(Collections.singletonList(element));
    }

    @Override
    public int size() {
        return IExtendedList.isNotNull(list) ? list.size() : 0;
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        list.forEach(action);
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T element) {
        return list.add(element);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index, c);
    }

    public boolean addNullSafe(T element) {
        return isNotNull() && !Objects.isNull(element) && list.add(element);
    }

    public boolean addAllNullSafe(Collection<? extends T> c) {
        return IExtendedList.addAllCondition(list, c) && list.addAll(c);
    }

    public boolean addAllNullSafe(int index, Collection<? extends T> c) {
        return (index > -1) && (index < size()) && IExtendedList.addAllCondition(list, c) && list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return list.removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        list.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        list.sort(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public DecoratedList<T> subList(int fromIndex, int toIndex) {
        return new DecoratedList<T>(list.subList(fromIndex, toIndex), type);
    }

    @Override
    public Spliterator<T> spliterator() {
        return list.spliterator();
    }

    @Override
    public Stream<T> stream() {
        return list.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return list.parallelStream();
    }

    public boolean isNull() {
        return IExtendedList.isNull(list);
    }

    public boolean isNotNull() {
        return IExtendedList.isNotNull(list);
    }

    @Override
    public boolean isEmpty() {
        return IExtendedList.isEmpty(list);
    }

    public boolean isNullOrEmpty() {
        return IExtendedList.isNullOrEmpty(list);
    }

    public boolean isNotNullAndNonEmpty() {
        return IExtendedList.isNotNullAndNonEmpty(list);
    }

    public T first() {
        return IExtendedList.first(list);
    }

    public int lastIndex() {
        return IExtendedList.lastIndex(list);
    }

    public T last() {
        return IExtendedList.last(list);
    }

    public <U> U tail(Class<U> clazz) {
        return clazz.cast(IExtendedList.tail(list));
    }

    public <U> U initials(Class<U> clazz) {
        return clazz.cast(IExtendedList.initials(list));
    }

    public <U> U subList(Class<U> clazz, int fromIndex, int toIndex) {
        return clazz.cast(list.subList(fromIndex, toIndex));
    }

    public DecoratedList<T> tail() {
        return new DecoratedList<>(IExtendedList.tail(list), type);
    }

    public DecoratedList<T> initials() {
        return new DecoratedList<>(IExtendedList.initials(list), type);
    }

    public boolean isSingle() {
        return size() == 1;
    }

    public boolean isMany() {
        return size() > 1;
    }

    public boolean hasMoreThan(int amount) {
        return size() > amount;
    }

    public boolean hasAtleast(int amount) {
        return size() > amount - 1;
    }

    public boolean hasIndex(int index) {
        return index < size();
    }


    public String getType() {
        return type;
    }


}
