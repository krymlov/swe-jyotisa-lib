/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.lucknow;

import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.lagna.Lagnas;
import org.jyotisa.api.upagraha.IUpagrahas;
import org.jyotisa.app.Kundali;
import org.junit.jupiter.api.Test;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA_TRUE_NODE;

/**
 * All 9 special {@code ILagna} types and all 11 {@code IUpagraha} types are now implemented (the
 * 5 remaining lagnas - vighati/varnada/sree/pranapada/indu - and the 6 Kalavela upagrahas -
 * kaala/mrityu/arthaprahaara/yamaghantaka/gulika/maandi - were added 2026-08, see
 * {@link org.jyotisa.lagna.Lagnas} and
 * {@link org.jyotisa.upagraha.Upagrahas#calcKalavelaUpagrahas}). Pinned here so any future
 * regression updates this test rather than silently changing behavior; see this project's
 * CLAUDE.md.
 *
 * @author Yura Krymlov
 * @version 1.2, 2026-08
 */
class UnimplementedFeaturesTest extends AbstractTest {
    static final int[] DATE_1947 = {1947, 8, 15, 10, 30};

    IKundali newLucknow1947() {
        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(getSwephExp(),
                new SweJulianDate(DATE_1947, 0f, 10.5), GEO_LUCKNOW, TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild());
    }

    @Test
    void lagnas_allNineTypesDoNotThrow() {
        // vighati()/varnada()/sree()/pranapada()/indu() are still commented out of the ILagnas
        // interface itself (see swe-jyotisa-api's CLAUDE.md), so they're only reachable on
        // the concrete class - same scoping already used for Ashtakavarga
        Lagnas lagnas = (Lagnas) newLucknow1947().lagnas();
        assertDoesNotThrow(lagnas::janma);
        assertDoesNotThrow(lagnas::bhava);
        assertDoesNotThrow(lagnas::hora);
        assertDoesNotThrow(lagnas::ghati);
        assertDoesNotThrow(lagnas::vighati);
        assertDoesNotThrow(lagnas::varnada);
        assertDoesNotThrow(lagnas::sree);
        assertDoesNotThrow(lagnas::pranapada);
        assertDoesNotThrow(lagnas::indu);
    }

    @Test
    void upagrahas_allElevenTypesDoNotThrow() {
        IUpagrahas upagrahas = newLucknow1947().upagrahas();
        assertDoesNotThrow(upagrahas::dhuma);
        assertDoesNotThrow(upagrahas::vyatipaata);
        assertDoesNotThrow(upagrahas::parivesha);
        assertDoesNotThrow(upagrahas::indrachaapa);
        assertDoesNotThrow(upagrahas::upaketu);
        assertDoesNotThrow(upagrahas::kaala);
        assertDoesNotThrow(upagrahas::mrityu);
        assertDoesNotThrow(upagrahas::arthaprahaara);
        assertDoesNotThrow(upagrahas::yamaghantaka);
        assertDoesNotThrow(upagrahas::gulika);
        assertDoesNotThrow(upagrahas::maandi);
    }
}
