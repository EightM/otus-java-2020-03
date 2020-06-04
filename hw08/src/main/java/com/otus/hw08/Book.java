package com.otus.hw08;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Book {

    private boolean isPrinted;
    private int isbn;
    private List<String> authors;
    private String[] analogs = new String[2];

    public Book(boolean isPrinted, int isbn, List<String> authors, String[] analogs) {
        this.isPrinted = isPrinted;
        this.isbn = isbn;
        this.authors = authors;
        this.analogs = analogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isPrinted == book.isPrinted &&
                isbn == book.isbn &&
                Objects.equals(authors, book.authors) &&
                Arrays.equals(analogs, book.analogs);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(isPrinted, isbn, authors);
        result = 31 * result + Arrays.hashCode(analogs);
        return result;
    }
}
