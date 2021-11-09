package ggc.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ggc.core.exception.UnknownObjectKeyException;
import ggc.core.exception.UnknownObjectKeyException.ObjectType;
import ggc.core.partner.Partner;
import ggc.core.product.Batch;
import ggc.core.product.Product;

public abstract class StockEntity {

    private List<Batch> _batches;

    public StockEntity() {
        _batches = new ArrayList<>();
    }

    /**
     * Create a new Batch
     * @param idPartner
     * @param idProduct
     * @param stock
     * @param price
     * @throws UnknownObjectKeyException
     */
    public void addBatch(Partner partner, Product product, int stock, double price) throws UnknownObjectKeyException {
        Batch batch = new Batch(partner, product, stock, price);
        _batches.add(batch);
    }
}
