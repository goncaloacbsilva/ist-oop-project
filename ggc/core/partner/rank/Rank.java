package ggc.core.partner.rank;

public interface Rank {
    public double getDiscount(int period);
    public double getPenalty(int period);
    public String getRankName();
    public Boolean checkRankMatch(int points);
}
