package ggc.core.partner;

public class Partner {
    private String _id;
    private String _name;
    private String _address;
    private int _points;
    private double _totalSellAmount;
    private double _totalbuyAmount;

    public Partner(String id, String name, String address) {
        _id = id;
        _name = name;
        _address = address;

    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public int getPoints() {
        return _points;
    }

    public void addPoints(int amount) {
        _points += 10 * amount;
    }

    public void takePoints(int amount) {
        _points -= amount;
    }
}
