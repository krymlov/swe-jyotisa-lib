package org.jyotisa.graha.shani;


import org.apache.commons.lang3.NotImplementedException;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGrahaEnumEntity;
import org.jyotisa.app.KundaliIterator;
import org.jyotisa.gochara.GrahaEntity;
import org.swisseph.api.ISweGeoLocation;
import swisseph.SwissOut;
import swisseph.TransitCalculator;
import swisseph.Transits;

import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Double.parseDouble;
import static org.jyotisa.graha.shani.GrahaShani.SHANI;

abstract class SaChGochara extends KundaliIterator<IGrahaEnumEntity> {
    protected final int limit;

    protected double startDegree = -1.;
    protected Iterator<IGrahaEnumEntity> iterator;

    /**
     * @param limit number of complete transits to include into the output
     */
    protected SaChGochara(final IKundali kundali, final int limit) {
        super(kundali, true);
        this.limit = limit;
    }

    protected abstract void addGocharaLongitudes(final StringBuilder gocharaArgsBuilder);

    @Override
    protected void calcFirstTransit() {
        final ISweGeoLocation geolocation = kundali.sweLocation();
        final StringBuilder builder = new StringBuilder(255).append("-p6 -topo");

        builder.append(geolocation.latitude()).append(';');
        builder.append(geolocation.longitude()).append(";");
        builder.append(geolocation.altitude());

        addGocharaLongitudes(builder);
        addGocharaStartDate(builder);
        addGocharaEndDate(builder);

        builder.append(" -sid").append(kundali.sweOptions().ayanamsa().fid());
        builder.append(" -true -Dloc24en -fj10v -ut -eswe -edir");
        builder.append(kundali.swissEph().swe_get_ephe_path());

        Transits transits = new Transits(swissEph);
        SwissOut swissOut = transits.getSwissOut();
        swissOut.setSkipFirstPrintln(6);
        swissOut.setSkipAllPrint(true);

        transits.startCalculations(builder.toString().split(" "));
        this.iterator = parseOutput(transits).iterator();
    }

    protected void addGocharaStartDate(final StringBuilder gocharaArgsBuilder) {
        gocharaArgsBuilder.append(" -b1/1/").append(kundali.sweJulianDate().date()[0] - 1);
    }

    protected void addGocharaEndDate(final StringBuilder gocharaArgsBuilder) {
        gocharaArgsBuilder.append(" -b1/1/").append(kundali.sweJulianDate().date()[0] + 119);
    }

    @Override
    protected TransitCalculator createTransitCalc(double startOffset) {
        throw new NotImplementedException("TransitCalculator is not implemented");
    }

    @Override
    public double getStartOffset() {
        return startDegree;
    }

    @Override
    protected IGrahaEnumEntity newTransitEntity() {
        return null;
    }

    @Override
    public boolean hasNext() {
        if (null == iterator) calcFirstTransit();
        return iterator.hasNext();
    }

    @Override
    public IGrahaEnumEntity next() {
        return iterator.next();
    }

    protected ArrayList<IGrahaEnumEntity> parseOutput(final Transits transitCalc) {
        final ArrayList<String> output = transitCalc.getSwissOut().getOutput();
        final ArrayList<IGrahaEnumEntity> gocharas = new ArrayList<>();

        IGrahaEnumEntity gochara = null;
        String prevDegree = null;
        int nTimesGochara = 0;

        for (String period : output) {
            final String[] strings = period.split("   ", 2);
            final String degree = strings[1].substring(0, strings[1].length() - 1);
            final double longitude = parseDouble(degree);
            if (null == prevDegree) prevDegree = degree;

            if (startDegree != longitude) {
                addGochara(gocharas, gochara);
                gocharas.add(gochara = newGrahaEntity(longitude, strings[0]));
            } else {
                if (degree.equals(prevDegree)) {
                    gochara = newGrahaEntity(longitude, strings[0]);
                    continue;
                }

                ++nTimesGochara;
                if (nTimesGochara > limit) break;

                addGochara(gocharas, gochara);
                gochara = newGrahaEntity(longitude, strings[0]);
            }

            prevDegree = degree;
        }

        if (limit > nTimesGochara) {
            addGochara(gocharas, gochara);
        }

        return gocharas;
    }

    protected boolean addGochara(final ArrayList<IGrahaEnumEntity> periods, final IGrahaEnumEntity gochara) {
        if (null == gochara) return false;

        final int size = periods.size();
        if (size == 0 || periods.get(periods.size() - 1) != gochara) {
            return periods.add(gochara);
        }

        return false;
    }

    protected IGrahaEnumEntity newGrahaEntity(double longitude, String julianDay) {
        return new GrahaEntity(longitude, SHANI, parseDouble(julianDay));
    }
}
