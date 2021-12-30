/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-10
*/

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

import static org.swisseph.api.ISweConstants.NITYA_YOGA_LENGTH;
import static org.swisseph.api.ISweConstants.i1;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * @author  Yura Krymlov
 * @version 1.1, 2019-10
 */
public enum ENityaYoga implements INityaYogaEnum {
    NIL {
        @Override public int fid() { return 0; }
        @Override public String code() { return NIL_CD; }
        @Override public INityaYoga yoga() {return null; }
    }, // 0  Reserved
    VISHKAMBHA {@Override public INityaYogaVishkambha yoga() { return NityaYogaVishkambha.NY1; }},
    PREETI {@Override public INityaYogaPreeti yoga() { return NityaYogaPreeti.NY2; }},
    AYUSHMANA {@Override public INityaYogaAyushmana yoga() { return NityaYogaAyushmana.NY3; }},
    SAUBHAGYA {@Override public INityaYogaSaubhagya yoga() { return NityaYogaSaubhagya.NY4; }},
    SOBHANA {@Override public INityaYogaSobhana yoga() { return NityaYogaSobhana.NY5; }},
    ATIGANDA {@Override public INityaYogaAtiganda yoga() { return NityaYogaAtiganda.NY6; }},
    SUKARMAN {@Override public INityaYogaSukarman yoga() { return NityaYogaSukarman.NY7; }},
    DHRITI {@Override public INityaYogaDhriti yoga() { return NityaYogaDhriti.NY8; }},
    SHULA {@Override public INityaYogaShula yoga() { return NityaYogaShula.NY9; }},
    GANDA {@Override public INityaYogaGanda yoga() { return NityaYogaGanda.NY10; }},
    VRIDDHI {@Override public INityaYogaVriddhi yoga() { return NityaYogaVriddhi.NY11; }},
    DHRUVA {@Override public INityaYogaDhruva yoga() { return NityaYogaDhruva.NY12; }},
    VYAGHATA {@Override public INityaYogaVyaghata yoga() { return NityaYogaVyaghata.NY13; }},
    HARSHANA {@Override public INityaYogaHarshana yoga() { return NityaYogaHarshana.NY14; }},
    VAJRA {@Override public INityaYogaVajra yoga() { return NityaYogaVajra.NY15; }},
    SIDDHI {@Override public INityaYogaSiddhi yoga() { return NityaYogaSiddhi.NY16; }},
    VYATIPATA {@Override public INityaYogaVyatipata yoga() { return NityaYogaVyatipata.NY17; }},
    VARIYAN {@Override public INityaYogaVariyan yoga() { return NityaYogaVariyan.NY18; }},
    PARIGHA {@Override public INityaYogaParigha yoga() { return NityaYogaParigha.NY19; }},
    SHIVA {@Override public INityaYogaShiva yoga() { return NityaYogaShiva.NY20; }},
    SIDDHA {@Override public INityaYogaSiddha yoga() { return NityaYogaSiddha.NY21; }},
    SADHYA {@Override public INityaYogaSadhya yoga() { return NityaYogaSadhya.NY22; }},
    SHUBHA {@Override public INityaYogaShubha yoga() { return NityaYogaShubha.NY23; }},
    SHUKLA {@Override public INityaYogaShukla yoga() { return NityaYogaShukla.NY24; }},
    BRAHMA {@Override public INityaYogaBrahma yoga() { return NityaYogaBrahma.NY25; }},
    INDRA {@Override public INityaYogaIndra yoga() { return NityaYogaIndra.NY26; }},
    VAIDHRITI {@Override public INityaYogaVaidhriti yoga() { return NityaYogaVaidhriti.NY27; }};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public INityaYogaEnum first() {
        return VISHKAMBHA;
    }

    @Override
    public INityaYogaEnum last() {
        return VAIDHRITI;
    }

    @Override
    public INityaYogaEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<INityaYogaEnum> iterator() {
        return new SweEnumIterator<>(values(), VISHKAMBHA.uid());
    }

    public static ISweEnumIterator<INityaYogaEnum> iteratorFrom(final INityaYogaEnum nityaYoga) {
        return new SweEnumIterator<>(values(), nityaYoga.uid());
    }

    public static ISweEnumIterator<INityaYogaEnum> iteratorTo(final INityaYogaEnum nityaYoga) {
        return new SweEnumIterator<>(values(), VISHKAMBHA.uid(), nityaYoga.uid());
    }

    public static INityaYogaEnum byYoga(final INityaYoga nityaYoga) {
        return ISweEnum.byCode(nityaYoga.code(), values());
    }

    // Yoga = (Moon Longitude + Sun Longitude) / 13 deg. 20 min
    public static INityaYoga byLongitude(final double surya, final double chandra) {
        return byOffset(surya + chandra);
    }

    /**
     * @param offset is (Chandra longitude + Surya longitude)
     */
    public static INityaYoga byOffset(final double offset) {
        return byUid(i1 + (int)(fix360(offset) / NITYA_YOGA_LENGTH));
    }

    public static INityaYoga byName(final String name) {
        final ENityaYoga[] values = values();
        for (int i = 1; i < values.length; i++) {
            INityaYoga value = values[i].yoga().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).yoga();
    }

    public static INityaYoga byIndex(final int idx) {
        return ISweEnum.byIndex(idx, values()).yoga();
    }

    public static INityaYoga byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).yoga();
    }
}
