/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */
package org.jyotisa.panchanga;

import org.jyotisa.api.karana.IKarana;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.nityayoga.INityaYoga;
import org.jyotisa.api.panchanga.IPanchanga;
import org.jyotisa.api.tithi.ITithi;
import org.jyotisa.api.vaara.IVaara;
import org.jyotisa.karana.EKarana;
import org.jyotisa.naksatra.ENaksatraPada;
import org.jyotisa.nityayoga.ENityaYoga;
import org.jyotisa.tithi.ETithi;
import org.jyotisa.vaara.EVaara;

import java.util.Calendar;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-12
 */
public class Panchanga implements IPanchanga {
    private static final long serialVersionUID = 900910028988138532L;

    protected final double chandraLongitude;
    protected final double suryaLongitude;

    protected final INaksatraPada pada;
    protected final INityaYoga yoga;
    protected final IKarana karana;
    protected final IVaara vaara;
    protected final ITithi tithi;

    /**
     * @param sweDayOfWeek Sunday is represented by 0, Saturday by 6. Any discontinuity
     *                     in the sequence of weekdays is <b>not</b> taken into account!
     *                     <B>Attention: the numbers are different from the numbers returned by the {@link Calendar}</B>
     */
    public Panchanga(final double suryaLongitude, final int sweDayOfWeek, final double chandraLongitude) {
        this.chandraLongitude = chandraLongitude;
        this.suryaLongitude = suryaLongitude;

        yoga = ENityaYoga.byLongitude(suryaLongitude, chandraLongitude);
        karana = EKarana.byLongitude(suryaLongitude, chandraLongitude);
        tithi = ETithi.byLongitude(suryaLongitude, chandraLongitude);
        pada = ENaksatraPada.byLongitude(chandraLongitude);
        vaara = EVaara.byDayOfWeek(sweDayOfWeek);
    }

    @Override
    public double chandraLongitude() {
        return chandraLongitude;
    }

    @Override
    public double suryaLongitude() {
        return suryaLongitude;
    }

    @Override
    public INaksatraPada pada() {
        return pada;
    }

    @Override
    public INityaYoga yoga() {
        return yoga;
    }

    @Override
    public IKarana karana() {
        return karana;
    }

    @Override
    public IVaara vaara() {
        return vaara;
    }

    @Override
    public ITithi tithi() {
        return tithi;
    }
}
