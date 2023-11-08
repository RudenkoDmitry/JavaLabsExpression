package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Expression implements ExpressionAbstract {

    public Expression() {
        expression = "";
        parametrs = new ArrayList<Pair>();
        pos = -1;
    }

    public Expression(String newExpression) {
        expression = newExpression;
        parametrs = new ArrayList<Pair>();
        pos = -1;
    }

    void nextChar() {
        currentSymbol = (++pos < expression.length()) ? expression.charAt(pos) : (char) -1;
    }

    boolean parse(int charToParse) {
        while (currentSymbol == ' ') nextChar();
        if (currentSymbol == charToParse) {
            nextChar();
            return true;
        }
        return false;
    }

    double parseExpression() {
        double x = parseTerm();
        for (; ; ) {
            if (parse('+')) x += parseTerm();
            else if (parse('-')) x -= parseTerm();
            else return x;
        }
    }

    double parseTerm() {
        double x = parseFactor();
        for (; ; ) {
            if (parse('*')) x *= parseFactor();
            else if (parse('/')) x /= parseFactor();
            else return x;
        }
    }

    double parseFactor() {
        if (parse('+')) return +parseFactor();
        if (parse('-')) return -parseFactor();

        double x = 0;
        int startPos = pos;
        if (parse('(')) {
            x = parseExpression();
            if (!parse(')')) throw new RuntimeException("Missing ')'");
        } else if ((currentSymbol >= '0' && currentSymbol <= '9') || currentSymbol == '.') {
            while ((currentSymbol >= '0' && currentSymbol <= '9') || currentSymbol == '.') nextChar();
            x = Double.parseDouble(expression.substring(startPos, pos));
        } else if (currentSymbol >= 'a' && currentSymbol <= 'z') {
            while (currentSymbol >= 'a' && currentSymbol <= 'z') nextChar();
            String paramName = expression.substring(startPos, pos);
            if (parse('(')) {
                x = parseExpression();
                if (!parse(')')) throw new RuntimeException("Missing ')' after argument to " + paramName);
            } else {
                x = Double.parseDouble(getParametrValue(paramName));
            }
            if (paramName.equals("sqrt")) x = Math.sqrt(x);
            else if (paramName.equals("sin")) x = Math.sin(Math.toRadians(x));
            else if (paramName.equals("cos")) x = Math.cos(Math.toRadians(x));
            else if (paramName.equals("tan")) x = Math.tan(Math.toRadians(x));



        } else {
            throw new RuntimeException("Unexpected: " + (char) currentSymbol);
        }

        if (parse('^')) x = Math.pow(x, parseFactor());

        return x;
    }

    @Override
    public double solve() {
        nextChar();
        double x = parseExpression();
        if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) currentSymbol);
        return x;
    }
    @Override
    public String get() {
        return expression;
    }
    @Override
    public void set(String newExpression) {
        expression = newExpression;
    }

    @Override
    public String getParametrValue(String parametrName) {
        for (Pair pair : parametrs) {
            if (pair.getName().equals(parametrName))
                return pair.getValue();
        }
        System.out.println("Value of " + parametrName + ": ");
        Scanner scaner = new Scanner(System.in);
        String paramValue = scaner.nextLine();
        parametrs.add(new Pair(parametrName, paramValue));
        return paramValue;
    }

    private String expression;

    private int pos;

    private char currentSymbol;

    private ArrayList<Pair> parametrs;
}
