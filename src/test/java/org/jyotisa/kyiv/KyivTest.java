package org.jyotisa.kyiv;

import org.junit.jupiter.api.Test;
import org.jyotisa.AJyotisaTest;
import org.jyotisa.api.IKundali;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
public class KyivTest extends AJyotisaTest {

    @Test
    void printKundali() {
        final IKundali kundali = newKyivKundali(getSwephExp());
        System.out.println(kundali);
    }
}
