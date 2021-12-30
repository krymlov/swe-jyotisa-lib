/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-09
*/

package org.jyotisa.karana;


import org.jyotisa.api.karana.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;
import org.swisseph.utils.IModuloUtils;

import static org.swisseph.api.ISweConstants.*;

/**
 * Each tithi is divided into 2 karanas. There are 11 karanas: (1) Bava, (2) Balava, (3)
 * Kaulava, (4) Taitula, (5) Garija, (6) Vanija, (7) Vishti, (8) Sakuna, (9) Chatushpada,
 * (10) Naga, and, (11) Kimstughna. The first 7 karanas repeat 8 times starting from the
 * 2nd half of the first lunar day of a month. The last 4 karanas come just once in a
 * month, starting from the 2nd half of the 29th lunar day and ending at the 1st half of the
 * first lunar day.
 *
 * @author Yura Krymlov
 * @version 1.1, 2019-10
 */
public enum EKarana implements IKaranaEnum {
    NIL {
        @Override public int fid() { return 0; }
        @Override public String code() { return NIL_CD; }
        @Override public IKarana karana() {return null; }
    }, // 0  Reserved
    BAVA {@Override public IKaranaBava karana() {return KaranaBava.KR1; }},
    BALAVA {@Override public IKaranaBalava karana() {return KaranaBalava.KR2; }},
    KAULAVA {@Override public IKaranaKaulava karana() {return KaranaKaulava.KR3; }},
    TAITULA {@Override public IKaranaTaitula karana() {return KaranaTaitula.KR4; }},
    GARIJA {@Override public IKaranaGarija karana() {return KaranaGarija.KR5; }},
    VANIJA {@Override public IKaranaVanija karana() {return KaranaVanija.KR6; }},
    VISHTI {@Override public IKaranaVishti karana() {return KaranaVishti.KR7; }},
    SAKUNA {@Override public IKaranaSakuna karana() {return KaranaSakuna.KR8; }},
    CHATUSHPADA {@Override public IKaranaChatushpada karana() {return KaranaChatushpada.KR9; }},
    NAGA {@Override public IKaranaNaga karana() {return KaranaNaga.KR10; }},
    KIMSTUGHNA {@Override public IKaranaKimstughna karana() {return KaranaKimstughna.KR11; }};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IKaranaEnum first() {
        return BAVA;
    }

    @Override
    public IKaranaEnum last() {
        return KIMSTUGHNA;
    }

    @Override
    public IKaranaEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<IKaranaEnum> iterator() {
        return new SweEnumIterator<>(values(), BAVA.ordinal());
    }

    public static ISweEnumIterator<IKaranaEnum> iteratorFrom(final IKaranaEnum karana) {
        return new SweEnumIterator<>(values(), karana.uid());
    }

    public static ISweEnumIterator<IKaranaEnum> iteratorTo(final IKaranaEnum karana) {
        return new SweEnumIterator<>(values(), BAVA.uid(), karana.uid());
    }

    public static IKaranaEnum byKarana(final IKarana lagna) {
        return ISweEnum.byCode(lagna.code(), values());
    }

    public static IKarana[] byLongitudes(final double surya, final double chandra) {
        final double offset = chandra - surya;
        return new IKarana[] { byOffset(offset), byOffset(offset + d6) };
    }

    // Karana = a half of Tithi
    public static IKarana byLongitude(final double surya, final double chandra) {
        return byOffset(chandra - surya);
    }

    public static IKarana byOffset(double offset) {
        offset = IModuloUtils.fix360(offset);

        final IKaranaEnum karana;
        if ( offset >= TH14th2ndP00 && offset < TH14th2ndP06 ) karana = SAKUNA;
        else if ( offset >= TH14th2ndP06 && offset < TH14th2ndP12 ) karana = CHATUSHPADA;
        else if ( offset >= TH14th2ndP12 && offset < TH14th2ndP18 ) karana = NAGA;
        else if ( offset >= d0 && offset < d6 ) karana = KIMSTUGHNA;
        else return byUid(i1 + ((int)(offset - d6) / i6) % 7);

        return karana.karana();
    }

    public static IKarana byName(final String name) {
        final EKarana[] values = values();
        for (int i = 1; i < values.length; i++) {
            IKarana value = values[i].karana().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).karana();
    }

    public static IKarana byIndex(final int index) {
        return ISweEnum.byIndex(index, values()).karana();
    }

    public static IKarana byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).karana();
    }
}
