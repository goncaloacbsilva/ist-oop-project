package ggc.core;

import java.io.Serializable;

public class Date implements Serializable {
    private static final long serialVersionUID = 202109192006L;

    private static int _value;

    public Date(int value) {
        _value = value;
    }

    public Date add(int value) {
        return new Date(_value + value);
    }

    public int getValue() {
        return _value;
    }

    public static Date now() {
        return new Date(_value);
    }
}
