/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.maasa;

import org.apache.commons.lang3.NotImplementedException;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

import static org.jyotisa.maasa.IMaasa.*;

/**
 * A solar year has about 365.2425 days, but a lunar year only has about 355 days.
 * Once in every 3 years, this difference accumulates to one month and an extra lunar
 * month comes. This results in Sun-Moon conjunction coming twice in the same rasi.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-12
 */
public enum EMaasa implements IMaasaEnum {
    NIL {
        @Override public int fid() { return NIL_FID; }
        @Override public String code() { return NIL_CD; }
        @Override public IMaasa maasa() { return null; }
    }, // 0  Reserved
    MADHUSUDANA {
        @Override public int fid() { return 1; }
        @Override public String code() { return APRMAY_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(APRMAY_CD);}
    },
    TRIVIKRAMA {
        @Override public int fid() { return 2; }
        @Override public String code() { return MAYJUN_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(MAYJUN_CD);}
    },
    VAMANA {
        @Override public int fid() { return 3; }
        @Override public String code() { return JUNJUL_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(JUNJUL_CD);}
    },
    SRIDHARA {
        @Override public int fid() { return 4; }
        @Override public String code() { return JULAUG_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(JULAUG_CD);}
    },
    HRSIKESA {
        @Override public int fid() { return 5; }
        @Override public String code() { return AUGSEP_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(AUGSEP_CD);}
    },
    PADMANABHA {
        @Override public int fid() { return 6; }
        @Override public String code() { return SEPOCT_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(SEPOCT_CD);}
    },
    DAMODARA {
        @Override public int fid() { return 7; }
        @Override public String code() { return OCTNOV_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(OCTNOV_CD);}
    },
    KESAVA {
        @Override public int fid() { return 8; }
        @Override public String code() { return NOVDEC_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(NOVDEC_CD);}
    },
    NARAYANA {
        @Override public int fid() { return 9; }
        @Override public String code() { return DECJAN_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(DECJAN_CD);}
    },
    MADHAVA {
        @Override public int fid() { return 10; }
        @Override public String code() { return JANFEB_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(JANFEB_CD);}
    },
    GOVINDA {
        @Override public int fid() { return 11; }
        @Override public String code() { return FEBMAR_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(FEBMAR_CD);}
    },
    VISNU {
        @Override public int fid() { return 12; }
        @Override public String code() { return MARAPR_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(MARAPR_CD);}
    },
    PURADH { // PURUSOTTAMA_ADHIKA
        @Override public int fid() { return 13; }
        @Override public String code() { return PURADH_CD; }
        @Override public IMaasa maasa() { throw new NotImplementedException(PURADH_CD);}
    };


    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IMaasaEnum first() {
        return MADHUSUDANA;
    }

    @Override
    public IMaasaEnum last() {
        return VISNU;
    }

    @Override
    public IMaasaEnum[] all() {
        return values();
    }


    public static IMaasaEnum byFid(final int fid) {
        return ISweEnum.byFid(fid, values());
    }

    public static IMaasaEnum byCode(final String code) {
        return ISweEnum.byCode(code, values());
    }

    public static ISweEnumIterator<IMaasaEnum> iterator() {
        return new SweEnumIterator<>(values(), MADHUSUDANA.uid());
    }

    public static ISweEnumIterator<IMaasaEnum> iteratorFrom(final IMaasaEnum maasa) {
        return new SweEnumIterator<>(values(), maasa.uid());
    }

    public static ISweEnumIterator<IMaasaEnum> iteratorTo(final IMaasaEnum maasa) {
        return new SweEnumIterator<>(values(), MADHUSUDANA.uid(), maasa.uid());
    }
}
