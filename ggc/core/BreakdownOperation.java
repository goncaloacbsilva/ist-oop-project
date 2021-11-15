package ggc.core;

import ggc.core.exception.NotEnoughResourcesException;
import ggc.core.Batch;
import ggc.core.Product;
import ggc.core.RecipeComponent;

class BreakdownOperation extends StockOperation {

    private double _acquisitonPrice;
    private String _componentsString;
    private double _sellPrice;
    private boolean _productIsSimple;

    BreakdownOperation(Warehouse warehouse) {
        super(warehouse);
    }

    void checkStock(Partner partner, Product product, int amount) throws NotEnoughResourcesException {
        _productIsSimple = product.isSimple();
        if (_productIsSimple) {
            return;
        }

        if (!product.hasAvailableStock(amount)) {
            throw new NotEnoughResourcesException(product.getId(), amount, product.getTotalStock());
        }
    }

    void updatePartnerStock(Partner partner, Product product, int amount) {
        double unitPrice = 0.0;
        _componentsString = "";

        if (_productIsSimple) {
            return;
        }
        
        for (RecipeComponent component : product.getRecipe()) {
            Product componentProduct = component.getProduct();
            try {
                unitPrice = componentProduct.getLowestPrice();
            } catch (IndexOutOfBoundsException ignored) {
                unitPrice = getStore().getTransactionsHighestPrice(componentProduct.getId());
            }

            int productAmount = component.getAmount() * amount;
            _componentsString += componentProduct.getId() + ":" + productAmount + ":" + Math.round(productAmount * unitPrice) + "#";
            componentProduct.addBatch(new Batch(partner, componentProduct, productAmount, unitPrice));

            _acquisitonPrice += unitPrice * productAmount;
        }

        _componentsString = _componentsString.subSequence(0, _componentsString.length() - 1).toString();
    }

    void processAgregation(Partner partner, Product product) throws NotEnoughResourcesException {};

    void updateWarehouse(Partner partner, Product product, int amount) throws NotEnoughResourcesException {

        if (_productIsSimple) {
            return;
        }

        _sellPrice = product.sellAmount(amount);
    }

    void registerTransaction(Partner partner, Product product, int amount) {

        if (_productIsSimple) {
            return;
        }

        Transaction breakdownTransaction = new BreakdownSale(getStore().generateTransactionId(), product, amount, partner, _sellPrice - _acquisitonPrice, _componentsString);
        getStore().addTransaction(breakdownTransaction);
        partner.addTransaction(breakdownTransaction);
        getStore().increaseSalesBalance(breakdownTransaction.calculatePriceToPay());
        breakdownTransaction.pay();
    }
}
