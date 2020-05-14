package com.otus.hw05;

// java -javaagent:.\changerDemo-all.jar -jar .\changerDemo-all.jar

public class Main {
        public static void main(String[] args) {
            int a = 333;
            int b = 444;
            Calculator.calculateSum(a, b);

            Calculator.calculateDivide(1.0, 4.5);
            Calculator.calculateMultiply(1000000000000000L, 20000000);
            Calculator.calculateSubtract(4, 1);
        }
}
