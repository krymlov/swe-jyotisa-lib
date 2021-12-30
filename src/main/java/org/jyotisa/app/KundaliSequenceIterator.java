package org.jyotisa.app;

import org.jyotisa.api.IKundali;
import org.jyotisa.api.IKundaliSequenceEntity;
import org.jyotisa.api.IKundaliSequenceIterator;

public abstract class KundaliSequenceIterator<E extends IKundaliSequenceEntity<?>>
        extends KundaliIterator<E> implements IKundaliSequenceIterator<E> {

    protected KundaliSequenceIterator(final IKundali kundali, final boolean forward, final double offsetStep) {
        super(kundali, forward, offsetStep);
    }

    protected KundaliSequenceIterator(IKundali kundali, boolean forward) {
        super(kundali, forward);
    }
}
