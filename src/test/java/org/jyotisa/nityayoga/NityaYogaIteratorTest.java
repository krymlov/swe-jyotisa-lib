/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.nityayoga;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.nityayoga.INityaYogaEntity;
import org.jyotisa.api.nityayoga.INityaYogaEnum;

import static org.jyotisa.nityayoga.ENityaYoga.VAIDHRITI;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
public class NityaYogaIteratorTest extends AbstractTest {

    @Test
    void testForwardIterator() {
        final IKundali kundali = newChennaiKundali(getSwephExp());
        final NityaYogaIterator iterator = new NityaYogaIterator(kundali, true);
        INityaYogaEnum refEnum = ENityaYoga.byYoga(kundali.panchanga().yoga());

        for (int index = 0; index < VAIDHRITI.ordinal() * 2; index++, refEnum = refEnum.following()) {
            INityaYogaEntity entity = iterator.next();
            Assertions.assertSame(refEnum.yoga(), entity.entityEnum());
        }
    }

    @Test
    void testBackwardIterator() {
        final IKundali kundali = newChennaiKundali(getSwephExp());
        final NityaYogaIterator iterator = new NityaYogaIterator(kundali, false);
        INityaYogaEnum refEnum = ENityaYoga.byYoga(kundali.panchanga().yoga());
        refEnum = refEnum.following(); // we start from the end point

        for (int index = 0; index < VAIDHRITI.ordinal() * 2; index++, refEnum = refEnum.previous()) {
            INityaYogaEntity entity = iterator.next();
            Assertions.assertSame(refEnum.yoga(), entity.entityEnum());
        }
    }
}
