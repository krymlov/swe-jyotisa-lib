/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhavaVyaya;

/**
 * 12. Vyaya - Loss (Moksha), House of the liberation
 * <p>
 * Liberation, loss, distant or a mysterious places, enlightenment, physical pleasures,
 * places which restrict the freedom (hospital, prison, ashram, monastery, nursing homes),
 * failure, poverty, indifference to the material world, renunciation of secular life,
 * generosity, self-sacrifice, spiritual paths, profession, life after death,
 * the previous incarnation, the end of life, situation after death, the next life,
 * problems, secret enemies, deportation, extravagance, vice, accidents, shame, scandals,
 * betrayal, emigration, the quest for spirituality.
 * <p>
 * Signifikators:
 * - Shani - losses
 * - Ketu - liberation
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum BhavaVyaya implements IBhavaVyaya {
    B12,
    VYY;

    @Override
    public IBhavaVyaya[] all() {
        return values();
    }
}
