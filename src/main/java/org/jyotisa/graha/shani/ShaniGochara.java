/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2018-02
 */

package org.jyotisa.graha.shani;

import org.jyotisa.api.IKundali;
import org.jyotisa.api.rasi.IRasiEnum;
import org.jyotisa.rasi.ERasi;
import org.swisseph.api.ISweSegment;

/**
 * http://cafejyotish.com/394-shani-dhaiya-transit-your-life.html
 * <p>
 * Shani Dhaiya starts when Saturn transits through the 4th or 8th house from Moon in the birth-chart.
 * Saturn remains for 2 1/2 years in one sign, therefore, this transit is commonly known
 * as Shani Dhaiya (2 1/2 year period) of Saturn. Saturn's transit in the 4th house from
 * the Moonsign is called Kantak Shani. Just like Shani Sadesati, it is possible to move ahead and grow
 * in Shani Dhaiyya too even though it is supposed to be an inauspicious transit.
 * <p>
 * If in the birth-chart, Saturn and Moon are in a good position and the effects of Saturn's transit is also
 * benefic the native has the possibility to gain auspicious results. Saturn has three kinds of aspects;
 * one is the 3rd aspect, second is 7th which is also known as the full aspect and the third is 10th aspect.
 * <p>
 * When Saturn is in the 8th house from Moon in the birth-chart, it aspects the 10th, 2nd and the 5th house.
 * The tenth house is also known as the house of Karma, the 2nd house is the house of income and
 * the 5th house is the house of children and high-education. like the person who has attained
 * good education gets good sources of livelihood, and good income, similarly, all these houses
 * are connected to each other. Due to the aspect of Saturn on the above houses, the native
 * may find obstacles in his life. The 8th house is called the house of longevity.
 * Longevity is more important for an individual than any other aspect of life, because of this reason,
 * high priority is given to the Ashtam Shani, which directly affects longevity. When Saturn is placed
 * in the eighth house from the Moonsign, it is known as Ashtam Shani.
 *
 * @author Yura Krymlov
 * @version 1.0, 2019-01
 */
public class ShaniGochara extends SaChGochara {
    protected final int bhava;

    /**
     * @param bhava Saturn transits through the house/bhava from the Moon in the birth-chart
     */
    public ShaniGochara(final IKundali kundali, final int bhava) {
        super(kundali, 4);
        this.bhava = bhava;
    }

    protected void addGocharaLongitudes(final StringBuilder builder) {
        builder.append(" -lon");

        IRasiEnum chRasi = ERasi.byRasi(kundali.grahas().chandra().pada().rasi());
        ISweSegment interval = chRasi.follow(bhava - 1).segment();
        builder.append(interval.start()).append('/').append(interval.close());
        super.startDegree = interval.start();
    }

}
