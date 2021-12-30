/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.IKundaliOptions;
import org.jyotisa.api.upagraha.IUpagrahaEntity;
import org.jyotisa.api.upagraha.IUpagrahas;
import org.jyotisa.app.KundaliRuntimeException;
import org.swisseph.api.ISweObjects;

import static org.jyotisa.upagraha.EUpagraha.*;
import static org.jyotisa.upagraha.UpagrahaDhuma.DHU;
import static org.jyotisa.upagraha.UpagrahaIndrachaapa.CHP;
import static org.jyotisa.upagraha.UpagrahaParivesha.PAR;
import static org.jyotisa.upagraha.UpagrahaUpaketu.UPK;
import static org.jyotisa.upagraha.UpagrahaVyatipaata.VYA;
import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.api.ISweObjects.SY;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-12
 */
public class Upagrahas implements IUpagrahas {
    private static final long serialVersionUID = -7745657850507246704L;
    protected final IUpagrahaEntity[] all = new UpagrahaEntity[UPAKETU.uid() + 1]; // so far only 5 are supported

    public Upagrahas(final IKundaliOptions options, final ISweObjects sweObjects) {
        calcMainUpagrahas(sweObjects);
    }

    protected void calcMainUpagrahas(ISweObjects sweObjects) {
        final double suryaLongitude = sweObjects.longitudes()[SY];

        // Sun's longitude + 133º 20'
        double degree = fix360(suryaLongitude + d133 + dd20);
        all[DHUMA.uid()] = new UpagrahaEntity(DHU, sweObjects, degree);

        // 360º – Dhuma’s longitude
        degree = fix360(d360 - degree);
        all[VYATIPAATA.uid()] = new UpagrahaEntity(VYA, sweObjects, degree);

        // Vyatipata's longitude + 180
        degree = fix360(degree + d180);
        all[PARIVESHA.uid()] = new UpagrahaEntity(PAR, sweObjects, degree);

        // 360º – Parivesha’s longitude
        degree = fix360(d360 - degree);
        all[INDRACHAAPA.uid()] = new UpagrahaEntity(CHP, sweObjects, degree);

        // Indrachaapa’s longitude + 16º 40' (or) Sun's longitude – 30
        degree = fix360(degree + d16 + dd40);
        all[UPAKETU.uid()] = new UpagrahaEntity(UPK, sweObjects, degree);
    }

    @Override
    public IUpagrahaEntity dhuma() {
        return all[DHUMA.uid()];
    }

    @Override
    public IUpagrahaEntity vyatipaata() {
        return all[VYATIPAATA.uid()];
    }

    @Override
    public IUpagrahaEntity parivesha() {
        return all[PARIVESHA.uid()];
    }

    @Override
    public IUpagrahaEntity indrachaapa() {
        return all[INDRACHAAPA.uid()];
    }

    @Override
    public IUpagrahaEntity upaketu() {
        return all[UPAKETU.uid()];
    }

    @Override
    public IUpagrahaEntity kaala() {
        throw new KundaliRuntimeException("Upagraha is not implemented yet");
    }

    @Override
    public IUpagrahaEntity mrityu() {
        throw new KundaliRuntimeException("Upagraha is not implemented yet");
    }

    @Override
    public IUpagrahaEntity arthaprahaara() {
        throw new KundaliRuntimeException("Upagraha is not implemented yet");
    }

    @Override
    public IUpagrahaEntity yamaghantaka() {
        throw new KundaliRuntimeException("Upagraha is not implemented yet");
    }

    @Override
    public IUpagrahaEntity gulika() {
        throw new KundaliRuntimeException("Upagraha is not implemented yet");
    }

    @Override
    public IUpagrahaEntity maandi() {
        throw new KundaliRuntimeException("Upagraha is not implemented yet");
    }

    @Override
    public IUpagrahaEntity[] all() {
        return all;
    }
}
