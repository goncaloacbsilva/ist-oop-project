package ggc.core.product;

//TODO: Serialization

public abstract class Product {
    private double _maxPrice;
    private String _id;

    public Product(String id) {
        _id = id;
    }

    public void updateMaxPrice(double maxPrice) {
        _maxPrice = maxPrice;
    }

    public String getId() {
        return _id;
    }

    public double getMaxPrice() {
        return _maxPrice;
    }

    @Override
    public boolean equals(Object a) {

        if (a == this) {
            return true;
        }

        if (a == null) {
            return false;
        }

        return ((Product)a).getId().toLowerCase().equals(_id.toLowerCase());
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }
}
