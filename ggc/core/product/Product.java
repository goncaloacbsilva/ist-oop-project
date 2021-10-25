package ggc.core.product;

import java.io.Serializable;

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


    public Product(String id) {
        _id = id;
    }

    /** 
     * Update product maximum price
     * @param maxPrice Maximum price
     */
    public void updateMaxPrice(double maxPrice) {
        _maxPrice = maxPrice;
    }

    /** 
     * Update total stock
     * @param stock New total stock
     */
    public void updateTotalStock(int stock) {
        _totalStock = stock;
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
     * Display Simple Product Information 
     * @return String (idProduto|preço-máximo|stock-actual-total)
     * @see DerivativeProduct#display()
     */
    public String display() {
        return getId() + "|" + Math.round(getMaxPrice()) + "|" + getTotalStock();
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

        return ((Product)a).getId().toLowerCase().equals(_id.toLowerCase());
    }

    /* Override hashCode to compare Product objects by their id */
    @Override
    public int hashCode() {
        return _id.hashCode();
    }
}
