/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.nityayoga;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.panchanga.IPanchanga;
import org.swisseph.utils.IDegreeUtils;

import static org.swisseph.api.ISweConstants.D100_NITYA_YOGA_LENGTH;
import static org.swisseph.api.ISweConstants.NITYA_YOGA_LENGTH;
import static org.swisseph.utils.IModuloUtils.modulo;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface INityaYoga extends IKundaliSequence<INityaYoga> {

    @Override
    default double length() {
        return NITYA_YOGA_LENGTH;
    }

    static double progress(final IPanchanga panchanga) {
        return IDegreeUtils.round(modulo(NITYA_YOGA_LENGTH,
            panchanga.chandraLongitude()+panchanga.suryaLongitude())
                * D100_NITYA_YOGA_LENGTH,2);
    }

    // Nitya Yogas
    /** 1. Vishkambha */    String NY01_CD = "NY1";
    /** 2. Preeti */        String NY02_CD = "NY2";
    /** 3. Aayushmaan */    String NY03_CD = "NY3";
    /** 4. Saubhaagya */    String NY04_CD = "NY4";
    /** 5. Sobhana */       String NY05_CD = "NY5";
    /** 6. Atiganda */      String NY06_CD = "NY6";
    /** 7. Sukarman */      String NY07_CD = "NY7";
    /** 8. Dhriti */        String NY08_CD = "NY8";
    /** 9. Shoola */        String NY09_CD = "NY9";
    /** 10. Ganda */        String NY10_CD = "NY10";
    /** 11. Vriddhi */      String NY11_CD = "NY11";
    /** 12. Dhruva */       String NY12_CD = "NY12";
    /** 13. Vyaaghaata */   String NY13_CD = "NY13";
    /** 14. Harshana */     String NY14_CD = "NY14";
    /** 15. Vajra */        String NY15_CD = "NY15";
    /** 16. Siddhi */       String NY16_CD = "NY16";
    /** 17. Vyatipaata */   String NY17_CD = "NY17";
    /** 18. Variyan */      String NY18_CD = "NY18";
    /** 19. Parigha */      String NY19_CD = "NY19";
    /** 20. Shiva */        String NY20_CD = "NY20";
    /** 21. Siddha */       String NY21_CD = "NY21";
    /** 22. Saadhya */      String NY22_CD = "NY22";
    /** 23. Subha */        String NY23_CD = "NY23";
    /** 24. Sukla */        String NY24_CD = "NY24";
    /** 25. Brahma */       String NY25_CD = "NY25";
    /** 26. Indra */        String NY26_CD = "NY26";
    /** 27. Vaidhriti */    String NY27_CD = "NY27";
}
