package ggc.core.partner.rank;

public interface Rank {
    public double getDiscount(int period, int N);
    public double getPenalty(int period, int N);
    public double getPointsPenalty(int period);
    public String getRankName();
    public Boolean checkRankMatch(int points);
}
