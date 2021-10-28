package ggc.core.partner.rank;

import ggc.core.partner.rank.Rank;
import ggc.core.partner.rank.Normal;
import ggc.core.partner.rank.Elite;
import ggc.core.partner.rank.Selection;

/** Implements RankFactory class */
public class RankFactory {
    /**
     * Returns matching rank for the supplied points
     * @param points
     * @return Rank
     */
    public static Rank getRank(int points) {

        if (points > 25000) {
            return new Elite();
        }
        else if (25000 >= points && points > 2000) {
            return new Selection();
        }
        else if (2000 >= points && points >= 0) {
            return new Normal();
        }
        
        return null;
    }
}
