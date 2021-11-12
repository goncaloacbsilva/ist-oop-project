package ggc.core.notifications;

import java.util.ArrayList;
import java.util.List;

import ggc.core.partner.Partner;
import ggc.core.product.Product;

public class Notification {
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

    public double getProductPrice(){
        return _productPrice;
    }

    @Override
    public String toString() {
        return getDescription() + "|" + getProductId() + "|" + getProductPrice();

    }

}


