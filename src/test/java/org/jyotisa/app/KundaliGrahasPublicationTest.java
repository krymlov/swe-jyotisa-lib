/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.app;

import org.junit.jupiter.api.Test;
import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundaliOptions;
import org.jyotisa.api.graha.IGrahaEntity;
import org.jyotisa.api.graha.IGrahas;
import org.jyotisa.api.karaka.ICharaKarakaOption;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import static org.junit.jupiter.api.Assertions.*;
import static org.jyotisa.karaka.ECharaKarakaOption.SEVEN_KARAKAS;
import static org.swisseph.api.ISweObjects.RA;
import static org.swisseph.api.ISweObjects.SY;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA_TRUE_NODE;

/**
 * {@code Kundali.grahas()} must publish its field only after the Chara karakas have been
 * assigned.
 * <p>
 * It used to assign {@code grahas} first and then mutate the entities it had already made
 * reachable:
 * <pre>
 * grahas = new Grahas(options, sweObjects);   // field is live from here
 * ...
 * entities.get(index).charaKaraka(charaKaraka);   // still being filled in
 * </pre>
 * A second caller entering {@code grahas()} inside that window passes the
 * {@code null != grahas} guard and takes the object away with {@code charaKaraka()} still
 * {@code null} on every graha. The class is not synchronised, and this workspace runs its
 * tests in parallel, so the window is real rather than theoretical.
 * <p>
 * <b>The first test below is deterministic, not a stress test.</b> {@code grahas()} calls
 * {@code options.charaKarakaOption()} three times between constructing {@code Grahas} and
 * finishing the karakas, so a spy passed in as the options gives a precise observation point
 * in the middle of the build - no threads and no timing needed to tell the two
 * implementations apart.
 */
class KundaliGrahasPublicationTest extends AbstractTest {

    /** exposes the protected lazy field so the test can see when it becomes reachable */
    private static class ObservingKundali extends Kundali {
        private static final long serialVersionUID = 1L;

        transient boolean observedAtLeastOnce;
        transient boolean fieldWasReachableDuringBuild;

        ObservingKundali(IKundaliOptions options, ISweObjects sweObjects) {
            super(options, sweObjects);
        }

        void observe() {
            observedAtLeastOnce = true;
            if (null != this.grahas) fieldWasReachableDuringBuild = true;
        }
    }

    /** an options object that peeks at the kundali every time it is consulted */
    private static class ObservingOptions implements IKundaliOptions {
        private static final long serialVersionUID = 1L;

        transient ObservingKundali kundali;
        transient int calls;

        @Override
        public ICharaKarakaOption charaKarakaOption() {
            calls++;
            if (null != kundali) kundali.observe();
            return SEVEN_KARAKAS;
        }
    }

    private ISweObjects lucknow1947() {
        return new SweObjects(getSwephExp(),
                new SweJulianDate(new int[]{1947, 8, 15, 10, 30}, 0f, 10.5),
                GEO_LUCKNOW, TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild();
    }

    @Test
    void theFieldIsNotReachableUntilTheKarakasAreAssigned() {
        final ObservingOptions options = new ObservingOptions();
        final ObservingKundali kundali = new ObservingKundali(options, lucknow1947());
        options.kundali = kundali;

        final IGrahas grahas = kundali.grahas();

        assertTrue(options.calls > 0, "the spy must actually have been consulted");
        assertTrue(kundali.observedAtLeastOnce, "the observation point must have been reached");
        assertFalse(kundali.fieldWasReachableDuringBuild,
                "grahas must stay null until the Chara karakas are assigned - publishing it "
                        + "earlier is what let a concurrent caller see karaka-less grahas");
        assertNotNull(grahas, "and it must of course be published in the end");
    }

    @Test
    void whatTheCallerGetsAlwaysHasItsKarakasAssigned() {
        final Kundali kundali = new Kundali(KundaliOptions.KUNDALI_7_KARAKAS, lucknow1947());
        final IGrahaEntity[] all = kundali.grahas().all();

        // the 7-karaka scheme ranks Surya..Shani and deliberately leaves Rahu unassigned
        for (int uid = SY; uid < RA; uid++) {
            assertNotNull(all[uid].charaKaraka(),
                    all[uid].entityEnum().code() + " must have a Chara karaka");
        }
    }

    @Test
    void aSecondCallReturnsTheSameFullyBuiltInstance() {
        final Kundali kundali = new Kundali(KundaliOptions.KUNDALI_7_KARAKAS, lucknow1947());

        final IGrahas first = kundali.grahas();
        final IGrahas second = kundali.grahas();

        assertSame(first, second, "the lazy field must still be cached");
        assertNotNull(second.all()[SY].charaKaraka(), "and still carry its karakas");
    }
}
