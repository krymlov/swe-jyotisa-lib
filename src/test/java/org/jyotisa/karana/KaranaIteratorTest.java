/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.karana;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jyotisa.AJyotisaTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.karana.IKarana;
import org.jyotisa.api.karana.IKaranaEntity;
import org.jyotisa.api.karana.IKaranaEnum;
import org.swisseph.api.ISweConstants;

import static org.jyotisa.karana.EKarana.KIMSTUGHNA;
import static org.swisseph.api.ISweObjects.CH;
import static org.swisseph.api.ISweObjects.SY;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
public class KaranaIteratorTest extends AJyotisaTest {

    @Test
    void testForwardIterator1st() {
        testForwardIterator(false);
    }

    @Test
    void testForwardIterator2nd() {
        testForwardIterator(true);
    }

    @Test
    void testBackwardIterator1st() {
        testBackwardIterator(false);
    }

    @Test
    void testBackwardIterator2nd() {
        testBackwardIterator(true);
    }

    // --------------------------------------------------------------------------------

    private void testForwardIterator(boolean startFrom2ndKaranaIfActual) {

        final IKundali kundali = newChennaiKundali(getSwephExp());

        final KaranaIterator iterator = new KaranaIterator(kundali, startFrom2ndKaranaIfActual, true);
        final IKaranaEnum refEnum = EKarana.values()[kundali.panchanga().karana().uid()];

        final double[] longitudes = kundali.sweObjects().longitudes();
        double offset = longitudes[CH] - longitudes[SY];
        IKarana karana = EKarana.byOffset(offset);
        Assertions.assertSame(refEnum.karana(), karana);

        IKaranaEntity entity = iterator.next();

        if (!startFrom2ndKaranaIfActual && !refEnum.karana().equals(entity.entityEnum())) {
            offset -= ISweConstants.KARANA_LENGTH;
            karana = EKarana.byOffset(offset);
        }

        for (int index = 0; index < KIMSTUGHNA.uid() * 24; index++) {
            Assertions.assertSame(karana, entity.entityEnum());

            entity = iterator.next();
            offset += ISweConstants.KARANA_LENGTH;
            karana = EKarana.byOffset(offset);
        }
    }

    // --------------------------------------------------------------------------------

    private void testBackwardIterator(boolean startFrom2ndKaranaIfActual) {

        final IKundali kundali = newChennaiKundali(getSwephExp());

        final KaranaIterator iterator = new KaranaIterator(kundali, startFrom2ndKaranaIfActual, false);
        final IKaranaEnum refEnum = EKarana.values()[kundali.panchanga().karana().uid()];

        final double[] longitudes = kundali.sweObjects().longitudes();
        double offset = longitudes[CH] - longitudes[SY];
        IKarana karana = EKarana.byOffset(offset);
        Assertions.assertSame(refEnum.karana(), karana);

        IKaranaEntity entity = iterator.next();

        if (!refEnum.karana().equals(entity.entityEnum())) {
            offset += ISweConstants.KARANA_LENGTH;
            karana = EKarana.byOffset(offset);
        }

        if (!karana.equals(entity.entityEnum())) {
            offset += ISweConstants.KARANA_LENGTH;
            karana = EKarana.byOffset(offset);
        }

        for (int index = 0; index < KIMSTUGHNA.uid() * 24; index++) {
            Assertions.assertSame(karana, entity.entityEnum());

            entity = iterator.next();
            offset -= ISweConstants.KARANA_LENGTH;
            karana = EKarana.byOffset(offset);
        }
    }
}
