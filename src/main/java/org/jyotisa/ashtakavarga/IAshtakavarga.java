/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.ashtakavarga;

import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.rasi.IRasi;

/**
 * Bhinnashtakavarga (each of the 7 classical grahas' and Lagna's own 12-rasi "bindu" table) and
 * Sarvashtakavarga (their combined total per rasi) - the surface {@link Ashtakavarga} exposes,
 * pulled out as its own interface so callers (starting with {@code Kundali}) depend on the
 * contract rather than the one classical-table-driven implementation.
 * <p>
 * Deliberately kept local to {@code swe-jyotisa-lib} rather than moved to
 * {@code swe-jyotisa-api}, the same scoping {@link Ashtakavarga}'s own class doc already
 * documents: it is not part of {@code IKundali}, so a caller of that interface alone never sees
 * it - only code that already depends on this concrete library, such as {@code Kundali} itself,
 * does.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IAshtakavarga {

    /**
     * Whether the chart supplied a rasi for this point, i.e. whether its row means anything.
     * An uncalculated point has an all-zero Bhinnashtakavarga and contributes nothing to
     * anyone else's.
     *
     * @param point one of the 8 contributing points; anything else is simply not calculated
     */
    boolean isCalculated(IGraha point);

    /**
     * Whether all 8 points were available. When false the tables are <b>partial</b>: every
     * bindu count is short by whatever the missing points would have contributed, so the
     * classical per-graha totals (48/49/54/52/39/56/39) will not hold and the figures must
     * not be compared against a reference chart.
     * <p>
     * The usual cause is a chart built without the ascendant.
     */
    boolean isComplete();

    /**
     * Bhinnashtakavarga: the number of bindus (0-8) the given point receives in the given rasi.
     *
     * @param point one of the 7 classical grahas (Surya..Shani) or Lagna
     * @param rasi  the rasi to count bindus in
     */
    int bindu(IGraha point, IRasi rasi);

    /**
     * Sarvashtakavarga: the combined bindu total (0-56) of the 7 classical grahas (not Lagna)
     * in the given rasi.
     */
    int sarva(IRasi rasi);

    /** The 8 contributing points, Surya..Shani then Lagna. */
    IGraha[] points();
}
