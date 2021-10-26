package ggc.core.product;

import java.lang.Math;
import ggc.core.partner.Partner;
import ggc.core.product.Product;

/** Implements Batch class */
public class Batch {

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
     * Displays Batch Information
     * @return String (idProduto|idParceiro|preço|stock-actual)
     */
    @Override
    public String toString() {
        return _product.getId() + "|" + _suplier.getName() + "|" + Math.round(_unitPrice) + "|" + _ammount;
    }

    
}
