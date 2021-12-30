/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.maasa;

import org.jyotisa.api.IKundaliSequence;

/**
 * A solar year has about 365.2425 days, but a lunar year only has about 355 days.
 * Once in every 3 years, this difference accumulates to one month and an extra lunar
 * month comes. This results in Sun-Moon conjunction coming twice in the same rasi.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-12
 */
public interface IMaasa extends IKundaliSequence<IMaasa> {

    @Override
    default double length() {
        return 0;
    }

    // Maasa codes

    /**
     * Vaisakha (Madhusudana)
     * Aries Vaisaakha Visaakha Apr/May
     */
    String APRMAY_CD = "MA1";

    /**
     * Jyestha (Trivikrama)
     * Taurus Jyeshtha Jyeshtha May/June
     */
    String MAYJUN_CD = "MA2";

    /**
     * Asadha (Vamana)
     * Gemini Aashaadha Poorva/Uttara Aashaadha June/July
     */
    String JUNJUL_CD = "MA3";

    /**
     * Sravana (Sridhara)
     * Cancer Sraavana Sravana July/Aug
     */
    String JULAUG_CD = "MA4";

    /**
     * Bhadra (Hrsikesa)
     * Leo Bhaadrapada Poorva/Uttara Bhadrapada Aug/Sept
     */
    String AUGSEP_CD = "MA5";

    /**
     * Asvina (Padmanabha)
     * Virgo Aaswayuja Aswini Sept/Oct
     */
    String SEPOCT_CD = "MA6";

    /**
     * Kartika (Damodara)
     * Libra Kaarteeka Krittika Oct/Nov
     */
    String OCTNOV_CD = "MA7";

    /**
     * Margasirsa (Kesava)
     * Scorpio Maargasira Mrigasira Nov/Dec
     */
    String NOVDEC_CD = "MA8";

    /**
     * Pausa (Narayana)
     * Sagittarius Pushya Pushyami Dec/Jan
     */
    String DECJAN_CD = "MA9";

    /**
     * Magha (Madhava)
     * Capricorn Maagha Makha Jan/Feb
     */
    String JANFEB_CD = "MA10";

    /**
     * Phalguna (Govinda)
     * Aquarius Phaalguna Poorva/Uttara Phalguni Feb/Mar
     */
    String FEBMAR_CD = "MA11";

    /**
     * Caitra (Visnu)
     * Pisces Chaitra Chitra Mar/Apr
     */
    String MARAPR_CD = "MA12";

    /**
     * Purusottama-adhika
     */
    String PURADH_CD = "MA13";
}
