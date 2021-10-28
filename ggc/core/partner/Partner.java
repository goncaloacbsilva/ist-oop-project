package ggc.core.partner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ggc.core.partner.rank.Rank;
import ggc.core.partner.rank.RankFactory;
import ggc.core.product.Batch;

/** Implements Partner class */
public class Partner implements Serializable, Comparable<Partner> {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;

    /** Partner Id */
    private String _id;

    /** Partner name */
    private String _name;

    /** Partner address */
    private String _address;

    /** Partner points */
    private int _points;

    /** Partner Total Sales */
    private double _totalSales;

    /** Partner Total Purchases */
    private double _totalPurchases;

    /** Partner Total Paid Sales */
    private double _paidSales;

    /** Partner Batches */
    private List<Batch> _batches;

    /** Partner Rank */
    private Rank _rank;

    /**
     * Creates a new Partner
     * @param id Partner id
     * @param name Partner name
     * @param address Partner address
     */
    public Partner(String id, String name, String address) {
        _id = id;
        _name = name;
        _address = address;
        _batches = new ArrayList<>();
        _rank = RankFactory.getRank(0);
    }

    /**
     * Get partner id
     * @return id
     */
    public String getId() {
        return _id;
    }

    /**
     * Get partner name
     * @return name
     */
    public String getName() {
        return _name;
    }

    /**
     * Get partner points
     * @return points
     */
    public int getPoints() {
        return _points;
    }
     
    /**
     * Get partner address
     * @return address
     */
    public String getAddress() {
        return _address;
    }

    /**
     * Get partner Total Purchases
     * @return total purchases
     */
    public double getTotalPurchases() {
        return _totalPurchases;
    }

    /**
     * Get partner Total Sales
     * @return total sales
     */
    public double getTotalSales() {
        return _totalSales;
    }

    /**
     * Get partner Paid Sales
     * @return paid sales
     */
    public double getPaidSales() {
        return _paidSales;
    }

    /**
     * Get partner rank name
     * @return rank name
     */
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

    /**
     * Check if the current rank is still valid (for the current points)
     * and updates partner rank according to his points
     */
    private void updateRank(){
        if (!_rank.checkRankMatch(_points)){
            _rank = RankFactory.getRank(_points);
        }
    }

    /**
     * Gives partner a certain amount of points 
     * calculated with the supplied price
     * @param amount
     */
    public void addPoints(double price) {
        _points += 10 * price;
        updateRank();
    }

    /**
     * Takes partner a certain amount of points
     * calculated with the supplied time period and the current rank policies
     * @param period
     */
    public void takePoints(int period) {
        _points *= _rank.getPointsPenalty(period);
        updateRank();
    }

    /**
     * Displays Partner Information
     * @return String (id|nome|endereço|estatuto|pontos|valor-compras|valor-vendas-efectuadas|valor-vendas-pagas)
     */
    public String toString() {
        return getId() + "|" + getName() + "|" + getAddress() + "|" + getRank() + "|" + 
        getPoints() + "|" + Math.round(getTotalPurchases()) + "|" + Math.round(getTotalSales()) + "|" + Math.round(getPaidSales());
    }

    /* Override equals in order to compare Partners by id */
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

    /* Override hashCode to compare Partner objects by their id */
    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    /* Implements Comparable interface method for sorting purposes */
    public int compareTo(Partner partner) {
        return _id.compareTo(partner.getId());
    }
    
}
