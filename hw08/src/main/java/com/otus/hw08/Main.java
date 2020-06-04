package com.otus.hw08;

import com.google.gson.Gson;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyGson myGson = new MyGson();
        Gson gson = new Gson();
        String[] analogs = {"Analog1, Analog2"};
        Book book = new Book(true, 1123223, List.of("John", "Pete", "Garry"), analogs);
        String myJson = myGson.toJson(book);
        String gsonJson = gson.toJson(book);

        System.out.println("MY json:");
        System.out.println(myJson);
        System.out.println("Gson json");
        System.out.println(gsonJson);

        System.out.println(book.equals(gson.fromJson(myJson, Book.class)));
    }
}
