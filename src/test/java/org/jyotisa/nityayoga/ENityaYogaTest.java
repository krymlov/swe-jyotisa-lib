/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.nityayoga;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * {@code ENityaYoga.byOffset} - keyed on {@code Surya + Chandra} (a SUM), unlike
 * Tithi/Karana's difference.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class ENityaYogaTest {

    static final double NY_LEN = 360. / 27.;

    @Test
    void byOffset_resolvesEachThirteenTwentySpan() {
        assertSame(ENityaYoga.VISHKAMBHA.yoga(), ENityaYoga.byOffset(0.));
        assertSame(ENityaYoga.PREETI.yoga(), ENityaYoga.byOffset(NY_LEN));
        assertSame(ENityaYoga.VAIDHRITI.yoga(), ENityaYoga.byOffset(359.999));
    }

    @Test
    void byLongitude_isSuryaPlusChandraNotTheDifference() {
        assertSame(ENityaYoga.byOffset(50.), ENityaYoga.byLongitude(20., 30.));
        // confirm it is NOT chandra-surya (which would give a different yoga here)
        assertSame(ENityaYoga.byOffset(30. - 20.), ENityaYoga.byOffset(10.));
    }

    @Test
    void byOffset_wrapsPastThreeHundredSixty() {
        assertSame(ENityaYoga.VISHKAMBHA.yoga(), ENityaYoga.byOffset(360. + NY_LEN / 2));
    }
}
