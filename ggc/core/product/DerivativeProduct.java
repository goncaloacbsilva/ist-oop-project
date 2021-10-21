package ggc.core.product;

import java.util.HashSet;
import java.util.Set;
import java.util.HashSet;

import ggc.core.product.Product;
import ggc.core.product.RecipeComponent;

public class DerivativeProduct extends Product {

    private Set<RecipeComponent> _recipe;
    private double _alpha;

    public DerivativeProduct(String id, Set<RecipeComponent> recipe, double alpha) {
        super(id);
        _recipe = new HashSet<RecipeComponent>(recipe);
        _alpha = alpha;
    }

    public Set<RecipeComponent> getRecipe() {
        return _recipe;
    }
}
