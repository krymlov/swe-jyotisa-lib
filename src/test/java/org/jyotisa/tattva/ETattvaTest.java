/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.tattva;

import org.junit.jupiter.api.Test;

import static org.jyotisa.graha.guru.GrahaGuru.GURU;
import static org.jyotisa.graha.mangala.GrahaMangala.MANGALA;
import static org.jyotisa.graha.budha.GrahaBudha.BUDHA;
import static org.jyotisa.graha.shani.GrahaShani.SHANI;
import static org.jyotisa.graha.shukra.GrahaShukra.SHUKRA;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The 5 concrete {@code Tattva*} classes' {@code lord()} - this specific graha attribution
 * (Akasha-Guru, Agni-Mangala, Prithvi-Budha, Vayu-Shani, Jala-Shukra) is one classical
 * scheme among several traditions; pinned here as this codebase's chosen mapping, not
 * asserted as the only correct one.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class ETattvaTest {

    @Test
    void lord_matchesThisCodebasesChosenAttribution() {
        assertEquals(GURU.code(), ETattva.AKASHA.tattva().lord().code());
        assertEquals(MANGALA.code(), ETattva.AGNI.tattva().lord().code());
        assertEquals(BUDHA.code(), ETattva.PRITHVI.tattva().lord().code());
        assertEquals(SHANI.code(), ETattva.VAYU.tattva().lord().code());
        assertEquals(SHUKRA.code(), ETattva.JALA.tattva().lord().code());
    }
}
