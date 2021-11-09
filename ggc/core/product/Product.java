package ggc.core.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ggc.core.exception.NotEnoughResourcesException;
import ggc.core.product.Batch;
import ggc.core.product.comparators.OrderByLowerPriceFirst;

/** Implements Product Base (abstract) class */
public abstract class Product implements Serializable, Comparable<Product> {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;

    /** The maximum product price */
    private double _maxPrice;

    /** Product total stock */
    private int _totalStock;
    
    /** Product id */
    private String _id;

    /** Product associated batches */
    private List<Batch> _batches;


    /**
     * Creates a new Product
     * @param id product id
     */
    public Product(String id) {
        _id = id;
        _batches = new ArrayList<>();
    }

    /**
     * Get product id
     * @return String id
     */
    public String getId() {
        return _id;
    }

    /**
     * Get max price
     * @return double price
     */
    public double getMaxPrice() {
        return _maxPrice;
    }

    /**
     * Get total stock
     * @return int total stock
     */
    public int getTotalStock() {
        return _totalStock;
    }

    public boolean isSimple() {
        return true;
    }


    /**
     * Displays Simple Product Information 
     * @return String (idProduto|preço-máximo|stock-actual-total)
     * @see DerivativeProduct#toString()
     */
    public String toString() {
        return getId() + "|" + Math.round(getMaxPrice()) + "|" + getTotalStock();
    }

    public void updateMaxPrice() {
        double maxPrice = 0;
        for (Batch batch : _batches) {
            if (batch.getUnitPrice() > maxPrice) {
                maxPrice = batch.getUnitPrice();
            }
        }
        _maxPrice = maxPrice;
    }

    /**
     * Adds a new product batch
     * @param batch
     */
    public void addBatch(Batch batch) {
        _totalStock += batch.getAmount();
        if (batch.getUnitPrice() > _maxPrice) {
            _maxPrice = batch.getUnitPrice();
        }
        
        _batches.add(batch);
    }

    /**
     * Removes a certain amount from batch. If the whole 
     * stock is removed the batch is deleted. Returns the remain
     * @param batch
     * @param amount
     * @return remain
     */
    public int takeBatchAmount(Batch batch, int amount) {
        int remain = amount - batch.getAmount();
        if (remain > 0) {
            _totalStock -= batch.getAmount();
            _batches.remove(batch);
            updateMaxPrice();
        } else {
            remain = 0;
            _totalStock -= amount;
            batch.takeAmount(amount);
        }
        return remain;
    }

    /**
     * Check if there's enough stock to satistfy the requested amount
     * @param amount
     * @return
     */
    public boolean hasAvailableStock(int amount) {
        return (_totalStock >= amount);
    }

    /**
     * Sell a specific amount of this product, returns the price
     * @param batch
     */
    public double sellAmount(int amount) throws NotEnoughResourcesException {
        if (hasAvailableStock(amount)) {
            int remain = amount;
            double price = 0.0;
            Iterator<Batch> batchIterator = getBatches(new OrderByLowerPriceFirst()).iterator();

            while (batchIterator.hasNext()) {
                Batch batch = batchIterator.next();
                double batchPrice = batch.getUnitPrice();
                int previousRemain = remain;
                remain = takeBatchAmount(batch, remain);
                price += batchPrice * (previousRemain - remain);
                if (remain == 0) {
                    break;
                }
            }

            return price;

        } else {
            throw new NotEnoughResourcesException(_id, amount, _totalStock);
        }
    }
    
    /**
     * Get product batches
     * @return list of the product batches
     */
    public List<Batch> getBatches() {
        List<Batch> batches = new ArrayList<>(_batches);
        Collections.sort(batches);
        return batches;
    }

    /**
     * Get product batches ordered with the suplied comparator
     * @param comparator
     * @return
     */
    public List<Batch> getBatches(Comparator<Batch> comparator) {
        List<Batch> batches = new ArrayList<>(_batches);
        Collections.sort(batches, comparator);
        return batches;
    }
    
    /* Override equals in order to compare Products by id */
    @Override
    public boolean equals(Object a) {

        if (a == this) {
            return true;
        }

        if (a == null) {
            return false;
        }

        return ((Product)a).getId().equalsIgnoreCase(_id.toLowerCase());
    }

    /* Override hashCode to compare Product objects by their id */
    @Override
    public int hashCode() {
        return _id.toLowerCase().hashCode();
    }

    /* Implements Comparable interface method for sorting purposes */
    public int compareTo(Product product) {
        return _id.compareToIgnoreCase(product.getId());
    }
}
