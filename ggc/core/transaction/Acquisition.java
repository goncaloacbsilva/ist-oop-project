package ggc.core.transaction;

import ggc.core.partner.Partner;
import ggc.core.product.Product;
import ggc.core.transaction.Transaction;

public class Acquisition extends Transaction {
    public Acquisition(Product product, int quantity, Partner partner) {
        super(product, quantity, partner);
    }
}
