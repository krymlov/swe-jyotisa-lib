/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */

package org.jyotisa.lagna;


import org.jyotisa.api.IKundaliFields;
import org.jyotisa.api.IKundaliOptions;
import org.jyotisa.api.lagna.ILagnaEntity;
import org.jyotisa.api.lagna.ILagnas;
import org.jyotisa.app.KundaliRuntimeException;
import org.swisseph.api.ISweObjects;

import static org.jyotisa.lagna.ELagna.*;
import static org.jyotisa.lagna.LagnaBhava.L1;
import static org.jyotisa.lagna.LagnaGhati.L3;
import static org.jyotisa.lagna.LagnaHora.L2;
import static org.jyotisa.lagna.LagnaJanma.L0;
import static org.swisseph.api.ISweObjects.LG;

/**
 * @author Yura Krymlov
 * @version 1.1, 2021-01
 */
public class Lagnas implements ILagnas {
    private static final long serialVersionUID = -1602056837470904368L;
    protected final ILagnaEntity[] all = new LagnaEntity[GHATI_LAGNA.uid() + 1]; // so far only 4 are supported

    public Lagnas(IKundaliOptions options, IKundaliFields kundaliFields, ISweObjects sweObjects) {
        final double julianDay = sweObjects.sweJulianDate().julianDay();
        this.all[JANMA_LAGNA.uid()] = new LagnaEntity(sweObjects.longitudes()[LG], L0, julianDay);
        this.all[BHAVA_LAGNA.uid()] = new LagnaEntity(kundaliFields.bhavaLagna(), L1, julianDay);
        this.all[HORA_LAGNA.uid()] = new LagnaEntity(kundaliFields.horaLagna(), L2, julianDay);
        this.all[GHATI_LAGNA.uid()] = new LagnaEntity(kundaliFields.ghatiLagna(), L3, julianDay);
    }

    @Override
    public ILagnaEntity janma() {
        return all[JANMA_LAGNA.uid()];
    }

    @Override
    public ILagnaEntity bhava() {
        return all[BHAVA_LAGNA.uid()];
    }

    @Override
    public ILagnaEntity hora() {
        return all[HORA_LAGNA.uid()];
    }

    @Override
    public ILagnaEntity ghati() {
        return all[GHATI_LAGNA.uid()];
    }

    //@Override
    public ILagnaEntity vighati() {
        throw new KundaliRuntimeException("Lagna is not implemented yet");
    }

    //@Override
    public ILagnaEntity varnada() {
        throw new KundaliRuntimeException("Lagna is not implemented yet");
    }

    //@Override
    public ILagnaEntity sree() {
        throw new KundaliRuntimeException("Lagna is not implemented yet");
    }

    //@Override
    public ILagnaEntity pranapada() {
        throw new KundaliRuntimeException("Lagna is not implemented yet");
    }

    //@Override
    public ILagnaEntity indu() {
        throw new KundaliRuntimeException("Lagna is not implemented yet");
    }

    @Override
    public ILagnaEntity[] all() {
        return all;
    }

}
