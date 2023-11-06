package org.example;

public class Main {
    public static void main(String[] args) {
        Expression primer1 = new Expression("(10 + 3) * 2 - (5 / 2) * 4");
        System.out.println(primer1.solve());
    }
}