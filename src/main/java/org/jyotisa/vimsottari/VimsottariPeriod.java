/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.vimsottari;

import org.jyotisa.api.vimsottari.IVimsottariDasa;
import org.jyotisa.api.vimsottari.IVimsottariPeriod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public class VimsottariPeriod implements IVimsottariPeriod {
    private static final long serialVersionUID = 4470062814470926885L;

    protected final IVimsottariDasa dasa;
    protected final IVimsottariPeriod parent;
    protected final double start;
    protected final double close;
    protected final int level;

    protected List<IVimsottariPeriod> periods = Collections.emptyList();

    public VimsottariPeriod(IVimsottariDasa dasa, IVimsottariPeriod parent,
                            double start, double close, int level) {
        this.dasa = dasa;
        this.parent = parent;
        this.start = start;
        this.close = close;
        this.level = level;
    }

    @Override
    public IVimsottariDasa dasa() {
        return dasa;
    }

    @Override
    public int level() {
        return level;
    }

    @Override
    public double start() {
        return start;
    }

    @Override
    public double close() {
        return close;
    }

    @Override
    public List<IVimsottariPeriod> periods() {
        return periods;
    }

    @Override
    public IVimsottariPeriod parent() {
        return parent;
    }

    protected List<IVimsottariPeriod> mutablePeriods(int size) {
        if (periods.isEmpty()) periods = new ArrayList<>(size);
        return periods;
    }

    @Override
    public String toString() {
        return dasa.code() + '[' + level + ']';
    }
}
