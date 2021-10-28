package ggc.core.partner.rank;
import java.io.Serializable;

/** Implements Normal class */
public class Normal implements Rank, Serializable {
    
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;

    @Override
    public double getDiscount(int period, int N) {
        if (period >= N){
            return 0.90;
        }

        else if (N > period && period >= 0) {
            return 1.00;
        }

        else if (0 < Math.abs(period) && Math.abs(period) <= N) {
            return 1.00;

        }

        else if (Math.abs(period) > N ) {
            return 1.00;
        }

        else {
            return 0;
        }
        
    }
    

    @Override
    public double getPenalty(int period, int N){
        if (period >= N){
            return 1.00;
        }

        else if (N > period && period >= 0) {
            return 1.00;
        }

        else if (0 < Math.abs(period) && Math.abs(period) <= N) {

            return 1 + Math.abs(period) * 0.05;

        }

        else if(Math.abs(period) >N ) {
            return 1 + Math.abs(period) * 0.10;
        }

        else {
            return 0;
        }
        
    }
    
    @Override
    public String getRankName() {
        return "NORMAL";
    }

    @Override
    public Boolean checkRankMatch(int points) {
        return (points>=0 && points<=2000);
    }

    @Override
    public double getPointsPenalty(int period){
        return 0*period;
    }
}
