package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ggc.core.Batch;

abstract class StockEntity implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;

    private List<Batch> _batches;

    StockEntity() {
        _batches = new ArrayList<>();
    }

    /**
     * Adds a new product batch
     * @param batch
     */
    void addBatch(Batch batch) {
        _batches.add(batch);
    }

    /**
     * Count branches with the supplied product Id
     * @param productId
     * @return
     */
    int countStock(String productId) {
        int total = 0;
        for (Batch batch : _batches) {
            if (batch.getProductId().equals(productId)) {
                total += batch.getAmount();
            }
        }
        return total;
    }

    /**
     * Count all branches
     * @param productId
     * @return
     */
    int countStock() {
        int total = 0;
        for (Batch batch : _batches) {
            total += batch.getAmount();
        }
        return total;
    }

    /**
     * Check if stock is available by productId
     * @param productId
     * @param amount
     * @return
     */
    boolean hasAvailableStock(String productId, int amount) {
        return (countStock(productId) >= amount);
    }

    /**
     * Check if there's enough stock to satistfy the requested amount
     * @param amount
     * @return
     */
    boolean hasAvailableStock(int amount) {
        return (countStock() >= amount);
    }

    int takeBatchAmount(Batch batch, int amount) {
        int remain = amount - batch.getAmount();
        if (remain >= 0) {
            _batches.remove(batch);
        } else {
            remain = 0;
            batch.takeAmount(amount);
        }
        return remain;
    }

    List<Batch> getBatchesByProduct(String productId) {
        List<Batch> tempBatches;
        tempBatches = new ArrayList<>();
        for (Batch batch : _batches) {
            if (batch.getProductId().equals(productId)) {
                tempBatches.add(batch);
            }
        }
        return tempBatches;
    }

    List<Batch> getBatchesByPartner(String partnerId) {
        List<Batch> tempBatches;
        tempBatches = new ArrayList<>();
        for (Batch batch : _batches) {
            if (batch.getSupplierId().equals(partnerId)) {
                tempBatches.add(batch);
            }
        }
        return tempBatches;
    }

    List<Batch> getBatches(Comparator<Batch> comparator) {
        List<Batch> batches = new ArrayList<>(_batches);
        Collections.sort(batches, comparator);
        return batches;
    }

    /**
     * Get product batches
     * @return list of the product batches
     */
    List<Batch> getBatches() {
        List<Batch> batches = new ArrayList<>(_batches);
        Collections.sort(batches);
        return batches;
    }

}
