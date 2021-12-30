package org.jyotisa.chennai;

import org.junit.jupiter.api.Test;
import org.jyotisa.AJyotisaTest;
import org.jyotisa.api.IKundali;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
public class ChennaiTest extends AJyotisaTest {

    @Test
    void printKundali() {
        final IKundali kundali = newChennaiKundali(getSwephExp());
        System.out.println(kundali);
    }
}
