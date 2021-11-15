package ggc.core;

import java.io.Serializable;

class Notification implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;

    private String _productId;
    private String _description;
    private double _productPrice;

    Notification(Product product, String description, double productPrice) {
        _productId = product.getId();
        _description = description;
        _productPrice = productPrice;
    }

    String getProductId() {
        return _productId;
    }

    String getDescription() {
        return _description;
    }

    double getProductPrice() {
        return _productPrice;
    }

    @Override
    public String toString() {
        return getDescription() + "|" + getProductId() + "|" + Math.round(getProductPrice());

    }

}


