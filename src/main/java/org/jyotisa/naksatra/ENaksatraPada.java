/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.naksatra;

import org.jyotisa.api.naksatra.INaksatra;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.rasi.ERasi;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

import static org.swisseph.api.ISweConstants.NAKSHATRA_PADA_LENGTH;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * <pre>
 * There are 27 Nakshatras. Every Nakshatra measures 13.20' (13 degrees and 20 minutes).
 * Each Nakshatra is divided into 4 parts of 3.20’ (3 degrees and 20 minutes). These parts are called padas.
 * 
 * Each pada has the characteristics of a sign of the zodiac, starting with Aries.
 * 
 * In each Nakshatra there are 4 padas. Therefore, in 3 Nakshatras we have 12 padas. Because each pada
 * represents a sign of the zodiac in a group of 3 Nakshatras we find the entire zodiac in the form of
 * pada-signs.
 * 
 * The navamsa is the 1/9 division of the zodiac. Therefore, there is connection between the padas and the
 * navamsa. The connection is simple. If a planet is in the pada of Aries then it is in the navamsa in Aries.
 * If it is in the pada of Taurus it is in the navamsa in Taurus and so on.
 * 
 * The navamsa is the chart of the soul. It gives information about the state of the soul and thus of the inner
 * life of the person.
 * 
 * Therefore, it can be said that the signs of the padas give information about the soul of a person.
 * 
 * If the rasi contains information about in which padas planets are located, there is no need for a separate
 * printout of the navamsa. Navamsa-signs and pada-signs are the same.
 * <pre>
 * 
 * @author Yura Krymlov
 * @version 1.1, 2019-11
 */
public enum ENaksatraPada implements INaksatraPada {
    NIL {
        @Override public int fid() { return 0; }
        @Override public String code() { return NIL_CD; }
        @Override public INaksatra naksatra() {return null; }
    }, // 0  Reserved
    ASH1,    //   1 -> [00°00'00" - 03°20'00") == [0.0 - 3.3333333333333335)
    ASH2,    //   2 -> [03°20'00" - 06°40'00") == [3.3333333333333335 - 6.666666666666667)
    ASH3,    //   3 -> [06°40'00" - 10°00'00") == [6.666666666666667 - 10.0)
    ASH4,    //   4 -> [10°00'00" - 13°20'00") == [10.0 - 13.333333333333334)
    BHA1,    //   5 -> [13°20'00" - 16°40'00") == [13.333333333333334 - 16.666666666666668)
    BHA2,    //   6 -> [16°40'00" - 20°00'00") == [16.666666666666668 - 20.0)
    BHA3,    //   7 -> [20°00'00" - 23°20'00") == [20.0 - 23.333333333333336)
    BHA4,    //   8 -> [23°20'00" - 26°40'00") == [23.333333333333336 - 26.666666666666668)
    KRI1,    //   9 -> [26°40'00" - 30°00'00") == [26.666666666666668 - 30.0)
    KRI2,    //  10 -> [30°00'00" - 33°20'00") == [30.0 - 33.333333333333336)
    KRI3,    //  11 -> [33°20'00" - 36°40'00") == [33.333333333333336 - 36.66666666666667)
    KRI4,    //  12 -> [36°40'00" - 40°00'00") == [36.66666666666667 - 40.0)
    ROH1,    //  13 -> [40°00'00" - 43°20'00") == [40.0 - 43.333333333333336)
    ROH2,    //  14 -> [43°20'00" - 46°40'00") == [43.333333333333336 - 46.66666666666667)
    ROH3,    //  15 -> [46°40'00" - 50°00'00") == [46.66666666666667 - 50.0)
    ROH4,    //  16 -> [50°00'00" - 53°20'00") == [50.0 - 53.333333333333336)
    MRG1,    //  17 -> [53°20'00" - 56°40'00") == [53.333333333333336 - 56.66666666666667)
    MRG2,    //  18 -> [56°40'00" - 60°00'00") == [56.66666666666667 - 60.0)
    MRG3,    //  19 -> [60°00'00" - 63°20'00") == [60.0 - 63.333333333333336)
    MRG4,    //  20 -> [63°20'00" - 66°40'00") == [63.333333333333336 - 66.66666666666667)
    ARD1,    //  21 -> [66°40'00" - 70°00'00") == [66.66666666666667 - 70.0)
    ARD2,    //  22 -> [70°00'00" - 73°20'00") == [70.0 - 73.33333333333334)
    ARD3,    //  23 -> [73°20'00" - 76°40'00") == [73.33333333333334 - 76.66666666666667)
    ARD4,    //  24 -> [76°40'00" - 80°00'00") == [76.66666666666667 - 80.0)
    PUN1,    //  25 -> [80°00'00" - 83°20'00") == [80.0 - 83.33333333333334)
    PUN2,    //  26 -> [83°20'00" - 86°40'00") == [83.33333333333334 - 86.66666666666667)
    PUN3,    //  27 -> [86°40'00" - 90°00'00") == [86.66666666666667 - 90.0)
    PUN4,    //  28 -> [90°00'00" - 93°20'00") == [90.0 - 93.33333333333334)
    PUS1,    //  29 -> [93°20'00" - 96°40'00") == [93.33333333333334 - 96.66666666666667)
    PUS2,    //  30 -> [96°40'00" - 100°00'00") == [96.66666666666667 - 100.0)
    PUS3,    //  31 -> [100°00'00" - 103°20'00") == [100.0 - 103.33333333333334)
    PUS4,    //  32 -> [103°20'00" - 106°40'00") == [103.33333333333334 - 106.66666666666667)
    ASL1,    //  33 -> [106°40'00" - 110°00'00") == [106.66666666666667 - 110.0)
    ASL2,    //  34 -> [110°00'00" - 113°20'00") == [110.0 - 113.33333333333334)
    ASL3,    //  35 -> [113°20'00" - 116°40'00") == [113.33333333333334 - 116.66666666666667)
    ASL4,    //  36 -> [116°40'00" - 120°00'00") == [116.66666666666667 - 120.0)
    MAG1,    //  37 -> [120°00'00" - 123°20'00") == [120.0 - 123.33333333333334)
    MAG2,    //  38 -> [123°20'00" - 126°40'00") == [123.33333333333334 - 126.66666666666667)
    MAG3,    //  39 -> [126°40'00" - 130°00'00") == [126.66666666666667 - 130.0)
    MAG4,    //  40 -> [130°00'00" - 133°20'00") == [130.0 - 133.33333333333334)
    PPH1,    //  41 -> [133°20'00" - 136°40'00") == [133.33333333333334 - 136.66666666666669)
    PPH2,    //  42 -> [136°40'00" - 140°00'00") == [136.66666666666669 - 140.0)
    PPH3,    //  43 -> [140°00'00" - 143°20'00") == [140.0 - 143.33333333333334)
    PPH4,    //  44 -> [143°20'00" - 146°40'00") == [143.33333333333334 - 146.66666666666669)
    UPH1,    //  45 -> [146°40'00" - 150°00'00") == [146.66666666666669 - 150.0)
    UPH2,    //  46 -> [150°00'00" - 153°20'00") == [150.0 - 153.33333333333334)
    UPH3,    //  47 -> [153°20'00" - 156°40'00") == [153.33333333333334 - 156.66666666666669)
    UPH4,    //  48 -> [156°40'00" - 160°00'00") == [156.66666666666669 - 160.0)
    HAS1,    //  49 -> [160°00'00" - 163°20'00") == [160.0 - 163.33333333333334)
    HAS2,    //  50 -> [163°20'00" - 166°40'00") == [163.33333333333334 - 166.66666666666669)
    HAS3,    //  51 -> [166°40'00" - 170°00'00") == [166.66666666666669 - 170.0)
    HAS4,    //  52 -> [170°00'00" - 173°20'00") == [170.0 - 173.33333333333334)
    CHT1,    //  53 -> [173°20'00" - 176°40'00") == [173.33333333333334 - 176.66666666666669)
    CHT2,    //  54 -> [176°40'00" - 180°00'00") == [176.66666666666669 - 180.0)
    CHT3,    //  55 -> [180°00'00" - 183°20'00") == [180.0 - 183.33333333333334)
    CHT4,    //  56 -> [183°20'00" - 186°40'00") == [183.33333333333334 - 186.66666666666669)
    SWA1,    //  57 -> [186°40'00" - 190°00'00") == [186.66666666666669 - 190.0)
    SWA2,    //  58 -> [190°00'00" - 193°20'00") == [190.0 - 193.33333333333334)
    SWA3,    //  59 -> [193°20'00" - 196°40'00") == [193.33333333333334 - 196.66666666666669)
    SWA4,    //  60 -> [196°40'00" - 200°00'00") == [196.66666666666669 - 200.0)
    VIS1,    //  61 -> [200°00'00" - 203°20'00") == [200.0 - 203.33333333333334)
    VIS2,    //  62 -> [203°20'00" - 206°40'00") == [203.33333333333334 - 206.66666666666669)
    VIS3,    //  63 -> [206°40'00" - 210°00'00") == [206.66666666666669 - 210.0)
    VIS4,    //  64 -> [210°00'00" - 213°20'00") == [210.0 - 213.33333333333334)
    ANU1,    //  65 -> [213°20'00" - 216°40'00") == [213.33333333333334 - 216.66666666666669)
    ANU2,    //  66 -> [216°40'00" - 220°00'00") == [216.66666666666669 - 220.0)
    ANU3,    //  67 -> [220°00'00" - 223°20'00") == [220.0 - 223.33333333333334)
    ANU4,    //  68 -> [223°20'00" - 226°40'00") == [223.33333333333334 - 226.66666666666669)
    JYE1,    //  69 -> [226°40'00" - 230°00'00") == [226.66666666666669 - 230.0)
    JYE2,    //  70 -> [230°00'00" - 233°20'00") == [230.0 - 233.33333333333334)
    JYE3,    //  71 -> [233°20'00" - 236°40'00") == [233.33333333333334 - 236.66666666666669)
    JYE4,    //  72 -> [236°40'00" - 240°00'00") == [236.66666666666669 - 240.0)
    MUL1,    //  73 -> [240°00'00" - 243°20'00") == [240.0 - 243.33333333333334)
    MUL2,    //  74 -> [243°20'00" - 246°40'00") == [243.33333333333334 - 246.66666666666669)
    MUL3,    //  75 -> [246°40'00" - 250°00'00") == [246.66666666666669 - 250.0)
    MUL4,    //  76 -> [250°00'00" - 253°20'00") == [250.0 - 253.33333333333334)
    PSH1,    //  77 -> [253°20'00" - 256°40'00") == [253.33333333333334 - 256.6666666666667)
    PSH2,    //  78 -> [256°40'00" - 260°00'00") == [256.6666666666667 - 260.0)
    PSH3,    //  79 -> [260°00'00" - 263°20'00") == [260.0 - 263.33333333333337)
    PSH4,    //  80 -> [263°20'00" - 266°40'00") == [263.33333333333337 - 266.6666666666667)
    USH1,    //  81 -> [266°40'00" - 270°00'00") == [266.6666666666667 - 270.0)
    USH2,    //  82 -> [270°00'00" - 273°20'00") == [270.0 - 273.33333333333337)
    USH3,    //  83 -> [273°20'00" - 276°40'00") == [273.33333333333337 - 276.6666666666667)
    USH4,    //  84 -> [276°40'00" - 280°00'00") == [276.6666666666667 - 280.0)
    SHR1,    //  85 -> [280°00'00" - 283°20'00") == [280.0 - 283.33333333333337)
    SHR2,    //  86 -> [283°20'00" - 286°40'00") == [283.33333333333337 - 286.6666666666667)
    SHR3,    //  87 -> [286°40'00" - 290°00'00") == [286.6666666666667 - 290.0)
    SHR4,    //  88 -> [290°00'00" - 293°20'00") == [290.0 - 293.33333333333337)
    DHA1,    //  89 -> [293°20'00" - 296°40'00") == [293.33333333333337 - 296.6666666666667)
    DHA2,    //  90 -> [296°40'00" - 300°00'00") == [296.6666666666667 - 300.0)
    DHA3,    //  91 -> [300°00'00" - 303°20'00") == [300.0 - 303.33333333333337)
    DHA4,    //  92 -> [303°20'00" - 306°40'00") == [303.33333333333337 - 306.6666666666667)
    SAT1,    //  93 -> [306°40'00" - 310°00'00") == [306.6666666666667 - 310.0)
    SAT2,    //  94 -> [310°00'00" - 313°20'00") == [310.0 - 313.33333333333337)
    SAT3,    //  95 -> [313°20'00" - 316°40'00") == [313.33333333333337 - 316.6666666666667)
    SAT4,    //  96 -> [316°40'00" - 320°00'00") == [316.6666666666667 - 320.0)
    PBH1,    //  97 -> [320°00'00" - 323°20'00") == [320.0 - 323.33333333333337)
    PBH2,    //  98 -> [323°20'00" - 326°40'00") == [323.33333333333337 - 326.6666666666667)
    PBH3,    //  99 -> [326°40'00" - 330°00'00") == [326.6666666666667 - 330.0)
    PBH4,    // 100 -> [330°00'00" - 333°20'00") == [330.0 - 333.33333333333337)
    UBH1,    // 101 -> [333°20'00" - 336°40'00") == [333.33333333333337 - 336.6666666666667)
    UBH2,    // 102 -> [336°40'00" - 340°00'00") == [336.6666666666667 - 340.0)
    UBH3,    // 103 -> [340°00'00" - 343°20'00") == [340.0 - 343.33333333333337)
    UBH4,    // 104 -> [343°20'00" - 346°40'00") == [343.33333333333337 - 346.6666666666667)
    REV1,    // 105 -> [346°40'00" - 350°00'00") == [346.6666666666667 - 350.0)
    REV2,    // 106 -> [350°00'00" - 353°20'00") == [350.0 - 353.33333333333337)
    REV3,    // 107 -> [353°20'00" - 356°40'00") == [353.33333333333337 - 356.6666666666667)
    REV4;    // 108 -> [356°40'00" - 360°00'00") == [356.6666666666667 - 360.0)
    
    @Override
    public int uid() {
        return ordinal();
    }
    
    /**
     * @return the sign of the pada
     */
    @Override
    public IRasi rasi() {
        return ERasi.byLongitude((ordinal() - 1) * NAKSHATRA_PADA_LENGTH);
    }

    /**
     * @return the navamsa sign of the pada
     */
    @Override
    public IRasi navamsa() {
        return ERasi.byIndex(ordinal());
    }

    /**
     * @return the nakshatra of the pada
     */
    @Override
    public INaksatra naksatra() {
        return ENaksatra.values()[1 + ((uid() - 1) / 4)].naksatra();
    }

    @Override
    public INaksatraPada first() {
        return ASH1;
    }

    @Override
    public INaksatraPada last() {
        return REV4;
    }

    @Override
    public INaksatraPada[] all() {
        return values();
    }

    public static ISweEnumIterator<INaksatraPada> iterator() {
        return new SweEnumIterator<>(values(), ASH1.uid());
    }

    public static ISweEnumIterator<INaksatraPada> iteratorFrom(final INaksatraPada pada) {
        return new SweEnumIterator<>(values(), pada.uid());
    }

    public static ISweEnumIterator<INaksatraPada> iteratorTo(final INaksatraPada pada) {
        return new SweEnumIterator<>(values(), ASH1.uid(), pada.uid());
    }

    // Nakshatra Pada = Longitude / 3 deg. 20 min
    public static INaksatraPada byLongitude(final double longitude) {
        return byUid(1 + (int) (fix360(longitude) / NAKSHATRA_PADA_LENGTH));
    }

    public static INaksatraPada byCode(String code) {
        return ISweEnum.byCode(code, values());
    }

    public static INaksatraPada byUid(int index) {
        return ISweEnum.byIndex(index, values());
    }

}