package ggc.core.transaction;

import java.io.Serializable;

import ggc.core.partner.Partner;
import ggc.core.product.Product;

public abstract class Transaction implements Serializable {
    private int _id;
    private int _quantity;
    private Product _product;
    private Partner _partner;
    private boolean _paid;
    private int _paymentDate;
    

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;

    public Transaction(int id, Product product, int quantity, Partner partner, int paymentDate) {
        _id = id;
        _product = product;
        _quantity = quantity;
        _partner = partner;
        _paymentDate = paymentDate;
    }

    public int getId() {
        return _id;
    }

    public boolean isPaid() {
        return _paid;
    } 

    public Partner getPartner() {
        return _partner;
    }

    public int getPaymentDate() {
        return _paymentDate;
    }

    @Override
    public String toString() {
        return _id + "|" + _partner.getId() + "|" + _product.getId() +"|" + _quantity;
    }

    /* Override equals in order to compare Transactions by id */
    @Override
    public boolean equals(Object a) {

        if (a == this) {
            return true;
        }

        if (a == null) {
            return false;
        }

        return (((Transaction)a).getId() == _id);
    }

    /* Override hashCode to compare Transaction objects by their id */
    @Override
    public int hashCode() {
        return _id;
    }


}
