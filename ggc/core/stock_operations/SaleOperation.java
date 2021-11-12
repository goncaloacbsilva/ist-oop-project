package ggc.core.stock_operations;

import ggc.core.Warehouse;
import ggc.core.exception.NotEnoughResourcesException;
import ggc.core.partner.Partner;
import ggc.core.product.Batch;
import ggc.core.product.Product;
import ggc.core.transaction.SaleByCredit;
import ggc.core.transaction.Transaction;

public class SaleOperation extends StockOperation {

    private int _remainAmount;
    private int _paymentDeadline;
    private double _basePrice;

    public SaleOperation(Warehouse warehouse, int paymentDeadline) {
        super(warehouse);
        _paymentDeadline = paymentDeadline;
    }
    
    public void checkStock(Partner partner, Product product, int amount) throws NotEnoughResourcesException {
        if (!product.hasAvailableStock(amount)) {
            // Check if we can create more
            if (!product.isSimple()) {
                _remainAmount = amount - product.getTotalStock();
                getStore().checkDerivativeStock(product, _remainAmount);
            } else {
                throw new NotEnoughResourcesException(product.getId(), amount, product.getTotalStock());
            }
        }
    }

    public void updatePartnerStock(Partner partner, Product product, int amount) {
        try {
            double unitPrice = product.getLowestPrice();
            if (amount - _remainAmount > 0) {
                partner.addBatch(new Batch(partner, product, amount - _remainAmount, unitPrice));
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void processAgregation(Partner partner, Product product) throws NotEnoughResourcesException {
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

    public void updateWarehouse(Partner partner, Product product, int amount) throws NotEnoughResourcesException {
        _basePrice = product.sellAmount(amount);
    }

    public void registerTransaction(Partner partner, Product product, int amount) {
        Transaction saleTransaction = new SaleByCredit(getStore().generateTransactionId(), product, amount, partner, _basePrice, _paymentDeadline);
        
        getStore().addTransaction(saleTransaction);
        partner.addTransaction(saleTransaction);
    }
}
