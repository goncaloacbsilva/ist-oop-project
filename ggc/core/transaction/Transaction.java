package ggc.core.transaction;

import ggc.core.Date;
import ggc.core.partner.Partner;
import ggc.core.product.Product;

public abstract class Transaction {
    private int _currentId;
    private Date _paymentDate;
    private double _baseValue;
    private int _quantity;
    private Product _product;
    private Partner _partner;
    private boolean _paid;

    public Transaction(Product product, int quantity, Partner partner) {
        _product = product;
        _quantity = quantity;
        _partner = partner;
    }

    public boolean isPaid() {
        return _paid;
    } 

    public Date getPaymentDate() {
        return _paymentDate;
    }
}
