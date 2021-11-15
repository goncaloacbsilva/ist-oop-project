package ggc.core;

import ggc.core.Date;
import ggc.core.Product;
import ggc.core.Sale;

class BreakdownSale extends Sale {

    private String _componentsString;

    BreakdownSale(int currentId, Product product, int quantity, Partner partner, double basePrice, String componentsString) {
        super(currentId, product, quantity, partner, basePrice, 0);
        _componentsString = componentsString;
    }

    @Override
    double calculatePriceToPay() {
        double basePrice = getBasePrice();
        if (basePrice < 0) {
            return 0.0;
        } else {
            return basePrice;
        }
    }

    @Override
    double pay() {
        if (!isPaid()) {
            Partner partner = getPartner();
            setPaymentDate(Date.now().getValue());
            partner.addPoints(calculatePriceToPay());
            super.pay();
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return "DESAGREGAÇÃO|" + super.toString() + "|" + Math.round(calculatePriceToPay()) + "|" + Math.round(getPaymentDate()) + "|" + _componentsString;
    }
}
