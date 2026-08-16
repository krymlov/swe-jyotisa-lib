package org.jyotisa.sadesati;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.graha.shani.ShaniSadeSati;
import org.swisseph.ISwissEph;
import org.swisseph.app.SweRuntimeException;

import static org.apache.commons.io.FileUtils.getTempDirectoryPath;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA;

@Execution(ExecutionMode.SAME_THREAD)
public class ShaniSadeSatiTest extends AbstractTest {

    @Test
    void testSadeSatiWithoutEpheThrowsException() {
        SweRuntimeException exception = Assertions.assertThrows(SweRuntimeException.class, () -> {
            try (ISwissEph swissEph = newSwissEph(getTempDirectoryPath())) {
                testSadeSati(newKyivKundali(swissEph));
            }
        });

        Assertions.assertTrue(exception.getMessage().contains("SwissEph file 'sepl_18.se1' not found in the paths of:"));
    }

    /**
     * Mean node on purpose. This is the one test that deliberately runs with no ephemeris files,
     * and an <b>upstream Swiss Ephemeris 2.10.03 bug</b> makes {@code SE_TRUE_NODE} fail in
     * exactly that situation when the ayanamsa is star-derived (True Citra) - it reports
     * "jd -0.001010 outside Moshier planet range" instead of falling back. Reproduces identically
     * in astro.com's own swetest, so it is not ours to fix; see
     * {@link AbstractTest#newKyivKundali(ISwissEph, org.swisseph.api.ISweObjectsOptions)}.
     */
    @Test
    void testSadeSatiWithoutEphe() {
        try (ISwissEph swissEph = newSwephExp(getTempDirectoryPath())) {
            testSadeSati(newKyivKundali(swissEph, TRUECITRA_AYANAMSA));
        }
    }

    @Test
    void testSadeSatiWithEphe() {
        testSadeSati(newKyivKundali(getSwephExp()));
    }

    void testSadeSati(IKundali kundali) {
        ShaniSadeSati sadeSati = new ShaniSadeSati(kundali);
        StringBuilder builder = new StringBuilder(2048);

        while (sadeSati.hasNext()) {
            builder.append(sadeSati.next()).append('\n');
        }

        System.out.println(builder);
    }

}
