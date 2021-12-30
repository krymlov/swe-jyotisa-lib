/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.tithi;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jyotisa.AJyotisaTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.tithi.ITithiEnum;

import static org.jyotisa.tithi.ETithi.KRISHNA_AMAVASYA;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
public class TithiIteratorTest extends AJyotisaTest {

    @Test
    void testForwardIterator() {
        final IKundali kundali = newChennaiKundali(getSwephExp());
        final TithiIterator iterator = new TithiIterator(kundali, true);
        ITithiEnum refEnum = ETithi.byTithi(kundali.panchanga().tithi());

        for (int index = 0; index < KRISHNA_AMAVASYA.ordinal() * 3; index++, refEnum = refEnum.following()) {
            TithiEntity entity = iterator.next();

            Assertions.assertSame(refEnum.paksa(), entity.entityEnum().paksa());
            Assertions.assertSame(refEnum.tithi(), entity.entityEnum());
        }
    }

    @Test
    void testBackwardIterator() {
        final IKundali kundali = newChennaiKundali(getSwephExp());
        final TithiIterator iterator = new TithiIterator(kundali, false);
        ITithiEnum refEnum = ETithi.byTithi(kundali.panchanga().tithi());
        refEnum = refEnum.following(); // we start from the end point

        for (int index = 0; index < KRISHNA_AMAVASYA.ordinal() * 3; index++, refEnum = refEnum.previous()) {
            TithiEntity entity = iterator.next();

            Assertions.assertSame(refEnum.paksa(), entity.entityEnum().paksa());
            Assertions.assertSame(refEnum.tithi(), entity.entityEnum());
        }
    }
}
