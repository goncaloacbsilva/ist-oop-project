package ggc.core.partner;

import java.util.ArrayList;
import java.util.List;

import ggc.core.product.Batch;

public class Partner {
    private String _id;
    private String _name;
    private String _address;
    private int _points;
    private double _totalSellAmount;
    private double _totalbuyAmount;
    private List<Batch> _batches;

    public Partner(String id, String name, String address) {
        _id = id;
        _name = name;
        _address = address;
        _batches = new ArrayList<>();
    }

    public String getId() {
        return _id;
    }

    /**
     * Adds a new product batch to partner
     * @param batch
     */
    public void addBatch(Batch batch) {
        _batches.add(batch);
    }

    /**
     * Get product batches
     * @return list of the product batches
     */
    public List<Batch> getBatches() {
        return new ArrayList<>(_batches);
    }

    public String getName() {
        return _name;
    }

    public int getPoints() {
        return _points;
    }

    public void addPoints(int amount) {
        _points += 10 * amount;
    }

    public void takePoints(int amount) {
        _points -= amount;
    }
}
