package ggc.core.partner.rank;
import java.lang.Math;

public class Elite implements Rank {
    
    @Override
    public double getDiscount(int period, int N) {
        if (period>=N){
            return 0.90;
        }

        else if (N > period && period >= 0) {
            return 0.90;
        }

        else if (0 < Math.abs(period) && Math.abs(period) <= N) {
            return 0.95;

        }

        else if(Math.abs(period) >N ) {
            return 1.00;
        }

        else {
            return 1;
        }
        
    }

    @Override
    public double getPenalty(int period, int N) {

        if (period>=N) {
            return 1.00;
        }

        else if (N > period && period >= 0) {
            return 1.00;
        }

        else if (0 < Math.abs(period) && Math.abs(period) <= N) {
            return 1.00;
        }

        else if ( Math.abs(period) >N ) {
            return 1.00;
        }

        else {
            return 1;
        }
    }
    
    @Override
    public String getRankName() {

        return "Elite";
    }

    @Override
    public Boolean checkRankMatch(int points) {
        
        return (points > 25000);
    }
}
