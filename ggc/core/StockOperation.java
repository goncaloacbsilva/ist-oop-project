package ggc.core;

import ggc.core.exception.NotEnoughResourcesException;
import ggc.core.exception.UnknownObjectKeyException;
import ggc.core.Product;

public abstract class StockOperation {
    private Warehouse _store;

    public StockOperation(Warehouse warehouse) {
        _store = warehouse;
    }

    public Warehouse getStore() {
        return _store;
    }

    public final void execute(String partnerId, String productId, int amount) throws UnknownObjectKeyException, NotEnoughResourcesException {
        Partner partner = _store.getPartner(partnerId);
        Product product = _store.getProduct(productId); 

        checkStock(partner, product, amount);

        updatePartnerStock(partner, product, amount);

        processAgregation(partner, product);

        updateWarehouse(partner, product, amount);

        registerTransaction(partner, product, amount);

    }

    public abstract void checkStock(Partner partner, Product product, int amount) throws NotEnoughResourcesException;

    public abstract void updatePartnerStock(Partner partner, Product product, int amount) throws UnknownObjectKeyException;

    public abstract void processAgregation(Partner partner, Product product) throws NotEnoughResourcesException;

    public abstract void updateWarehouse(Partner partner, Product product, int amount) throws NotEnoughResourcesException;

    public abstract void registerTransaction(Partner partner, Product product, int amount);
    
}
