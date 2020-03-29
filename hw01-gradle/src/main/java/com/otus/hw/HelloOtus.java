package com.otus.hw;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class HelloOtus {
    public static void main(String[] args) {
        var list = Lists.newArrayList("1", "2", "3", "4", "5");
        String result = Joiner.on(" * ").join(list);
        System.out.println(result);
    }
}
