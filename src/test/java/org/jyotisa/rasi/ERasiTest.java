/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.rasi;

import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.tattva.ITattva;
import org.jyotisa.tattva.TattvaAgni;
import org.jyotisa.tattva.TattvaJala;
import org.jyotisa.tattva.TattvaPrithvi;
import org.jyotisa.tattva.TattvaVayu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.swisseph.api.ISweGender;

import static org.jyotisa.graha.budha.GrahaBudha.BUDHA;
import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;
import static org.jyotisa.graha.guru.GrahaGuru.GURU;
import static org.jyotisa.graha.mangala.GrahaMangala.MANGALA;
import static org.jyotisa.graha.shani.GrahaShani.SHANI;
import static org.jyotisa.graha.shukra.GrahaShukra.SHUKRA;
import static org.jyotisa.graha.surya.GrahaSurya.SURYA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.swisseph.app.SweGender.FEMALE;
import static org.swisseph.app.SweGender.MALE;

/**
 * {@code ERasi.byLongitude} plus the 12 concrete {@code Rasi*} classes' {@code lord()}/
 * {@code gender()}/{@code tattva()}/{@code badhaka()} - the mappings swe-jyotisa-api leaves
 * entirely abstract (see its CLAUDE.md notes). All 12 verified directly against classical
 * Jyotisha: alternating gender starting with Mesha=male, correct elements, and the badhaka
 * rule (movable sign -> 11th sign is badhaka, fixed sign -> 9th, dual sign -> 7th).
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class ERasiTest {

    @Test
    void byLongitude_resolvesEachThirtyDegreeSpan() {
        assertSame(ERasi.MESHA.rasi(), ERasi.byLongitude(0.));
        assertSame(ERasi.MESHA.rasi(), ERasi.byLongitude(29.999));
        assertSame(ERasi.VRISHABHA.rasi(), ERasi.byLongitude(30.));
        assertSame(ERasi.MEENA.rasi(), ERasi.byLongitude(359.999));
    }

    @ParameterizedTest
    @EnumSource(value = ERasi.class, names = "NIL", mode = EnumSource.Mode.EXCLUDE)
    void lordGenderTattva_matchClassicalAssignments(ERasi e) {
        IRasi rasi = e.rasi();
        IGraha expectedLord;
        ISweGender expectedGender;
        ITattva expectedTattva;

        switch (e) {
            case MESHA:     expectedLord = MANGALA; expectedGender = MALE;   expectedTattva = TattvaAgni.AGN;    break;
            case VRISHABHA: expectedLord = SHUKRA;  expectedGender = FEMALE; expectedTattva = TattvaPrithvi.PRI; break;
            case MITHUNA:   expectedLord = BUDHA;   expectedGender = MALE;   expectedTattva = TattvaVayu.VAY;    break;
            case KARKATA:   expectedLord = CHANDRA; expectedGender = FEMALE; expectedTattva = TattvaJala.JAL;    break;
            case SIMHA:     expectedLord = SURYA;   expectedGender = MALE;   expectedTattva = TattvaAgni.AGN;    break;
            case KANYA:     expectedLord = BUDHA;   expectedGender = FEMALE; expectedTattva = TattvaPrithvi.PRI; break;
            case TULA:      expectedLord = SHUKRA;  expectedGender = MALE;   expectedTattva = TattvaVayu.VAY;    break;
            case VRISCHIKA: expectedLord = MANGALA; expectedGender = FEMALE; expectedTattva = TattvaJala.JAL;    break;
            case DHANUS:    expectedLord = GURU;    expectedGender = MALE;   expectedTattva = TattvaAgni.AGN;    break;
            case MAKARA:    expectedLord = SHANI;   expectedGender = FEMALE; expectedTattva = TattvaPrithvi.PRI; break;
            case KUMBHA:    expectedLord = SHANI;   expectedGender = MALE;   expectedTattva = TattvaVayu.VAY;    break;
            case MEENA:     expectedLord = GURU;    expectedGender = FEMALE; expectedTattva = TattvaJala.JAL;    break;
            default: throw new IllegalStateException(e.name());
        }

        assertSame(expectedLord, rasi.lord(), e + ".lord()");
        assertSame(expectedGender, rasi.gender(), e + ".gender()");
        assertSame(expectedTattva, rasi.tattva(), e + ".tattva()");
    }

    @ParameterizedTest
    @EnumSource(value = ERasi.class, names = "NIL", mode = EnumSource.Mode.EXCLUDE)
    void badhaka_followsTheModalityRule(ERasi e) {
        IRasi rasi = e.rasi();
        // movable sign -> 11th sign from it is badhaka; fixed -> 9th; dual -> 7th
        int stepsToBadhaka;
        if (rasi.movable()) stepsToBadhaka = 10;      // 11th house = +10 signs
        else if (rasi.fixed()) stepsToBadhaka = 8;    // 9th house = +8 signs
        else stepsToBadhaka = 6;                       // 7th house = +6 signs

        IRasi expectedBadhaka = ERasi.byLongitude((rasi.fid() - 1 + stepsToBadhaka) % 12 * 30. + 1.);
        assertSame(expectedBadhaka, rasi.badhaka(), e + ".badhaka()");
    }

    @Test
    void genderAlternatesStartingWithMeshaMale() {
        ERasi[] order = {ERasi.MESHA, ERasi.VRISHABHA, ERasi.MITHUNA, ERasi.KARKATA, ERasi.SIMHA, ERasi.KANYA,
                ERasi.TULA, ERasi.VRISCHIKA, ERasi.DHANUS, ERasi.MAKARA, ERasi.KUMBHA, ERasi.MEENA};
        for (int i = 0; i < order.length; i++) {
            ISweGender expected = (i % 2 == 0) ? MALE : FEMALE;
            assertEquals(expected, order[i].rasi().gender(), order[i].name());
        }
    }
}
