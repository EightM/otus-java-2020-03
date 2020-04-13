package com.otus.hw02;

import java.util.*;

public class DIYArrayList<E> implements List<E> {

    private Object[] elements;
    private static final int STANDRD_CAPACITY = 15;
    private int size = 0;

    public DIYArrayList() {
        elements = new Object[STANDRD_CAPACITY];
    }

    private Object[] expandArray(Object[] elements) {
        return Arrays.copyOf(elements, elements.length * 2);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return elements.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        return new DIYIterator();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        if (size == elements.length) {
            elements = expandArray(elements);
        }
        elements[size] = e;
        size++;
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        Arrays.sort((E[]) elements, 0, size, c);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(int index) {
        if (index > size - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return element(index);
    }

    @SuppressWarnings("unchecked")
    private E element(int index) {
        return (E)elements[index];
    }

    @Override
    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        E oldValue = element(index);
        elements[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new DIYListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private class DIYIterator implements Iterator<E> {

        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor != size();
        }

        @Override
        public E next() {

            if (cursor > size()) {
                throw new NoSuchElementException();
            }

            E element = element(cursor);
            cursor++;
            return element;
        }
    }

    private class DIYListIterator implements ListIterator<E> {

        int cursor = 0;
        int currentIndex = -1;

        @Override
        public boolean hasNext() {
            return cursor != size();
        }

        @Override
        public E next() {
            if (cursor > size()) {
                throw new NoSuchElementException();
            }

            E element = element(cursor);
            currentIndex = cursor;
            cursor++;
            return element;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public E previous() {
            if (cursor < 0) {
                throw new NoSuchElementException();
            }

            E element = element(cursor);
            currentIndex = cursor;
            cursor--;
            return element;
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            if (currentIndex < 0) {
                throw new IllegalStateException();
            }

            DIYArrayList.this.remove(currentIndex);
            cursor = currentIndex;
        }

        @Override
        public void set(E e) {
            if (currentIndex < 0) {
                throw new IllegalStateException();
            }

            DIYArrayList.this.set(currentIndex, e);
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }
}
