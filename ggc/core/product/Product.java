package ggc.core.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ggc.core.product.Batch;
import ggc.core.partner.Partner;

/** Implements Product Base (abstract) class */
public abstract class Product implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;

    /** The maximum product price */
    private double _maxPrice;

    /** Product total stock */
    private int _totalStock;
    
    /** Product id */
    private String _id;
    private List<Batch> _batches;


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
     * @see DerivativeProduct#display()
     */
    public String display() {
        return getId() + "|" + Math.round(getMaxPrice()) + "|" + getTotalStock();
    }

    /**
     * Adds a new product batch
     * @param suplier Partner who suplies the batch
     * @param ammount Ammount of product units
     * @param unitPrice Price per unit
     */
    public void addBatch(Partner suplier, int ammount, double unitPrice) {
        _totalStock += ammount;
        if (unitPrice > _maxPrice) {
            _maxPrice = unitPrice;
        }
        
        _batches.add(new Batch(suplier, this, ammount, unitPrice));
    }
    
    /**
     * Get product batches
     * @return list of the product batches
     */
    public List<Batch> getBatches() {
        return new ArrayList<>(_batches);
    }
    
    /* Override equals in order to compare Products by name */
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
}
