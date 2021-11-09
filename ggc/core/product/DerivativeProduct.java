package ggc.core.product;

import java.util.HashSet;
import java.util.Set;

import ggc.core.product.Product;
import ggc.core.product.RecipeComponent;

/** Implements Derivative Product class */
public class DerivativeProduct extends Product {

    /** Derivative Product Recipe */
    private Set<RecipeComponent> _recipe;

    /** Alpha Product value */
    private double _alpha;

    /**
     * Creates a Derivative Product
     * @param id Product id
     * @param recipe recipe
     * @param alpha Alpha factor
     */
    public DerivativeProduct(String id, Set<RecipeComponent> recipe, double alpha) {
        super(id);
        _recipe = new HashSet<RecipeComponent>(recipe);
        _alpha = alpha;
    }

    /**
     * Get Product recipe
     * @return RecipeComponent set
     */
    public Set<RecipeComponent> getRecipe() {
        return _recipe;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    /**
     * Displays Derivative Product Information
     * @return String (idProduto|preço-máximo|stock-actual-total|agravamento|componente-1:quantidade-1#...#componente-n:quantidade-n)
     */
    @Override
    public String toString() {
        String temp = super.toString() + "|" + _alpha + "|";

        for(RecipeComponent component : _recipe) {
            temp += component.getProduct().getId() + ":" + component.getamount() + "#";
        }

        // Remove last "#" and return
        return temp.subSequence(0, temp.length() - 1).toString();
    }
}
