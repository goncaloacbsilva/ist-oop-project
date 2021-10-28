package ggc.core.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ggc.core.product.Batch;
import ggc.core.partner.Partner;

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


    /**
     * Displays Simple Product Information 
     * @return String (idProduto|preço-máximo|stock-actual-total)
     * @see DerivativeProduct#toString()
     */
    public String toString() {
        return getId() + "|" + Math.round(getMaxPrice()) + "|" + getTotalStock();
    }

    /**
     * Adds a new product batch
     * @param batch
     */
    public void addBatch(Batch batch) {
        _totalStock += batch.getamount();
        if (batch.getUnitPrice() > _maxPrice) {
            _maxPrice = batch.getUnitPrice();
        }
        
        _batches.add(batch);
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
        return _id.hashCode();
    }

    /* Implements Comparable interface method for sorting purposes */
    public int compareTo(Product product) {
        return _id.compareTo(product.getId());
    }
}
