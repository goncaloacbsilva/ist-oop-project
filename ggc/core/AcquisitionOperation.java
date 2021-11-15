package ggc.core;

import ggc.core.exception.NotEnoughResourcesException;
import ggc.core.exception.UnknownObjectKeyException;
import ggc.core.Batch;
import ggc.core.Product;

class AcquisitionOperation extends StockOperation {

    private double _totalPrice;
    private double _price;

    AcquisitionOperation(Warehouse warehouse, double price) {
        super(warehouse);
        _price = price;
    }

    void checkStock(Partner partner, Product product, int amount) throws NotEnoughResourcesException {}

    void updatePartnerStock(Partner partner, Product product, int amount) throws UnknownObjectKeyException {
        try {
            _totalPrice = partner.sellBatch(product.getId(), amount, _price);
        } catch (NotEnoughResourcesException exception) {
            _totalPrice = _price * amount;
            partner.increasePurchases(_totalPrice);
        }
    }

    void processAgregation(Partner partner, Product product) throws NotEnoughResourcesException {};

    void updateWarehouse(Partner partner, Product product, int amount) throws NotEnoughResourcesException {
        product.addBatch(new Batch(partner, product, amount, _price));
    }

    void registerTransaction(Partner partner, Product product, int amount) {
        Transaction acquisitionTransaction = new Acquisition(getStore().generateTransactionId(), product, amount, partner, Date.now().getValue(), _totalPrice);
        getStore().addTransaction(acquisitionTransaction);
        partner.addTransaction(acquisitionTransaction);
        acquisitionTransaction.pay();
        getStore().increaseAcquisitionsBalance(_totalPrice);
    }

}
