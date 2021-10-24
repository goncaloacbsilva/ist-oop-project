package ggc.core;

import java.io.Serializable;

/**
 * Implements Date object
 */
public class Date implements Serializable {
    private static final long serialVersionUID = 202109192006L;
    private static int _value;

    /**
     * Create new Date object with the supplied value
     * @param value
     */
    public Date(int value) {
        _value = value;
    }

    /**
     * Advance a given number of days
     * @param value number of days to advance
     * @return Date object
     */
    public Date add(int value) {
        return new Date(_value + value);
    }

    /**
     * Calculate the difference between two dates
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
    public static Date now() {
        return new Date(_value);
    }
}
