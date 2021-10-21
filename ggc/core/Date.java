package ggc.core;

import java.io.Serializable;

public class Date implements Serializable {
    private int _value;

    public Date(int value) {
        _value = value;
    }

    public boolean goForward(int value) {
        if (value < 0) {
            return false;
        }

        _value += value;

        return true;
    }

    public int getValue() {
        return _value;
    }
}
