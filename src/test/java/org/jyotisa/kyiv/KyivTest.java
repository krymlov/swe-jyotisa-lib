package org.jyotisa.kyiv;

import org.junit.jupiter.api.Test;
import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
public class KyivTest extends AbstractTest {

    @Test
    void printKundali() {
        final IKundali kundali = newKyivKundali(getSwephExp());
        System.out.println(kundali);
    }
}
