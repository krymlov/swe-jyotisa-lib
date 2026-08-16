/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */

package org.jyotisa.lagna;


import org.jyotisa.api.IKundaliFields;
import org.jyotisa.api.IKundaliOptions;
import org.jyotisa.api.lagna.ILagnaEntity;
import org.jyotisa.api.lagna.ILagnas;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.rasi.ERasi;
import org.swisseph.api.ISweObjects;

import static org.jyotisa.lagna.ELagna.*;
import static org.jyotisa.lagna.LagnaBhava.L1;
import static org.jyotisa.lagna.LagnaGhati.L3;
import static org.jyotisa.lagna.LagnaHora.L2;
import static org.jyotisa.lagna.LagnaIndu.L8;
import static org.jyotisa.lagna.LagnaJanma.L0;
import static org.jyotisa.lagna.LagnaPranapada.L7;
import static org.jyotisa.lagna.LagnaSree.L6;
import static org.jyotisa.lagna.LagnaVarnada.L5;
import static org.jyotisa.lagna.LagnaVighati.L4;
import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.api.ISweObjects.*;
import static org.swisseph.utils.IModuloUtils.fix360;
import static org.swisseph.utils.IModuloUtils.modulo;

/**
 * @author Yura Krymlov
 * @version 1.2, 2026-08
 */
public class Lagnas implements ILagnas {
    private static final long serialVersionUID = -1602056837470904368L;

    /**
     * Indu Lagna's classical planetary "Kala" (ray) values, Surya..Shani - Rahu/Ketu are not
     * used. B.V. Raman's method: the Kalas of the 9th lord from Lagna and the 9th lord from
     * Chandra are added, reduced modulo 12 (0 treated as 12), and that many rasis are counted
     * from Chandra's own rasi to reach Indu Lagna.
     */
    private static final int[] KALA = new int[SA + 1];

    static {
        KALA[SY] = 30;
        KALA[CH] = 16;
        KALA[MA] = 6;
        KALA[BU] = 8;
        KALA[GU] = 10;
        KALA[SK] = 12;
        KALA[SA] = 1;
    }

    protected final ILagnaEntity[] all = new LagnaEntity[INDU_LAGNA.uid() + 1];

    public Lagnas(IKundaliOptions options, IKundaliFields kundaliFields, ISweObjects sweObjects) {
        final double julianDay = sweObjects.sweJulianDate().julianDay();
        final double[] longitudes = sweObjects.longitudes();
        final double lagnaLongitude = longitudes[LG];
        final double moonLongitude = longitudes[CH];
        final double horaLongitude = kundaliFields.horaLagna();
        final double vighatiLongitude = kundaliFields.vighatiLagna();

        this.all[JANMA_LAGNA.uid()] = new LagnaEntity(lagnaLongitude, L0, julianDay);
        this.all[BHAVA_LAGNA.uid()] = new LagnaEntity(kundaliFields.bhavaLagna(), L1, julianDay);
        this.all[HORA_LAGNA.uid()] = new LagnaEntity(horaLongitude, L2, julianDay);
        this.all[GHATI_LAGNA.uid()] = new LagnaEntity(kundaliFields.ghatiLagna(), L3, julianDay);
        this.all[VIGHATI_LAGNA.uid()] = new LagnaEntity(vighatiLongitude, L4, julianDay);
        this.all[VARNADA_LAGNA.uid()] = new LagnaEntity(
                calcVarnada(lagnaLongitude, horaLongitude), L5, julianDay);
        this.all[SREE_LAGNA.uid()] = new LagnaEntity(
                calcSree(lagnaLongitude, moonLongitude), L6, julianDay);
        this.all[PRANAPADA_LAGNA.uid()] = new LagnaEntity(
                calcPranapada(vighatiLongitude, longitudes[SY]), L7, julianDay);
        this.all[INDU_LAGNA.uid()] = new LagnaEntity(
                calcIndu(lagnaLongitude, moonLongitude), L8, julianDay);
    }

    @Override
    public ILagnaEntity janma() {
        return all[JANMA_LAGNA.uid()];
    }

    @Override
    public ILagnaEntity bhava() {
        return all[BHAVA_LAGNA.uid()];
    }

    @Override
    public ILagnaEntity hora() {
        return all[HORA_LAGNA.uid()];
    }

    @Override
    public ILagnaEntity ghati() {
        return all[GHATI_LAGNA.uid()];
    }

    //@Override
    public ILagnaEntity vighati() {
        return all[VIGHATI_LAGNA.uid()];
    }

    //@Override
    public ILagnaEntity varnada() {
        return all[VARNADA_LAGNA.uid()];
    }

    //@Override
    public ILagnaEntity sree() {
        return all[SREE_LAGNA.uid()];
    }

    //@Override
    public ILagnaEntity pranapada() {
        return all[PRANAPADA_LAGNA.uid()];
    }

    //@Override
    public ILagnaEntity indu() {
        return all[INDU_LAGNA.uid()];
    }

    @Override
    public ILagnaEntity[] all() {
        return all;
    }

    /**
     * Sree Lagna: the Ascendant advanced by 27x the distance Chandra has already travelled
     * within its own naksatra - i.e. that fractional progress (0..1) is rescaled from one
     * naksatra span (13&deg;20') onto the full 360&deg; zodiac and added to the Lagna.
     * Verified against a worked BPHS-derived example (Lagna 175&deg;05' Virgo, Chandra 13&deg;06'
     * Libra in Swati -&gt; Sree Lagna 18&deg;47' Pisces) and against the reference PyJHora
     * implementation ({@code sree_lagna_from_moon_asc_longitudes}), which computes the same
     * quantity as {@code nakshatra_remainder_in_degrees * 27}.
     */
    static double calcSree(final double lagnaLongitude, final double moonLongitude) {
        final double naksatraRemainder = modulo(NAKSHATRA_LENGTH, moonLongitude);
        return fix360(lagnaLongitude + naksatraRemainder * 27);
    }

    /**
     * Pranapada Lagna: the Vighati Lagna longitude (the same sunrise-anchored progression used
     * for {@link #vighati()}, at 5&deg;/minute since sunrise), shifted by a classical offset that
     * trisects the zodiac by the sign-modality of the Sun: 0&deg; if Surya is in a movable sign,
     * 120&deg; if dual, 240&deg; if fixed. Cross-checked against the reference PyJHora
     * implementation ({@code pranapada_lagna}), which computes the identical quantity as
     * {@code vighati_lagna_longitude + offset}.
     */
    static double calcPranapada(final double vighatiLongitude, final double sunLongitude) {
        final double offset = IRasi.inFixedRasi(sunLongitude) ? 240D
                : IRasi.inDualRasi(sunLongitude) ? 120D : 0D;
        return fix360(vighatiLongitude + offset);
    }

    /**
     * Indu Lagna (B.V. Raman's method, the one PyJHora and JHora use by default): the Kalas
     * (classical planetary "ray" values - Surya 30, Chandra 16, Kuja 6, Budha 8, Guru 10,
     * Shukra 12, Shani 1; Rahu/Ketu excluded) of the lord of the 9th-from-Lagna and the lord of
     * the 9th-from-Chandra are added, reduced modulo 12 (a remainder of 0 is treated as 12),
     * and that many rasis are counted inclusively forward from Chandra's own rasi. The result
     * keeps Chandra's own degree-in-sign, exactly as the reference PyJHora implementation
     * ({@code indu_lagna}) does by construction (it reuses {@code moon_long}, the degree
     * portion, unchanged).
     */
    static double calcIndu(final double lagnaLongitude, final double moonLongitude) {
        final int lagnaSign0 = IRasi.rasiFid0(lagnaLongitude);
        final int moonSign0 = IRasi.rasiFid0(moonLongitude);

        final int ninthFromLagnaUid = (lagnaSign0 + i8) % i12 + i1;
        final int ninthFromMoonUid = (moonSign0 + i8) % i12 + i1;

        final int kalaLagna9th = KALA[ERasi.byUid(ninthFromLagnaUid).lord().uid()];
        final int kalaMoon9th = KALA[ERasi.byUid(ninthFromMoonUid).lord().uid()];

        int il = (kalaLagna9th + kalaMoon9th) % i12;
        if (i0 == il) il = i12;

        final int induSign0 = (moonSign0 + il - i1) % i12;
        return fix360(induSign0 * RASI_LENGTH + IRasi.rasiDegree(moonLongitude));
    }

    /**
     * Varnada Lagna (B.V. Raman's method, the default in JHora/PyJHora - there are three other
     * published methods that can disagree on the final direction of counting; this is the one
     * the reference software actually uses). Both Lagna and Hora Lagna are converted to an
     * inclusive rasi-count from the same-parity end of the zodiac (odd sign: counted forward
     * from Mesha; even sign: counted backward from Meena), the two counts are combined (added
     * if both signs share the same parity, otherwise the absolute difference), and the
     * <em>Lagna's own</em> parity - not the combined count's - decides whether the final count
     * is applied forward from Mesha or backward from Meena. Verified against the classical
     * worked example (Lagna Karkata, Hora Lagna Meena -&gt; Varnada Mithuna) and against the
     * reference PyJHora implementation ({@code _varnada_lagna_bv_raman}).
     */
    static double calcVarnada(final double lagnaLongitude, final double horaLongitude) {
        final int lagnaSign0 = IRasi.rasiFid0(lagnaLongitude);
        final int horaSign0 = IRasi.rasiFid0(horaLongitude);
        final boolean lagnaIsOdd = IRasi.inOddRasi(lagnaLongitude);
        final boolean horaIsOdd = IRasi.inOddRasi(horaLongitude);

        final int count1 = lagnaIsOdd ? countFromAries(lagnaSign0) : countFromPisces(lagnaSign0);
        final int count2 = horaIsOdd ? countFromAries(horaSign0) : countFromPisces(horaSign0);
        final int count = (lagnaIsOdd == horaIsOdd)
                ? (count1 + count2) % i12
                : Math.abs(count1 - count2) % i12;

        final int varnadaSign0 = lagnaIsOdd ? Math.floorMod(11 + count, 12) : Math.floorMod(-count, 12);
        return fix360(varnadaSign0 * RASI_LENGTH + IRasi.rasiDegree(lagnaLongitude));
    }

    // inclusive count from Mesha(0) forward to sign0, i.e. sign0's own 1-based position
    private static int countFromAries(final int sign0) {
        return sign0 + i1;
    }

    // inclusive count from Meena(11) backward to sign0
    private static int countFromPisces(final int sign0) {
        return Math.floorMod(23 - sign0, 12) + 1;
    }
}
