package ggc.core;

import ggc.core.Product;

abstract class Sale extends Transaction {

    private double _basePrice;

    Sale(int currentId, Product product, int quantity, Partner partner, double basePrice, int date) {
        super(currentId, product, quantity, partner, date, TransactionType.SALE);
        _basePrice = basePrice;
    }

    @Override
    double getBasePrice() {
        return _basePrice;
    } 

    @Override
    public String toString() {
        return super.toString() + "|" + Math.round(_basePrice);
    }
}
