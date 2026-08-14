/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.lucknow;

import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.lagna.ILagnas;
import org.jyotisa.lagna.Lagnas;
import org.jyotisa.api.upagraha.IUpagrahas;
import org.jyotisa.app.KundaliRuntimeException;
import org.jyotisa.app.Kundali;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.app.SweObjectsOptions.LAHIRI_AYANAMSA;

/**
 * Of the 9 special {@code ILagna} types, only 4 (janma/bhava/hora/ghati) are implemented;
 * of the 11 {@code IUpagraha} types, only 5 (dhuma/vyatipaata/parivesha/indrachaapa/upaketu)
 * are implemented - the remaining 6, including the two most commonly used in practice
 * (Gulika, Maandi), throw {@link KundaliRuntimeException}. Pinned here so implementing one
 * updates this test rather than silently changing behavior; see this project's CLAUDE.md.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class UnimplementedFeaturesTest extends AbstractTest {
    static final int[] DATE_1947 = {1947, 8, 15, 10, 30};

    IKundali newLucknow1947() {
        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(getSwephExp(),
                new SweJulianDate(DATE_1947, 0f, 10.5), GEO_LUCKNOW, LAHIRI_AYANAMSA).completeBuild());
    }

    @Test
    void lagnas_fourImplementedTypesDoNotThrow() {
        ILagnas lagnas = newLucknow1947().lagnas();
        assertDoesNotThrow(lagnas::janma);
        assertDoesNotThrow(lagnas::bhava);
        assertDoesNotThrow(lagnas::hora);
        assertDoesNotThrow(lagnas::ghati);
    }

    @Test
    void lagnas_fiveUnimplementedTypesThrow() {
        // vighati()/varnada()/sree()/pranapada()/indu() are commented out of the ILagnas
        // interface itself (see swe-jyotisa-api's CLAUDE.md), so they're only reachable on
        // the concrete class
        Lagnas lagnas = (Lagnas) newLucknow1947().lagnas();
        for (Executable e : new Executable[]{lagnas::vighati, lagnas::varnada, lagnas::sree,
                lagnas::pranapada, lagnas::indu}) {
            KundaliRuntimeException ex = assertThrows(KundaliRuntimeException.class, e);
            org.junit.jupiter.api.Assertions.assertTrue(ex.getMessage().contains("not implemented"));
        }
    }

    @Test
    void upagrahas_fiveImplementedTypesDoNotThrow() {
        IUpagrahas upagrahas = newLucknow1947().upagrahas();
        assertDoesNotThrow(upagrahas::dhuma);
        assertDoesNotThrow(upagrahas::vyatipaata);
        assertDoesNotThrow(upagrahas::parivesha);
        assertDoesNotThrow(upagrahas::indrachaapa);
        assertDoesNotThrow(upagrahas::upaketu);
    }

    @Test
    void upagrahas_sixUnimplementedTypesThrow_notablyGulikaAndMaandi() {
        IUpagrahas upagrahas = newLucknow1947().upagrahas();
        for (Executable e : new Executable[]{upagrahas::kaala, upagrahas::mrityu,
                upagrahas::arthaprahaara, upagrahas::yamaghantaka, upagrahas::gulika, upagrahas::maandi}) {
            KundaliRuntimeException ex = assertThrows(KundaliRuntimeException.class, e);
            org.junit.jupiter.api.Assertions.assertTrue(ex.getMessage().contains("not implemented"));
        }
    }
}
