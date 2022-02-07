/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */
package org.jyotisa.app;

import org.jyotisa.api.IKundali;
import org.jyotisa.api.IKundaliFields;
import org.jyotisa.api.IKundaliOptions;
import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.graha.IGrahaEntity;
import org.jyotisa.api.graha.IGrahas;
import org.jyotisa.api.grahan.IGrahan;
import org.jyotisa.api.karaka.ICharaKaraka;
import org.jyotisa.api.karana.IKarana;
import org.jyotisa.api.lagna.ILagna;
import org.jyotisa.api.lagna.ILagnaEntity;
import org.jyotisa.api.lagna.ILagnaEnum;
import org.jyotisa.api.lagna.ILagnas;
import org.jyotisa.api.naksatra.INaksatra;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.nityayoga.INityaYoga;
import org.jyotisa.api.panchanga.IPanchanga;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.tithi.ITithi;
import org.jyotisa.api.upagraha.IUpagrahaEntity;
import org.jyotisa.api.upagraha.IUpagrahaEnum;
import org.jyotisa.api.upagraha.IUpagrahas;
import org.jyotisa.api.varga.IVarga;
import org.jyotisa.bhava.EBhava;
import org.jyotisa.bindu.BhriguBindu;
import org.jyotisa.graha.EGraha;
import org.jyotisa.graha.Grahas;
import org.jyotisa.grahan.ChandraGrahan;
import org.jyotisa.grahan.SuryaGrahan;
import org.jyotisa.karaka.ECharaKaraka;
import org.jyotisa.lagna.ELagna;
import org.jyotisa.lagna.Lagnas;
import org.jyotisa.panchanga.Panchanga;
import org.jyotisa.rasi.ERasi;
import org.jyotisa.upagraha.EUpagraha;
import org.jyotisa.upagraha.Upagrahas;
import org.jyotisa.varga.VargaD1;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.api.ISweGeoLocation;
import org.swisseph.api.ISweObjects;
import org.swisseph.api.ISweObjectsOptions;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;
import static org.jyotisa.graha.chaya.GrahaKetu.KETU;
import static org.jyotisa.graha.chaya.GrahaKetu.KETU_TRUE;
import static org.jyotisa.graha.chaya.GrahaRahu.RAHU;
import static org.jyotisa.graha.chaya.GrahaRahu.RAHU_TRUE;
import static org.jyotisa.karaka.ECharaKarakaOption.SEVEN_KARAKAS;
import static org.jyotisa.upagraha.EUpagraha.UPAKETU;
import static org.swisseph.api.ISweConstants.CH_VS;
import static org.swisseph.api.ISweConstants.d0;
import static org.swisseph.api.ISweEnum.NIL_CD;
import static org.swisseph.api.ISweObjects.*;
import static org.swisseph.utils.IDateUtils.format6;
import static org.swisseph.utils.IDegreeUtils.*;
import static org.swisseph.utils.IModuloUtils.fix30;
import static swisseph.SweConst.SEFLG_SWIEPH;
import static swisseph.SweDate.getDayOfWeekNr;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-12
 */
public class Kundali implements IKundali {
    private static final long serialVersionUID = 2041383721876032821L;

    protected final IKundaliOptions options;
    protected final ISweObjects sweObjects;

    protected IKundaliFields fields;
    protected IPanchanga panchanga;
    protected IUpagrahas upagrahas;
    protected IGrahas grahas;
    protected ILagnas lagnas;

    public Kundali(IKundaliOptions options, ISweObjects sweObjects) {
        this.sweObjects = sweObjects;
        this.options = options;
    }

    @Override
    public IKundaliOptions getOptions() {
        return options;
    }

    @Override
    public ISweObjects sweObjects() {
        return sweObjects;
    }

    @Override
    public IKundaliFields fields() {
        if (null != fields) return fields;
        return fields = new KundaliFields(options, sweObjects);
    }
    // ---------------------------------------------

    @Override
    public ILagnas lagnas() {
        if (null != lagnas) return lagnas;
        return lagnas = new Lagnas(options, fields(), sweObjects);
    }

    @Override
    public IGrahas grahas() {
        if (null != grahas) return grahas;
        grahas = new Grahas(options, sweObjects);

        final IGrahaEntity[] all = grahas.all();
        final boolean sevenKarakas = SEVEN_KARAKAS.equals(options.charaKarakaOption());
        final List<IGrahaEntity> entities = new ArrayList<>(options.charaKarakaOption().fid());

        for (int i = SY; i < RA; i++) entities.add(all[i]);
        if ( !sevenKarakas ) entities.add(all[RA]);

        // sort grahas according to Chara karakas
        sort(entities, options.charaKarakaOption());

        for (int index = 0, karaka = 1; index < entities.size(); index++, karaka++) {
            if ( sevenKarakas && karaka == ECharaKaraka.PITRI_KARAKA.uid() ) karaka++;
            ICharaKaraka charaKaraka = ECharaKaraka.byUid(karaka);
            entities.get(index).charaKaraka(charaKaraka);
        }

        return grahas;
    }

    @Override
    public IUpagrahas upagrahas() {
        if (null != upagrahas) return upagrahas;
        return upagrahas = new Upagrahas(options, sweObjects);
    }

    @Override
    public IPanchanga panchanga() {
        if (null != panchanga) return panchanga;
        return panchanga = new Panchanga(sweObjects.longitudes()[SY], getDayOfWeekNr(
                sweObjects.sweJulianDate().julianDay()), sweObjects.longitudes()[CH]);
    }

    @Override
    public IGrahan suryaGrahan(boolean backward, boolean locally) {
        final SuryaGrahan grahan = new SuryaGrahan(sweObjects.sweLocation());
        final StringBuilder serr = new StringBuilder(0);

        if (locally) {
            swissEph().swe_sol_eclipse_when_loc(fields().sunrise(), SEFLG_SWIEPH,
                    grahan.getCoordinates(), grahan.getOccasions(), grahan.getAttributes(),
                    backward ? 1 : 0, serr);
        } else {
            //final double julday = ((long) getJulianDate().getJulDay()) - d05;
            swissEph().swe_sol_eclipse_when_glob(fields().sunrise(), SEFLG_SWIEPH,
                    0, grahan.getOccasions(), backward ? 1 : 0, serr);
        }

        if (serr.length() > 0) {
            throw new KundaliRuntimeException(serr.toString());
        }

        return grahan;
    }

    @Override
    public IGrahan chandraGrahan(boolean backward, boolean locally) {
        final ChandraGrahan grahan = new ChandraGrahan(sweObjects.sweLocation());
        final StringBuilder serr = new StringBuilder(0);

        if (locally) {
            swissEph().swe_lun_eclipse_when_loc(fields().moonrise(), SEFLG_SWIEPH,
                    grahan.getCoordinates(), grahan.getOccasions(), grahan.getAttributes(),
                    backward ? 1 : 0, serr);
        } else {
            //final double julday = ((long) getJulianDate().getJulDay()) - d05;
            swissEph().swe_lun_eclipse_when(fields().moonrise(), SEFLG_SWIEPH,
                    0, grahan.getOccasions(), backward ? 1 : 0, serr);
        }

        if (serr.length() > 0) {
            throw new KundaliRuntimeException(serr.toString());
        }

        return grahan;
    }

    @Override
    public IDignity dignity(IVarga varga, IGraha graha) {
        return graha.dignity(varga, sweObjects.longitudes()[graha.uid()]);
    }

    @Override
    public IDignity dignity(IGraha graha) {
        return graha.dignity(VargaD1.D1, sweObjects.longitudes()[graha.uid()]);
    }

    @Override
    public IGraha chayaGraha(boolean rahu) {
        final boolean trueNode = sweOptions().trueNode();
        if ( rahu ) return trueNode ? RAHU_TRUE : RAHU;
        else return trueNode ? KETU_TRUE : KETU;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(5120);
        final ISweObjectsOptions opt = sweObjects.sweOptions();
        final ISweGeoLocation geo = sweObjects.sweLocation();
        final IPanchanga pnchng = panchanga();
        final IUpagrahas upgrhs = upagrahas();
        final ILagnas lagnas = lagnas();

        builder.append("Ayanamsa: ").append(sweObjects
                .sweOptions().ayanamsa().name()).append(CH_VS)
                .append(toDMSms(sweObjects.ayanamsa()))
                .append(", Event Date: ").append(format6(sweJulianDate()))
                .append(" UTC: ").append(toDMS(sweJulianDate().timeZone(), true))
                .append(", Location: ").append(toLON(geo.longitude()))
                .append(" / ").append(toLAT(geo.latitude()))
                .append("\nHousesys: ").append(opt.houseSystem())
                .append(", Naksatra: ").append(pnchng.pada().naksatra().following()).append('(')
                .append(INaksatra.progress(pnchng)).append("%)")
                .append(", Tithi: ").append(pnchng.tithi().code()).append('(')
                .append(ITithi.progress(pnchng)).append("%)")
                .append(", Vaara: ").append(pnchng.vaara().code())
                .append(", Nitya Yoga: ").append(pnchng.yoga().following()).append('(')
                .append(INityaYoga.progress(pnchng)).append("%)")
                .append(", Karana: ").append(pnchng.karana().following()).append('(')
                .append(IKarana.progress(pnchng)).append("%)")
                .append("\nBhrigu Bindu: ").append(new BhriguBindu(this))
                .append('\n');

        IGrahaEntity[] grhs = grahas().all();
        int[] grahaInRasi = sweObjects.signs();
        int[] grahaInBhava = sweObjects.houses();
        double[] grahaDegree = sweObjects.longitudes();
        boolean[] grahaVakri = sweObjects.retrogrades();

        for (int i = 0; i < grahaInRasi.length; i++) {
            EGraha grahas = EGraha.values()[i];
            IGraha graha = grahas.graha();

            boolean vakri = grahaVakri[i];
            double degree = grahaDegree[i];
            String strGraha = grahas.code();
            if (vakri) strGraha = "(" + strGraha + ")";

            INaksatraPada pada = grhs[i].pada();
            IDignity dignity = grhs[i].dignity();
            IRasi rasi = ERasi.byUid(grahaInRasi[i]);
            ICharaKaraka karaka = grhs[i].charaKaraka();
            double mrityuBhaga = graha.inMrityuBhaga(degree);

            builder.append(String.format("%-5s= %-13s -> Rasi= %-3s (%-5s%%) | %s -> Naksatra= %3s|%2s (%-5s%%) " +
                "-> Navamsa= %3s|%2s -> Bhava= %-3s -> Dignity= %-3s -> %3s -> %-2s\n",
                    strGraha, toDMSms(degree), rasi == null ? "?" : rasi.following(),
                    graha.progressInRasi(degree), toDMSms(fix30(degree)), pada, pada.naksatra().lord().code(),
                    graha.progressInNaksatra(degree), pada.navamsa().following(), pada.navamsa().lord().code(),
                    EBhava.byUid(grahaInBhava[i]),
                    (null != dignity ? grhs[i].dignity() : NIL_CD),
                    (null == karaka ? "   " : karaka.code()),
                    (d0 == mrityuBhaga ? "  " : "!" + Math.round(mrityuBhaga) + "%")
                )
            );
        }

        ISweEnumIterator<IUpagrahaEnum> upagrahaIterator = EUpagraha.iteratorTo(UPAKETU);
        while (upagrahaIterator.hasNext()) {
            final IUpagrahaEnum upgen = upagrahaIterator.next();
            IUpagrahaEntity upagraha = upgrhs.all()[upgen.uid()];
            String strGraha = upagraha.entityEnum().name();
            INaksatraPada pada = upagraha.pada();
            double degree = upagraha.longitude();

            builder.append(String.format("%-5s= %-13s -> Rasi= %-3s (%-5s%%) | %s "
                            + "-> Naksatra= %3s|%2s (%-5s%%) -> Navamsa= %3s|%2s -> Bhava= %-3s\n",
                    strGraha, toDMSms(degree), pada.rasi().following(), IRasi.progress(degree),
                    toDMSms(fix30(degree)), pada, pada.naksatra().lord().code(),
                    INaksatra.progress(degree), pada.navamsa().following(),
                    pada.navamsa().lord().code(), upagraha.bhava()));
        }

        ISweEnumIterator<ILagnaEnum> lagnaIterator = ELagna.iteratorTo(ELagna.GHATI_LAGNA);
        while (lagnaIterator.hasNext()) {
            ILagnaEnum lgenum = lagnaIterator.next();
            ILagnaEntity lagna = lagnas.all()[lgenum.uid()];
            ILagna lg = lagna.entityEnum().following();
            INaksatraPada pada = lagna.pada();
            double degree = lagna.longitude();

            builder.append(String.format("%-5s= %-13s -> Rasi= %-3s (%-5s%%) | %s "
                            + "-> Naksatra= %3s|%2s (%-5s%%) -> Navamsa= %3s|%2s -> Bhava= %-3s\n",
                    lg, toDMSms(degree), pada.rasi().following(), IRasi.progress(degree),
                    toDMSms(fix30(degree)), pada, pada.naksatra().lord().code(),
                    INaksatra.progress(degree), pada.navamsa().following(),
                    pada.navamsa().lord().code(), EBhava.TANU.code()));
        }

        builder.append('\n').append(fields());
        return builder.toString();
    }

}
