package ggc.core;

import ggc.core.exception.NotEnoughResourcesException;
import ggc.core.exception.UnknownObjectKeyException;
import ggc.core.Batch;
import ggc.core.Product;

class SaleOperation extends StockOperation {

    private int _remainAmount;
    private int _savedRemainAmount;
    private int _paymentDeadline;
    private double _basePrice;

    SaleOperation(Warehouse warehouse, int paymentDeadline) {
        super(warehouse);
        _paymentDeadline = paymentDeadline;
    }

    SaleOperation(Warehouse warehouse) {
        super(warehouse);
        _paymentDeadline = 0;
    }
    
    void checkStock(Partner partner, Product product, int amount) throws UnknownObjectKeyException, NotEnoughResourcesException {
        if (!product.hasAvailableStock(amount)) {
            // Check if we can create more
            if (!product.isSimple()) {
                _remainAmount = amount - product.getTotalStock();
                try {
                    getStore().checkDerivativeStock(product, _remainAmount);
                } catch (NotEnoughResourcesException exception) {
                    StockOperation agregation = new SaleOperation(getStore());
                    agregation.executeAgregationWithoutTransaction(partner.getId(), exception.getObjectKey(), exception.getRequestedAmount());
                }
            } else {
                throw new NotEnoughResourcesException(product.getId(), amount, product.getTotalStock());
            }
        }
    }

    void updatePartnerStock(Partner partner, Product product, int amount) {
        try {
            double unitPrice = product.getLowestPrice();
            if (amount - _remainAmount > 0) {
                partner.addBatch(new Batch(partner, product, amount - _remainAmount, unitPrice));
            }
        } catch (IndexOutOfBoundsException e) {
            // Will be handled on processAgregation stage
        }
    }

    void processAgregation(Partner partner, Product product) throws NotEnoughResourcesException, UnknownObjectKeyException {
        if (_remainAmount > 0) {
            double previousUnitPrice = 0.0;
            double agregationCost = 1 + product.getAlpha();
            int currentAmount = 0;
            for (int i = 0; i < _remainAmount; i++) {
                double currentUnitPrice = getStore().createProduct(product);
                if (previousUnitPrice != 0.0) {
                    if (previousUnitPrice != currentUnitPrice) {
                        product.addBatch(new Batch(partner, product, currentAmount, previousUnitPrice * agregationCost));
                        currentAmount = 0;
                    }
                }
                previousUnitPrice = currentUnitPrice;
                currentAmount++;
            }
            product.addBatch(new Batch(partner, product, currentAmount, previousUnitPrice * agregationCost));
        }
    }

    void updateWarehouse(Partner partner, Product product, int amount) throws NotEnoughResourcesException {
        _basePrice = product.sellAmount(amount);
    }

    void registerTransaction(Partner partner, Product product, int amount) {
        Transaction saleTransaction = new SaleByCredit(getStore().generateTransactionId(), product, amount, partner, _basePrice, _paymentDeadline);
        
        getStore().addTransaction(saleTransaction);
        partner.addTransaction(saleTransaction);
    }
}
