package org.jyotisa.lucknow;

import org.junit.jupiter.api.Test;
import org.jyotisa.AbstractTest;
import org.jyotisa.app.Kundali;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import java.io.IOException;

import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.app.SweObjectsOptions.LAHIRI_AYANAMSA;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
public class LucknowTest extends AbstractTest {
    final int[] date1947 = new int[]{1947, 8, 15, 10, 30, 0};

    @Test
    void testLahiriKundali() throws IOException {
        ISweObjects sweObjects = new SweObjects(getSwephExp(), new SweJulianDate(date1947, 0f),
                GEO_LUCKNOW, LAHIRI_AYANAMSA).completeBuild();

        String kundaliText = printKundali(new Kundali(KUNDALI_7_KARAKAS, sweObjects)).toString();
        loadAndAssert("LUCKNOW1947_A01.txt", kundaliText);
        System.out.println(kundaliText);
    }

    @Test
    void testTruecitraKundali() throws IOException {
        ISweObjects sweObjects = new SweObjects(getSwephExp(), new SweJulianDate(date1947, 0f),
                GEO_LUCKNOW, TRUECITRA_AYANAMSA).completeBuild();
        String kundaliText = printKundali(new Kundali(KUNDALI_7_KARAKAS, sweObjects)).toString();
        loadAndAssert("LUCKNOW1947_A27.txt", kundaliText);
        System.out.println(kundaliText);
    }
}
