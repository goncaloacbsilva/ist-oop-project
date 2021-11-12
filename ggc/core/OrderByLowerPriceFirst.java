package ggc.core;

import java.util.Comparator;

public class OrderByLowerPriceFirst implements Comparator<Batch> {
    
    @Override
    public int compare(Batch o1, Batch o2) {
        return Double.compare(o1.getUnitPrice(), o2.getUnitPrice());
    }
}
