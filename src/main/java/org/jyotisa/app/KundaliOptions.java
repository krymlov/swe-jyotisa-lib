/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-12
*/

package org.jyotisa.app;


import org.jyotisa.api.IKundaliOptions;
import org.jyotisa.api.karaka.ICharaKarakaOption;

import static org.jyotisa.karaka.ECharaKarakaOption.EIGHT_KARAKAS;
import static org.jyotisa.karaka.ECharaKarakaOption.SEVEN_KARAKAS;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-12
 */
public class KundaliOptions implements IKundaliOptions {
    private static final long serialVersionUID = -948455369743542224L;

    public static final IKundaliOptions KUNDALI_7_KARAKAS = new KundaliOptions(SEVEN_KARAKAS);
    public static final IKundaliOptions KUNDALI_8_KARAKAS = new KundaliOptions(EIGHT_KARAKAS);

    protected final ICharaKarakaOption charaKaraka;

    /**
     * By default - SEVEN KARAKAS
     */
    public KundaliOptions() {
        charaKaraka = SEVEN_KARAKAS;
    }
    
    public KundaliOptions(ICharaKarakaOption charaKaraka) {
        this.charaKaraka = charaKaraka;
    }

    @Override
    public ICharaKarakaOption charaKarakaOption() {
        return charaKaraka;
    }
    
    public static IKundaliOptions byDefault() {
        return KUNDALI_7_KARAKAS;
    }
}
