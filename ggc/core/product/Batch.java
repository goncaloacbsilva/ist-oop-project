package ggc.core.product;

import java.io.Serializable;
import ggc.core.partner.Partner;
import ggc.core.product.Product;

/** Implements Batch class */
public class Batch implements Serializable, Comparable<Batch> {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;

    /** Batch suplier */
    private Partner _suplier;

    /** Batch associated product */
    private Product _product;

    /** Total ammount of product units */
    private int _ammount;

    /** Price per unit */
    private double _unitPrice;

    /**
     * Creates a new Product Batch
     * @param suplier Partner associated to the batch
     * @param product Product associated to the batch
     * @param ammount Ammount of product units
     * @param unitPrice Price per unit
     */
    public Batch(Partner suplier, Product product, int ammount, double unitPrice) {
        _suplier = suplier;
        _product = product;
        _ammount = ammount;
        _unitPrice = unitPrice;
    }

    /**
     * Get ammount of product units
     * @return int ammount
     */
    public int getAmmount() {
        return _ammount;
    }

    /**
     * Get price per unit
     * @return double price
     */
    public double getUnitPrice() {
        return _unitPrice;
    }

    public String getProductId() {
        return _product.getId();
    }

    public String getSuplierId() {
        return _suplier.getId();
    }

    /**
     * Displays Batch Information
     * @return String (idProduto|idParceiro|preço|stock-actual)
     */
    @Override
    public String toString() {
        return _product.getId() + "|" + _suplier.getId() + "|" + Math.round(_unitPrice) + "|" + _ammount;
    }

    public int compareTo(Batch batch) {
        if (getProductId().equalsIgnoreCase(batch.getProductId())) {
            if (getSuplierId().equalsIgnoreCase(batch.getSuplierId())) {
                if (Math.round(getUnitPrice()) == Math.round(batch.getUnitPrice())) {
                    return getAmmount() - batch.getAmmount();
                } else {
                    return (int) (Math.round(getUnitPrice()) - Math.round(batch.getUnitPrice()));
                }
            } else {
                return getSuplierId().compareTo(batch.getSuplierId());
            }
        } else {
            return getProductId().compareTo(batch.getProductId());
        }
    }
}
