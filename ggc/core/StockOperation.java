package ggc.core;

import ggc.core.exception.NotEnoughResourcesException;
import ggc.core.exception.UnknownObjectKeyException;
import ggc.core.Product;

abstract class StockOperation {
    private Warehouse _store;

    StockOperation(Warehouse warehouse) {
        _store = warehouse;
    }

    Warehouse getStore() {
        return _store;
    }

    final void execute(String partnerId, String productId, int amount) throws UnknownObjectKeyException, NotEnoughResourcesException {
        Partner partner = _store.getPartner(partnerId);
        Product product = _store.getProduct(productId); 

        checkStock(partner, product, amount);

        updatePartnerStock(partner, product, amount);

        processAgregation(partner, product);

        updateWarehouse(partner, product, amount);

        registerTransaction(partner, product, amount);

    }

    final void executeWithoutTransaction(String partnerId, String productId, int amount) throws UnknownObjectKeyException, NotEnoughResourcesException {
        Partner partner = _store.getPartner(partnerId);
        Product product = _store.getProduct(productId); 

        checkStock(partner, product, amount);

        updatePartnerStock(partner, product, amount);

        processAgregation(partner, product);

        updateWarehouse(partner, product, amount);

    }



    abstract void checkStock(Partner partner, Product product, int amount) throws UnknownObjectKeyException, NotEnoughResourcesException;

    abstract void updatePartnerStock(Partner partner, Product product, int amount) throws UnknownObjectKeyException;

    abstract void processAgregation(Partner partner, Product product) throws NotEnoughResourcesException;

    abstract void updateWarehouse(Partner partner, Product product, int amount) throws NotEnoughResourcesException;

    abstract void registerTransaction(Partner partner, Product product, int amount);
    
}
