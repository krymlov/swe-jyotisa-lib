/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.vimsottari;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The 9 concrete {@code VimsottariDasa*} classes' {@code lord()} - trivially self-referential
 * (each dasha is ruled by its own namesake graha), but worth pinning since nothing else in
 * this library currently exercises the Vimsottari family: it has no {@code byLongitude}-style
 * lookup and, per this project's CLAUDE.md, no dasha-period computation exists anywhere in
 * the main source tree despite the enum/lord/year-length data being complete.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class EVimsottariDasaTest {

    @Test
    void lord_isTheSameNamedGraha() {
        assertEquals("SY", EVimsottariDasa.SURYA_DASA.dasa().lord().code());
        assertEquals("CH", EVimsottariDasa.CHANDRA_DASA.dasa().lord().code());
        assertEquals("MA", EVimsottariDasa.MANGALA_DASA.dasa().lord().code());
        assertEquals("RA", EVimsottariDasa.RAHU_DASA.dasa().lord().code());
        assertEquals("GU", EVimsottariDasa.GURU_DASA.dasa().lord().code());
        assertEquals("SA", EVimsottariDasa.SHANI_DASA.dasa().lord().code());
        assertEquals("BU", EVimsottariDasa.BUDHA_DASA.dasa().lord().code());
        assertEquals("KE", EVimsottariDasa.KETU_DASA.dasa().lord().code());
        assertEquals("SK", EVimsottariDasa.SHUKRA_DASA.dasa().lord().code());
    }

    @Test
    void yearsSumToOneHundredTwenty() {
        double total = 0;
        for (EVimsottariDasa d : EVimsottariDasa.values()) {
            if (d.dasa() == null) continue; // NIL
            total += d.dasa().length();
        }
        assertEquals(120., total, 1e-9);
    }
}
