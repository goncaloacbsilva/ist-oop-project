package ggc.core.partner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ggc.core.partner.rank.Normal;
import ggc.core.partner.rank.Rank;
import ggc.core.partner.rank.RankFactory;
import ggc.core.product.Batch;
import ggc.core.exception.BadEntryException;

public class Partner implements Serializable, Comparable<Partner> {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;

    private String _id;
    private String _name;
    private String _address;
    private int _points;
    private double _totalSellAmount;
    private double _totalBuyAmount;
    private double _totalSell;
    private List<Batch> _batches;
    private Rank _rank;

    public Partner(String id, String name, String address) {
        _id = id;
        _name = name;
        _address = address;
        _batches = new ArrayList<>();
        _rank = new Normal();
    }

    public Partner(String[] objectString) {
        this(objectString[1], objectString[2], objectString[3]);
    }

    public String getId() {
        return _id;
    }
     
    public String getAddress(){
        return _address;
    }

    public double getTotalBuyAmount(){
        return _totalBuyAmount;
    }

    public double getTotalSellAmount(){
        return _totalSellAmount;
    }

    public double getTotalSell(){
        return _totalSell;
    }

    public String getRank(){
        return _rank.getRankName();
    }

    /**
     * Adds a new product batch to partner
     * @param batch
     */
    public void addBatch(Batch batch) {
        _batches.add(batch);
    }

    /**
     * Get product batches
     * @return list of the product batches
     */
    public List<Batch> getBatches() {
        List<Batch> batches = new ArrayList<>(_batches);
        Collections.sort(batches);
        return batches;
    }

    public String getName() {
        return _name;
    }

    public int getPoints() {
        return _points;
    }

    public void addPoints(int amount) {
        _points += 10 * amount;
        updateRank();
    }

    public void takePoints(int period) {
        _points *= _rank.getPointsPenalty(period);
        updateRank();
    }

    public void updateRank(){
        if (!_rank.checkRankMatch(_points)){
            _rank = RankFactory.getRank(_points);
        }
    }

    public String display() {
        return getId() + "|" + getName() + "|" + getAddress() + "|" + getRank() + "|" + 
        getPoints() + "|" + Math.round(getTotalBuyAmount()) + "|" + Math.round(getTotalSellAmount()) + "|" + Math.round(getTotalSell());
    }

    @Override
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }

        if (a == null) {
            return false;
        }

        return ((Partner)a).getId().equalsIgnoreCase(_id.toLowerCase());
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    public int compareTo(Partner partner) {
        return _id.compareTo(partner.getId());
    }
    
}
