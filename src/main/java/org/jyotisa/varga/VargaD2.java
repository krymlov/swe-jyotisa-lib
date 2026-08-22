/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD2;

import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.jyotisa.api.rasi.IRasi.rasiFid0;
import static org.jyotisa.rasi.ERasi.byLongitude;
import static org.swisseph.api.ISweConstants.RASI_LENGTH;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * 2 Hora - <b>Jagannatha Hora's "D-2 (US)" scheme</b>.
 *
 * <h2>The rule</h2>
 * The hora runs <b>forward</b> through an odd sign and <b>backward</b> through an even one, and
 * the doubled position is then laid on the whole zodiac rather than being folded into the two
 * signs of the Sun and the Moon:
 *
 * <pre>
 * s = the 0-based sign, d = the degree within it
 * x = s*30 + (s is an odd sign ? d : 30 - d)
 * the varga position is fix360(2 * x)
 * </pre>
 *
 * <h2>Why this and not the classical hora</h2>
 * <b>This deliberately departs from Brihat Parashara Hora Shastra</b>, and the departure is the
 * whole point: the classical hora sends every object into Cancer or Leo and nowhere else, because
 * the two halves of a sign belong to the Moon and the Sun. This library computed that until
 * 2026-08-22 and it was correct as far as it went - it simply was not the chart Jagannatha Hora
 * draws, and matching JHora is what was asked for.
 * <p>
 * The rule above was <b>recovered from JHora's own output</b> rather than from a text: fitted
 * against the seventeen reference reports in {@code org/jyotisa/refjhora8} and verified on
 * <b>527 of 527</b> object placements across all of them, exactly. It is pinned by
 * {@code JhoraRefChartsTest}.
 *
 * <h2>What still holds</h2>
 * The division is still equal - 15&deg; to each half - so the degree within the varga sign is the
 * same figure the classical hora produced; only the sign it is reported in changes. Reversing an
 * even sign is what makes the two schemes differ by an even number of signs and never an odd one.
 *
 * @author Yura Krymlov
 * @version 2.0, 2026-08
 */
public enum VargaD2 implements IVargaD2 {
    D2;

    @Override
    public IVargaD2[] all() {
        return values();
    }

    /**
     * The doubled position, taken forward through an odd sign and backward through an even one.
     * <p>
     * {@code rasiFid0} is 0-based, so a classical <i>odd</i> sign - Aries, Gemini, Leo ... - is an
     * <i>even</i> index. Getting that inversion the wrong way round produces a chart that is right
     * for half the zodiac, which is why it is spelled out here.
     */
    @Override
    public double virtualDegree(final double longitudeInD1) {
        final int rasi0 = rasiFid0(longitudeInD1);
        final double degree = rasiDegree(longitudeInD1);
        final double within = (rasi0 % 2 == 0) ? degree : (RASI_LENGTH - degree);

        return fix360((rasi0 * RASI_LENGTH + within) * 2.);
    }

    @Override
    public IRasi rasi(final double longitudeInD1) {
        return byLongitude(virtualDegree(longitudeInD1));
    }
}
