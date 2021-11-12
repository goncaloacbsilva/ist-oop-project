package ggc.core.stock_operations;

import ggc.core.Warehouse;
import ggc.core.exception.NotEnoughResourcesException;
import ggc.core.partner.Partner;
import ggc.core.product.Batch;
import ggc.core.product.Product;
import ggc.core.product.RecipeComponent;
import ggc.core.transaction.BreakdownSale;
import ggc.core.transaction.Transaction;

public class BreakdownOperation extends StockOperation {

    private double _acquisitonPrice;
    private String _componentsString;
    private double _sellPrice;
    private boolean _productIsSimple;

    public BreakdownOperation(Warehouse warehouse) {
        super(warehouse);
    }

    public void checkStock(Partner partner, Product product, int amount) throws NotEnoughResourcesException {
        _productIsSimple = product.isSimple();
        if (_productIsSimple) {
            return;
        }

        if (!product.hasAvailableStock(amount)) {
            throw new NotEnoughResourcesException(product.getId(), amount, product.getTotalStock());
        }
    }

    public void updatePartnerStock(Partner partner, Product product, int amount) {
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

    public void processAgregation(Partner partner, Product product) throws NotEnoughResourcesException {};

    public void updateWarehouse(Partner partner, Product product, int amount) throws NotEnoughResourcesException {

        if (_productIsSimple) {
            return;
        }

        _sellPrice = product.sellAmount(amount);

        getStore().increaseAcquisitionsBalance(_acquisitonPrice);
        getStore().increaseSalesBalance(_sellPrice);
    }

    public void registerTransaction(Partner partner, Product product, int amount) {

        if (_productIsSimple) {
            return;
        }

        Transaction breakdownTransaction = new BreakdownSale(getStore().generateTransactionId(), product, amount, partner, _sellPrice - _acquisitonPrice, _componentsString);
        getStore().addTransaction(breakdownTransaction);
        partner.addTransaction(breakdownTransaction);
        breakdownTransaction.pay();
    }
}
