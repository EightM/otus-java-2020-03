package com.otus.hw02;

import java.util.Collections;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        DIYArrayList<Integer> src = new DIYArrayList<>();
        DIYArrayList<Integer> dest = new DIYArrayList<>();

        Integer[] addingSrc = IntStream.range(1, 101).boxed().toArray(Integer[]::new);
        Integer[] addingDest = IntStream.range(401, 501).boxed().toArray(Integer[]::new);

        Collections.addAll(src, addingSrc);
        Collections.addAll(dest, addingDest);

        System.out.println("First list:");
        src.forEach(e -> System.out.print(e + " "));
        System.out.println();

        System.out.println("Second list:");
        dest.forEach(e -> System.out.print(e + " "));
        System.out.println();

        Collections.copy(dest, src);
        System.out.println("Second list after copy:");
        dest.forEach(e -> System.out.print(e + " "));
        System.out.println();

        Collections.sort(dest, (o1, o2) -> o2 - o1);
        System.out.println("Second src after sort:");
        dest.forEach(e -> System.out.print(e + " "));
    }
}
