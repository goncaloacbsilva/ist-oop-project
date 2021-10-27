package ggc.core;

import java.io.Serializable;

/**
 * Implements Date object
 */
public class Date implements Serializable {
    private static final long serialVersionUID = 202109192006L;
    private int _value; // Should be Static! But right now it cant be because of Serialization

    /**
     * Creates a new Date object with the supplied value
     * @param value
     */
    public Date(int value) {
        _value = value;
    }

    /**
     * Advances a given number of days
     * @param value number of days to advance
     * @return new Date object
     */
    public Date add(int value) {
        return new Date(_value + value);
    }

    /**
     * Calculates the difference between two dates
     * @param date Date to compare
     * @return absolute difference between dates 
     */
    public int difference(Date date) {
        return Math.abs(date.getValue() - _value);
    }

    /**
     * Get Date value
     * @return value
     */
    public int getValue() {
        return _value;
    }

    /**
     * Get the current Date object
     * @return Date object
     */
    public Date now() {
        return new Date(_value);
    }
}
