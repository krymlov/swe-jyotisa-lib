/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.arudha;

import org.jyotisa.api.arudha.IArudhaPada;
import org.jyotisa.api.arudha.IArudhaPadaEnum;
import org.jyotisa.api.arudha.IArudhaPadas;
import org.jyotisa.api.bhava.IBhava;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.bhava.EBhava;
import org.jyotisa.rasi.ERasi;
import org.swisseph.api.ISweObjects;

import static org.swisseph.api.ISweConstants.i12;
import static org.swisseph.api.ISweObjects.LG;

/**
 * The twelve arudha padas of one chart.
 *
 * <h2>What it needs, and what it deliberately does not</h2>
 * Only the ascendant's rasi and the rasi each graha occupies - both of which {@link ISweObjects}
 * already holds. There is no ephemeris call here and no degree arithmetic: an arudha is a whole
 * sign counted from another whole sign, so this is entirely a matter of signs.
 * <p>
 * The bhavas are therefore <b>whole sign</b> ones, counted from the ascendant's rasi, which is
 * what Jyotisha means by a bhava in this context and what the report's own {@code Bhava} column
 * uses. The chalit reading plays no part.
 *
 * <h2>The lords</h2>
 * Taken from {@link IRasi#lord()} - the traditional seven, with Mars owning Scorpio and Saturn
 * Aquarius rather than the nodes. That is checkable rather than assumed: in the 1970 reference
 * chart {@code A3} is the arudha of a Scorpio bhava and {@code A6} of an Aquarius one, and both
 * match Jagannatha Hora only under this lordship.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see IArudhaPadas
 */
public class ArudhaPadas implements IArudhaPadas {
    private static final long serialVersionUID = 8703192055374146318L;

    /** where the arudha is moved to when it would fall on the bhava itself or its opposite */
    protected static final int ESCAPE = 10;

    /** the rasi of each arudha pada, indexed 1..12; index 0 is the NIL rasi */
    protected final IRasi[] rasis = new IRasi[i12 + 1];

    protected final boolean calculated;

    /** the ascendant's own rasi, which every bhava is counted from */
    protected final int lagnaRasi;

    public ArudhaPadas(final ISweObjects sweObjects) {
        this.calculated = sweObjects.isCalculated(LG);
        this.lagnaRasi = calculated ? sweObjects.signs()[LG] : 0;

        for (int index = 0; index <= i12; index++) rasis[index] = ERasi.NIL.rasi();
        if (!calculated) return;

        final int[] signs = sweObjects.signs();
        final double[] longitudes = sweObjects.longitudes();

        for (int bhava = 1; bhava <= i12; bhava++) {
            final int rasi = advance(lagnaRasi, bhava - 1);
            final int at = signs[ArudhaLords.of(rasi, signs, longitudes)];

            if (at < 1 || at > i12) continue;   // a lord this chart never calculated

            rasis[bhava] = ERasi.byUid(arudha(rasi, at));
        }
    }

    /** the rasi {@code steps} signs on from {@code rasi}, both 1-based */
    protected static int advance(final int rasi, final int steps) {
        return (rasi - 1 + steps) % i12 + 1;
    }

    /** how far {@code to} is from {@code from}, counting both - 1 when they are the same */
    protected static int distance(final int from, final int to) {
        return (to - from + i12) % i12 + 1;
    }

    /**
     * The arudha of a bhava whose rasi is {@code rasi} and whose lord sits in {@code lord}.
     * <p>
     * The escape is applied when the arudha would fall on the bhava's own rasi or on the seventh
     * from it - the two positions a bhava cannot cast an image onto.
     */
    protected static int arudha(final int rasi, final int lord) {
        final int pada = advance(lord, distance(rasi, lord) - 1);

        return (pada == rasi || pada == advance(rasi, 6))
                ? advance(pada, ESCAPE - 1) : pada;
    }

    // ------------------------------------------------------------------ the contract

    @Override
    public boolean isCalculated() {
        return calculated;
    }

    @Override
    public IRasi rasi(final IArudhaPada pada) {
        if (null == pada || pada.isNil()) return ERasi.NIL.rasi();
        return rasis[pada.fid()];
    }

    @Override
    public IRasi rasi(final IBhava bhava) {
        if (null == bhava || bhava.isNil()) return ERasi.NIL.rasi();
        return rasis[bhava.fid()];
    }

    @Override
    public IBhava bhava(final IArudhaPada pada) {
        final IRasi rasi = rasi(pada);
        if (rasi.isNil() || !calculated) return EBhava.NIL.bhava();

        return EBhava.byUid(distance(lagnaRasi, rasi.fid()));
    }

    @Override
    public IRasi arudhaLagna() {
        return rasis[1];
    }

    @Override
    public IRasi upapadaLagna() {
        return rasis[i12];
    }

    @Override
    public IRasi[] all() {
        return rasis.clone();
    }

    // ------------------------------------------------------------------ the report

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(300)
                .append("Arudha Padas (A1 is the Arudha Lagna, A12 the Upapada Lagna)\n");

        if (!calculated) {
            return builder.append("  not calculated - the chart has no ascendant\n").toString();
        }

        for (org.swisseph.api.ISweEnumIterator<IArudhaPadaEnum> it = EArudhaPada.iterator();
             it.hasNext(); ) {

            final IArudhaPadaEnum pada = it.next();
            builder.append(String.format("%-4s= %-4s %s\n", pada.arudhaPada().label(),
                    rasi(pada.arudhaPada()).label(), bhava(pada.arudhaPada()).code()));
        }

        return builder.toString();
    }
}
