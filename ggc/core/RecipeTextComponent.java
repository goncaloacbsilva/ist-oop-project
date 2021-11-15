package ggc.core;

public class RecipeTextComponent {
    private String _id;
    private int _amount;

    public RecipeTextComponent(String id, int amount) {
        _id = id;
        _amount = amount;
    }

    public int getAmount() {
        return _amount;
    }

    public String getId() {
        return _id;
    }
}
