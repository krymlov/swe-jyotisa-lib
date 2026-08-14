/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.maasa;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The lunar-month (Maasa) family is entirely unimplemented in this library: every non-NIL
 * constant's {@code maasa()} throws {@link NotImplementedException}, and there are no
 * concrete {@code Maasa*} classes anywhere (unlike every other family, which has one). This
 * pins that fact so porting one maasa updates this test rather than silently changing
 * behavior. See this project's CLAUDE.md for the full list of unimplemented families.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class EMaasaTest {

    @Test
    void nil_maasaIsNull() {
        assertNull(EMaasa.NIL.maasa());
    }

    @Test
    void everyOtherConstant_maasaThrowsNotImplemented() {
        for (EMaasa m : EMaasa.values()) {
            if (m == EMaasa.NIL) continue;
            assertThrows(NotImplementedException.class, m::maasa, m.name());
        }
    }

    @Test
    void thirteenConstantsExist_twelveRegularPlusOneAdhikaPlaceholder() {
        assertEquals(14, EMaasa.values().length, "13 real constants + NIL");
        assertEquals(EMaasa.MADHUSUDANA, EMaasa.NIL.first());
        assertEquals(EMaasa.VISNU, EMaasa.NIL.last(), "PURADH (the Adhika Maasa placeholder) sits outside first()/last()");
    }
}
