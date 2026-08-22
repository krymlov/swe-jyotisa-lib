/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.gochara;

import org.jyotisa.api.graha.IGraha;

/**
 * One station of a graha: the moment its apparent motion in longitude reverses.
 * <p>
 * {@link #longitude()} is the degree it turns at and {@link org.swisseph.api.ISweEnumEntity#julianDay()}
 * the moment, both as for any other gochara entity. What a station adds is {@link #retrograde()} -
 * which of the two kinds of turn this is.
 * <p>
 * The name follows {@link org.swisseph.api.ISweStation#retrograde()} rather than the
 * {@code vakri()} used elsewhere in this library, and the difference is deliberate: on an
 * ordinary graha {@code vakri()} says the graha <i>is</i> moving backwards right now, while
 * here the flag says the graha <i>starts</i> doing so at this instant. Same word, different
 * claim, so a different name.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see GrahaStationGochara
 */
public class GrahaStationEntity extends GrahaEntity {
    private static final long serialVersionUID = 7714163907781243508L;

    protected final boolean retrograde;

    public GrahaStationEntity(final double longitude, final IGraha graha,
                              final double julday, final boolean retrograde) {
        super(longitude, graha, julday);
        this.retrograde = retrograde;
    }

    /**
     * @return {@code true} when the graha turns <b>retrograde</b> here (it was direct and
     *         starts moving backwards), {@code false} when it turns <b>direct</b>
     */
    public boolean retrograde() {
        return retrograde;
    }

    @Override
    public String toString() {
        return entityEnum.code() + (retrograde ? " R " : " D ") + super.toString();
    }
}
