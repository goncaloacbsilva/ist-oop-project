package ggc.core;

import java.io.Serializable;

import ggc.core.Product;

public abstract class Transaction implements Serializable, Comparable<Transaction> {

    public enum TransactionType {
        ACQUISITION,
        SALE
    };

    private int _id;
    private int _quantity;
    private Product _product;
    private Partner _partner;
    private boolean _paid;
    private int _paymentDate;
    private TransactionType _type;
    

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;

    Transaction(int id, Product product, int quantity, Partner partner, int paymentDate, TransactionType type) {
        _id = id;
        _product = product;
        _quantity = quantity;
        _partner = partner;
        _paymentDate = paymentDate;
        _type = type;
    }

    int getId() {
        return _id;
    }

    TransactionType getType() {
        return _type;
    }

    boolean isPaid() {
        return _paid;
    } 

    double pay() {
        _paid = true;
        return 0.0;
    }

    Partner getPartner() {
        return _partner;
    }

    Product getProduct() {
        return _product;
    }

    int getQuantity() {
        return _quantity;
    }

    int getPaymentDate() {
        return _paymentDate;
    }

    void setPaymentDate(int value) {
        _paymentDate = value;
    }

    abstract double getBasePrice();

    double calculatePriceToPay() {
        return 0.0;
    }

    @Override
    public String toString() {
        return _id + "|" + _partner.getId() + "|" + _product.getId() +"|" + _quantity;
    }

    public int compareTo(Transaction transaction) {
        return _id - transaction.getId();
    }
}
