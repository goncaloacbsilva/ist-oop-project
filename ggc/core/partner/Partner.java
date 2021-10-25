package ggc.core.partner;

public class Partner {
    private string _id;
    private string _name;
    private string _address;
    private int _points;
    private double _totalSellAmount;
    private double _totalbuyAmount;

    public Partner(String name, String adress){
        _name=name;
        _address=address;

    }

    public int getPoints(){
        return _points;
    }
    public void addPoints(int amount){
        _points += 10*amount;
    }
    public void takePoints(int amount){
        _points -= amount
    }
}
