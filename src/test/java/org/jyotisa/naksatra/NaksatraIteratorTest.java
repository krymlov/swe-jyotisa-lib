/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.naksatra;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.naksatra.INaksatraEntity;
import org.jyotisa.api.naksatra.INaksatraEnum;
import org.jyotisa.gochara.naksatra.NaksatraGrahaGochara;

import static org.jyotisa.naksatra.ENaksatra.REVATI;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
public class NaksatraIteratorTest extends AbstractTest {

    @Test
    void testForwardIterator() {
        final IKundali kundali = newChennaiKundali(getSwephExp());
        final NaksatraGrahaGochara iterator = new NaksatraGrahaGochara(kundali);
        INaksatraEnum refEnum = ENaksatra.byNaksatra(kundali.panchanga().pada().naksatra());

        for (int index = 0; index < REVATI.ordinal() * 10; index++, refEnum = refEnum.following()) {
            INaksatraEntity entity = iterator.next();
            Assertions.assertSame(refEnum.naksatra(), entity.entityEnum());
        }
    }

    @Test
    void testBackwardIterator() {
        final IKundali kundali = newChennaiKundali(getSwephExp());
        final NaksatraGrahaGochara iterator = new NaksatraGrahaGochara(kundali, false);
        INaksatraEnum refEnum = ENaksatra.byNaksatra(kundali.panchanga().pada().naksatra());
        refEnum = refEnum.following(); // we start from the end

        for (int index = 0; index < REVATI.ordinal() * 10; index++, refEnum = refEnum.previous()) {
            INaksatraEntity entity = iterator.next();
            Assertions.assertSame(refEnum.naksatra(), entity.entityEnum());
        }
    }

}
