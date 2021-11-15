package ggc.core;

import ggc.core.Date;
import ggc.core.Product;

class SaleByCredit extends Sale {

    private int _deadline;
    private int _period;
    private double _paidValue;

    SaleByCredit(int currentId, Product product, int quantity, Partner partner, double basePrice, int deadline) {
        super(currentId, product, quantity, partner, basePrice, 0);
        _deadline = deadline;
        if (product.isSimple()) {
            _period = 5;
        } else {
            _period = 3;
        }
        partner.increaseSales(getBasePrice());
    }

    @Override
    double calculatePriceToPay() {
        Rank partnerRank = getPartner().getRank();
        if (!isPaid()) {
            int interval = _deadline - Date.now().getValue();
            return getBasePrice() * partnerRank.getDiscount(interval, _period) * partnerRank.getPenalty(interval, _period);
        } else {
            return _paidValue;
        }
    }

    @Override
    double pay() {
        if (!isPaid()) {
            Partner partner = getPartner();
            setPaymentDate(Date.now().getValue());

            _paidValue = calculatePriceToPay();
            partner.increasePaidSales(_paidValue);

            if (_deadline - getPaymentDate() >= 0) {
                partner.addPoints(_paidValue);
            } else {
                partner.takePoints(_deadline - getPaymentDate());
            }
            super.pay();

            return _paidValue;
        } else {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        String displayString = "VENDA|" + super.toString() + "|" + Math.round(calculatePriceToPay()) + "|" + _deadline;
        if (super.isPaid()) {
            displayString += "|" + super.getPaymentDate();
        }
        return displayString;
    }
}
