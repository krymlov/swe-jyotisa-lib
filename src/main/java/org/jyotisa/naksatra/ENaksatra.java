/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-08
*/

package org.jyotisa.naksatra;

import org.jyotisa.api.naksatra.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

import static org.jyotisa.naksatra.NaksatraAnuradha.N17;
import static org.jyotisa.naksatra.NaksatraArdra.N6;
import static org.jyotisa.naksatra.NaksatraAshlesha.N9;
import static org.jyotisa.naksatra.NaksatraAshwini.N1;
import static org.jyotisa.naksatra.NaksatraBharani.N2;
import static org.jyotisa.naksatra.NaksatraChitra.N14;
import static org.jyotisa.naksatra.NaksatraDhanishta.N23;
import static org.jyotisa.naksatra.NaksatraHasta.N13;
import static org.jyotisa.naksatra.NaksatraJyeshtha.N18;
import static org.jyotisa.naksatra.NaksatraKrittika.N3;
import static org.jyotisa.naksatra.NaksatraMagha.N10;
import static org.jyotisa.naksatra.NaksatraMrigashira.N5;
import static org.jyotisa.naksatra.NaksatraMula.N19;
import static org.jyotisa.naksatra.NaksatraPunarvasu.N7;
import static org.jyotisa.naksatra.NaksatraPurvaAshadha.N20;
import static org.jyotisa.naksatra.NaksatraPurvaBhadrapada.N25;
import static org.jyotisa.naksatra.NaksatraPurvaPhalguni.N11;
import static org.jyotisa.naksatra.NaksatraPushya.N8;
import static org.jyotisa.naksatra.NaksatraRevati.N27;
import static org.jyotisa.naksatra.NaksatraRohini.N4;
import static org.jyotisa.naksatra.NaksatraShatabhisha.N24;
import static org.jyotisa.naksatra.NaksatraShravana.N22;
import static org.jyotisa.naksatra.NaksatraSwati.N15;
import static org.jyotisa.naksatra.NaksatraUttaraAshadha.N21;
import static org.jyotisa.naksatra.NaksatraUttaraBhadrapada.N26;
import static org.jyotisa.naksatra.NaksatraUttaraPhalguni.N12;
import static org.jyotisa.naksatra.NaksatraVishakha.N16;
import static org.swisseph.api.ISweConstants.NAKSHATRA_LENGTH;
import static org.swisseph.api.ISweConstants.NAKSHATRA_PADA_LENGTH;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * There are 27 Nakshatras. Every Nakshatra measures 13.20' (13 degrees and 20 minutes).<br><br>
 * Each Nakshatra is divided into 4 parts of 3.20' (3 degrees and 20 minutes).
 * <br>These parts are called padas.
 * <br>Each pada has the characteristics of a sign of the zodiac, starting with Aries.
 *
 * @author Yura Krymlov
 * @version 1.1, 2019-08
 */
public enum ENaksatra implements INaksatraEnum {
    NIL {
        @Override public int fid() { return 0; }
        @Override public String code() { return NIL_CD; }
        @Override public INaksatra naksatra() {return null; }
    }, // 0  Reserved
    ASHWINI {@Override public INaksatraAshwini naksatra() { return N1; }},
    BHARANI {@Override public INaksatraBharani naksatra() { return N2; }},
    KRITTIKA {@Override public INaksatraKrittika naksatra() { return N3; }},
    ROHINI {@Override public INaksatraRohini naksatra() { return N4; }},
    MRIGASHIRA {@Override public INaksatraMrigashira naksatra() { return N5; }},
    ARDRA {@Override public INaksatraArdra naksatra() { return N6; }},
    PUNARVASU {@Override public INaksatraPunarvasu naksatra() { return N7; }},
    PUSHYA {@Override public INaksatraPushya naksatra() { return N8; }},
    ASHLESHA {@Override public INaksatraAshlesha naksatra() { return N9; }},
    MAGHA {@Override public INaksatraMagha naksatra() { return N10; }},
    PURVA_PHALGUNI {@Override public INaksatraPurvaPhalguni naksatra() { return N11; }},
    UTTARA_PHALGUNI {@Override public INaksatraUttaraPhalguni naksatra() { return N12; }},
    HASTA {@Override public INaksatraHasta naksatra() { return N13; }},
    CHITRA {@Override public INaksatraChitra naksatra() { return N14; }},
    SWATI {@Override public INaksatraSwati naksatra() { return N15; }},
    VISHAKHA {@Override public INaksatraVishakha naksatra() { return N16; }},
    ANURADHA {@Override public INaksatraAnuradha naksatra() { return N17; }},
    JYESHTHA {@Override public INaksatraJyeshtha naksatra() { return N18; }},
    MULA {@Override public INaksatraMula naksatra() { return N19; }},
    PURVA_ASHADHA {@Override public INaksatraPurvaAshadha naksatra() { return N20; }},
    UTTARA_ASHADHA {@Override public INaksatraUttaraAshadha naksatra() { return N21; }},
    SHRAVANA {@Override public INaksatraShravana naksatra() { return N22; }},
    DHANISHTA {@Override public INaksatraDhanishta naksatra() { return N23; }},
    SHATABHISHA {@Override public INaksatraShatabhisha naksatra() { return N24; }},
    PURVA_BHADRAPADA {@Override public INaksatraPurvaBhadrapada naksatra() { return N25; }},
    UTTARA_BHADRAPADA {@Override public INaksatraUttaraBhadrapada naksatra() { return N26; }},
    REVATI {@Override public INaksatraRevati naksatra() { return N27; }};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public INaksatraEnum first() {
        return ASHWINI;
    }

    @Override
    public INaksatraEnum last() {
        return REVATI;
    }

    @Override
    public INaksatraEnum[] all() {
        return values();
    }

    @Override
    public INaksatraPada pada(final int pada) {
        if (1 > pada || pada > 4) {
            throw new RuntimeException("Wrong pada number: " + pada);
        }

        return ENaksatraPada.byUid(pada + (4 * (fid() - 1)));
    }

    public static ISweEnumIterator<INaksatraEnum> iterator() {
        return new SweEnumIterator<>(values(), ASHWINI.ordinal());
    }

    public static ISweEnumIterator<INaksatraEnum> iteratorFrom(final INaksatraEnum naksatra) {
        return new SweEnumIterator<>(values(), naksatra.uid());
    }

    public static ISweEnumIterator<INaksatraEnum> iteratorTo(final INaksatraEnum naksatra) {
        return new SweEnumIterator<>(values(), ASHWINI.uid(), naksatra.uid());
    }

    public static INaksatraEnum byNaksatra(final INaksatra naksatra) {
        return ISweEnum.byCode(naksatra.code(), values());
    }

    // Nakshatra = Chandra Longitude / 13 deg. 20 min
    public static INaksatra byLongitude(final double longitude) {
        return byUid(1 + (int) (fix360(longitude) / NAKSHATRA_LENGTH));
    }

    public static INaksatra byName(final String name) {
        final ENaksatra[] values = values();
        for (int i = 1; i < values.length; i++) {
            INaksatra value = values[i].naksatra().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).naksatra();
    }

    public static INaksatra byIndex(final int idx) {
        return ISweEnum.byIndex(idx, values()).naksatra();
    }

    public static INaksatra byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).naksatra();
    }

    public static byte pada(final double longitude) {
        return (byte)(((fix360(longitude) / NAKSHATRA_PADA_LENGTH) % 4) + 1);
    }

}
