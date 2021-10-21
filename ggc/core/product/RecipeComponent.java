package ggc.core.product;

import ggc.core.product.Product;

public class RecipeComponent {
    private Product _product;
    private int _ammount;

    public RecipeComponent(Product product, int ammount) {
        _product = product;
        _ammount = ammount;
    }

    public Product getProduct() {
        return _product;
    }

    public int getAmmount() {
        return _ammount;
    }

}
