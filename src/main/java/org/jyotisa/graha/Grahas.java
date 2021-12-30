/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */

package org.jyotisa.graha;


import org.jyotisa.api.IKundaliOptions;
import org.jyotisa.api.graha.IGrahaEntity;
import org.jyotisa.api.graha.IGrahas;
import org.swisseph.api.ISweObjects;

import static org.jyotisa.graha.EGraha.*;
import static org.jyotisa.graha.budha.GrahaBudha.BU;
import static org.jyotisa.graha.chandra.GrahaChandra.CH;
import static org.jyotisa.graha.chaya.GrahaKetu.KE;
import static org.jyotisa.graha.chaya.GrahaKetu.KETU_TRUE;
import static org.jyotisa.graha.chaya.GrahaRahu.RA;
import static org.jyotisa.graha.chaya.GrahaRahu.RAHU_TRUE;
import static org.jyotisa.graha.guru.GrahaGuru.GU;
import static org.jyotisa.graha.lagna.GrahaLagna.LG;
import static org.jyotisa.graha.mangala.GrahaMangala.MA;
import static org.jyotisa.graha.shani.GrahaShani.SA;
import static org.jyotisa.graha.shukra.GrahaShukra.SK;
import static org.jyotisa.graha.surya.GrahaSurya.SY;
import static org.jyotisa.graha.sweta.GrahaSweta.SW;
import static org.jyotisa.graha.syama.GrahaSyama.SM;
import static org.jyotisa.graha.teevra.GrahaTeevra.TE;

/**
 * @author Yura Krymlov
 * @version 1.?, 2019-12
 */
public class Grahas implements IGrahas {
    private static final long serialVersionUID = 527724480322860586L;
    protected final IGrahaEntity[] all = new GrahaEntity[EGraha.values().length];

    public Grahas(IKundaliOptions options, ISweObjects objects) {
        all[LAGNA.uid()] = new GrahaEntity(LG, objects);
        all[SURYA.uid()] = new GrahaEntity(SY, objects);
        all[CHANDRA.uid()] = new GrahaEntity(CH, objects);
        all[MANGALA.uid()] = new GrahaEntity(MA, objects);
        all[BUDHA.uid()] = new GrahaEntity(BU, objects);
        all[GURU.uid()] = new GrahaEntity(GU, objects);
        all[SHUKRA.uid()] = new GrahaEntity(SK, objects);
        all[SHANI.uid()] = new GrahaEntity(SA, objects);
        all[SWETA.uid()] = new GrahaEntity(SW, objects);
        all[SYAMA.uid()] = new GrahaEntity(SM, objects);
        all[TEEVRA.uid()] = new GrahaEntity(TE, objects);

        final boolean trueNode = objects.sweOptions().trueNode();
        all[RAHU.uid()] = new GrahaEntity(trueNode ? RAHU_TRUE : RA, objects);
        all[KETU.uid()] = new GrahaEntity(trueNode ? KETU_TRUE : KE, objects);
    }

    @Override
    public IGrahaEntity lagna() {
        return all[LAGNA.uid()];
    }

    @Override
    public IGrahaEntity surya() {
        return all[SURYA.uid()];
    }

    @Override
    public IGrahaEntity chandra() {
        return all[CHANDRA.uid()];
    }

    @Override
    public IGrahaEntity mangala() {
        return all[MANGALA.uid()];
    }

    @Override
    public IGrahaEntity budha() {
        return all[BUDHA.uid()];
    }

    @Override
    public IGrahaEntity guru() {
        return all[GURU.uid()];
    }

    @Override
    public IGrahaEntity shukra() {
        return all[SHUKRA.uid()];
    }

    @Override
    public IGrahaEntity shani() {
        return all[SHANI.uid()];
    }

    @Override
    public IGrahaEntity rahu() {
        return all[RAHU.uid()];
    }

    @Override
    public IGrahaEntity ketu() {
        return all[KETU.uid()];
    }

    @Override
    public IGrahaEntity sweta() {
        return all[SWETA.uid()];
    }

    @Override
    public IGrahaEntity syama() {
        return all[SYAMA.uid()];
    }

    @Override
    public IGrahaEntity teevra() {
        return all[TEEVRA.uid()];
    }

    @Override
    public IGrahaEntity[] all() {
        return all;
    }
}
