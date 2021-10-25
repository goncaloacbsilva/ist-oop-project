package ggc.core.product;

import java.lang.Math;
import ggc.core.partner.Partner;
import ggc.core.product.Product;


public class Batch {

    private Partner _suplier;
    private Product _product;
    private int _ammount;
    private double _unitPrice;

    public Batch(Partner suplier, Product product, int ammount, double unitPrice) {
        _suplier = suplier;
        _product = product;
        _ammount = ammount;
        _unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return _product.getId() + "|" + _suplier.getName() + "|" + Math.round(_unitPrice) + "|" + _ammount;
    }

    
}
