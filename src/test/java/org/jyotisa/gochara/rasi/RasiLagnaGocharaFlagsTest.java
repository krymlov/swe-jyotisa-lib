/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.gochara.rasi;

import org.junit.jupiter.api.Test;
import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.swisseph.api.ISweObjectsOptions;
import org.swisseph.app.SweObjectsOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.swisseph.api.ISweObjectsOptions.DEFAULT_SS_TRANSIT_FLAGS;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA_TRUE_NODE;
import static swisseph.SweConst.SEFLG_TRANSIT_LONGITUDE;
import static swisseph.SweConst.SEFLG_TRUEPOS;

/**
 * {@link RasiLagnaGochara#transitCalcFlags()} must <b>clear</b> {@code SEFLG_TRUEPOS}, never
 * toggle it.
 * <p>
 * It used to be written as {@code super.transitCalcFlags() ^ SEFLG_TRUEPOS}, and every
 * existing test passed - because {@code DEFAULT_SS_TRANSIT_FLAGS} carries the bit, so XOR
 * removed it. The defect only surfaces for a caller who builds transit flags without
 * {@code SEFLG_TRUEPOS}, which {@code SweObjectsOptions.Builder.transitFlags(...)} permits:
 * there XOR switches the flag <b>on</b>, the opposite of the intent, with nothing to say so.
 * <p>
 * The second test below is the one that distinguishes the two implementations; the first
 * pins the default case so the intended behaviour stays covered either way.
 */
class RasiLagnaGocharaFlagsTest extends AbstractTest {

    private IKundali kyivWithTransitFlags(final int transitFlags) {
        final ISweObjectsOptions options = new SweObjectsOptions.Builder()
                .options(TRUECITRA_AYANAMSA_TRUE_NODE)
                .transitFlags(transitFlags)
                .build();
        return newKyivKundali(getSwissEph(), options);
    }

    @Test
    void withTheDefaultFlagsTruePosIsRemoved() {
        // the default really does carry the bit - otherwise this test proves nothing
        assertNotEquals(0, DEFAULT_SS_TRANSIT_FLAGS & SEFLG_TRUEPOS,
                "DEFAULT_SS_TRANSIT_FLAGS is expected to carry SEFLG_TRUEPOS");

        final IKundali kundali = kyivWithTransitFlags(DEFAULT_SS_TRANSIT_FLAGS);
        final int flags = new RasiLagnaGochara(kundali).transitCalcFlags();

        assertEquals(0, flags & SEFLG_TRUEPOS, "SEFLG_TRUEPOS must be cleared");
        assertEquals(DEFAULT_SS_TRANSIT_FLAGS & ~SEFLG_TRUEPOS, flags,
                "nothing but SEFLG_TRUEPOS may change");
    }

    @Test
    void whenTheCallerNeverSetTruePosItMustNotBeTurnedOn() {
        final int without = DEFAULT_SS_TRANSIT_FLAGS & ~SEFLG_TRUEPOS;

        final IKundali kundali = kyivWithTransitFlags(without);
        final int flags = new RasiLagnaGochara(kundali).transitCalcFlags();

        // with the old '^' this came back with SEFLG_TRUEPOS set
        assertEquals(0, flags & SEFLG_TRUEPOS,
                "clearing an already-clear flag must leave it clear, not toggle it on");
        assertEquals(without, flags, "flags must be unchanged when there is nothing to clear");
    }

    @Test
    void everyOtherTransitFlagSurvives() {
        final IKundali kundali = kyivWithTransitFlags(DEFAULT_SS_TRANSIT_FLAGS);
        final int flags = new RasiLagnaGochara(kundali).transitCalcFlags();

        assertNotEquals(0, flags & SEFLG_TRANSIT_LONGITUDE,
                "the longitude-transit flag is what the search is driven by");
    }
}
