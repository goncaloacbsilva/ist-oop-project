package ggc.core;

import ggc.core.Product;
import ggc.core.Transaction;

public class Acquisition extends Transaction {

    private double _paidValue;

    public Acquisition(int currentId, Product product, int quantity, Partner partner, int paymentDate, double paidValue) {
        super(currentId, product, quantity, partner, paymentDate, TransactionType.ACQUISITION);
        _paidValue = paidValue;
    }

    @Override
    public double getBasePrice() {
        return _paidValue;
    } 

    @Override
    public String toString() {
        return "COMPRA|" + super.toString() + "|" + Math.round(_paidValue) + "|" + super.getPaymentDate();
    }
}
