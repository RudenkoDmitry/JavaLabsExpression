package org.example;

public class Pair {
    public Pair() {
        name = "";
        value = "0";
    }

    public Pair(String ParamName, String ParamValue){
        name = ParamName;
        value = ParamValue;
    }
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    private String name;
    private String value;
}
