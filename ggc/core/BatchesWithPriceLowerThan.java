package ggc.core;

import java.util.ArrayList;
import java.util.Collection;

class BatchesWithPriceLowerThan implements LookupStrategy {
    private double _price;

    BatchesWithPriceLowerThan(double price) {
        _price = price;
    }

    public Collection<Object> execute(Warehouse store) {
        Collection<Object> objects = new ArrayList<>();
        for (Batch batch : store.getAvailableBatches()) {
            if (batch.getUnitPrice() < _price) {
                objects.add(batch);
            }
        }
        return objects;
    }
}
