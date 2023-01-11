package org.jyotisa.chennai;

import org.junit.jupiter.api.Test;
import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
public class ChennaiTest extends AbstractTest {

    @Test
    void printKundali() {
        final IKundali kundali = newChennaiKundali(getSwephExp());
        System.out.println(kundali);
    }
}
