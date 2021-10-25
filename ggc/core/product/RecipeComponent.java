package ggc.core.product;

import ggc.core.product.Product;

/** Implements Recipe Component class */
public class RecipeComponent {

    /** Product associated to the recipe component */
    private Product _product;

    /** Product ammount for the recipe */
    private int _ammount;

    /**
     * Create a Recipe Component
     * @param product product associated
     * @param ammount product ammount for the recipe
     */
    public RecipeComponent(Product product, int ammount) {
        _product = product;
        _ammount = ammount;
    }

    /**
     * Get associated product
     * @return Product
     */
    public Product getProduct() {
        return _product;
    }

    /**
     * Get product ammount for the recipe
     * @return int ammount
     */
    public int getAmmount() {
        return _ammount;
    }

}
