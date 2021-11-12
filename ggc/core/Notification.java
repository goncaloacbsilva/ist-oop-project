package ggc.core;

import java.io.Serializable;

public class Notification implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;

    private String _productId;
    private String _description;
    private double _productPrice;

    public Notification(Product product, String description, double productPrice) {
        _productId = product.getId();
        _description = description;
        _productPrice = productPrice;
    }

    public String getProductId() {
        return _productId;
    }

    public String getDescription() {
        return _description;
    }

    public double getProductPrice() {
        return _productPrice;
    }

    @Override
    public String toString() {
        return getDescription() + "|" + getProductId() + "|" + Math.round(getProductPrice());

    }

}


