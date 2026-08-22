/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.maasa;

/**
 * The "not a maasa" member - a Null Object, so that a lookup which cannot name one has something
 * to return instead of {@code null} and without throwing.
 * <p>
 * It sits beside {@link IMaasa} rather than in {@code swe-jyotisa-api} for the simple reason that
 * {@code IMaasa} itself lives here - alone among the families, this one's interface was never
 * moved up. See {@link org.jyotisa.api.rasi.NilRasi} for the fuller account of why a Null Object
 * rather than {@code null} or an exception.
 * <p>
 * Note this is <b>not</b> an implementation of the maasa calculation: every real member of
 * {@link EMaasa} still throws {@code NotImplementedException} from {@code maasa()}. NIL is the one
 * member that can answer, because "no month" needs no arithmetic.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum NilMaasa implements IMaasa {
    NIL;

    @Override
    public int fid() {
        return NIL_FID;
    }

    @Override
    public String code() {
        return NIL_CD;
    }

    @Override
    public IMaasa[] all() {
        return values();
    }
}
