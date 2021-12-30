package org.jyotisa.api.graha;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.bhava.IBhava;
import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.naksatra.INaksatra;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVarga;
import org.swisseph.api.ISweSegment;

import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.app.SweSegment.ZERO_SEGMENT;

public interface IGraha extends IKundaliSequence<IGraha> {
    int[] DRISHTI_3710 = new int[]{3, 7, 10};
    int[] DRISHTI_478 = new int[]{4, 7, 8};
    int[] DRISHTI_579 = new int[]{5, 7, 9};
    int[] DRISHTI_7 = new int[]{7};
    int[] NO_DRISHTI = new int[0];

    /**
     * @return SWE Planet FID
     */
    int swefid();

    @Override
    default double length() {
        return 0;
    }

    default int[] drishti() {
        return DRISHTI_7;
    }

    default ISweSegment segment() {
        return ZERO_SEGMENT;
    }

    default double progressInRasi(final double longitude) {
        return IRasi.progress(longitude);
    }

    default double progressInBhava(final double longitude) {
        return IBhava.progress(longitude);
    }

    default double progressInNaksatra(final double longitude) {
        return INaksatra.progress(longitude);
    }

    default double progressInNaksatraPada(final double longitude) {
        return INaksatraPada.progress(longitude);
    }

    IDignity dignity(final IVarga varga, final double longitude);

    /**
     * Mrityu means death and bhaga means portion (MB).
     * In brief MB is the particular degree portion of planet in various sign indicating some mishaps
     * or tragedy or troubles like death or death itself. In Sanskrit Mrityu means death, but we cannot
     * go verbatim and here we may construed it as capital punishment or disease or tragic event factor.
     *
     * https://sudarshana.ru/vedic-astrology/mrityu-bhaga
     *
     * @param longitude of the Graha to check if it is in Mrityu Bhaga
     * @return 0 if not else from 1% to 100% of Mrityu Bhaga result
     */
    double inMrityuBhaga(final double longitude);

    /**
     * @param longitude of the Graha to check if it is in Mrityu Bhaga
     * @param degrees array of all Mrityu Bhaga degrees of the Graha
     * @return 0 if not else from 1% to 100% of Mrityu Bhaga result
     */
    static double inMrityuBhaga(final double longitude, final double[] degrees) {
        int rasiFid0 = IRasi.rasiFid0(longitude);
        final double diff = Math.abs((rasiFid0 * RASI_LENGTH + degrees[rasiFid0]) - longitude);
        if (diff <= d1) return d100 - (diff * d100);
        return d0;
    }

    /**
     * 0. Lagna
     */
    String LG_CD = "LG";

    /**
     * 1. Surya
     */
    String SY_CD = "SY";

    /**
     * 2. Chandra
     */
    String CH_CD = "CH";

    /**
     * 3. Guru
     */
    String GU_CD = "GU";

    /**
     * 4. Rahu
     */
    String RA_CD = "RA";

    /**
     * 5. Budha
     */
    String BU_CD = "BU";

    /**
     * 6. Shukra
     */
    String SK_CD = "SK";

    /**
     * 7. Ketu
     */
    String KE_CD = "KE";

    /**
     * 8. Shani
     */
    String SA_CD = "SA";

    /**
     * 9. Mangala
     */
    String MA_CD = "MA";

    /**
     * 10. Uranus
     */
    String UR_CD = "SW"; // Uranus or Sweta

    /**
     * 11. Neptune
     */
    String NE_CD = "SM"; // Neptune or Syama

    /**
     * 12. Pluto
     */
    String PL_CD = "TE"; // Pluto or Teekshana/Teevra
}
