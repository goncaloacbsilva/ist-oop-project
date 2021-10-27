package ggc.core.partner.rank;

import ggc.core.partner.rank.*;

public class RankFactory {
    public static Rank getRank(int points) {

        if(points > 25000) {
            return new Elite();
        }

        else if( 25000 >= points && points > 2000 ) {

            return new Selection();
        }

        else if( 2000 >= points && points >=0 ) {

            return new Normal();
        }
        
        return null;
    }
}
