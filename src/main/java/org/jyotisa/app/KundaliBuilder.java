/*
 * Copyright (C) By the Author
* Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.app;


import org.jyotisa.api.IKundali;
import org.jyotisa.api.IKundaliOptions;
import org.jyotisa.graha.EGraha;
import org.swisseph.ISwissEph;
import org.swisseph.api.ISweGeoLocation;
import org.swisseph.api.ISweJulianDate;
import org.swisseph.api.ISweObjects;
import org.swisseph.api.ISweObjectsOptions;
import org.swisseph.app.SweObjects;

/**
 * The class is intended to build {@link IKundali} instance
 *
 * @author Yura Krymlov
 * @version 1.1, 2019-10
 */
public class KundaliBuilder {
    protected final IKundaliOptions kundaliOptions;
    protected final SweObjects objectsBuilder;

    public KundaliBuilder(ISwissEph swissEph, ISweJulianDate julianDate, ISweObjects sweObjects) {
        this.objectsBuilder = new SweObjects(swissEph, julianDate, sweObjects);
        this.kundaliOptions = KundaliOptions.byDefault();
    }
    
    public KundaliBuilder(ISwissEph swissEph, ISweJulianDate julianDate, ISweGeoLocation geolocation, ISweObjectsOptions objectsOptions) {
        this(KundaliOptions.byDefault(), swissEph, julianDate, geolocation, objectsOptions);
    }
    
    public KundaliBuilder(IKundaliOptions options, ISwissEph swissEph, ISweJulianDate julianDate, ISweGeoLocation geolocation, ISweObjectsOptions objectsOptions) {
        this.objectsBuilder = new SweObjects(swissEph, julianDate, geolocation, objectsOptions);
        this.kundaliOptions = options;
    }

    public IKundali buildForGraha(EGraha graha) {
        return new Kundali(kundaliOptions, objectsBuilder.buildObject(graha.uid()));
    }

    public IKundali buildForPanchanga() {
        return new Kundali(kundaliOptions, objectsBuilder.buildSunMoon());
    }

    public IKundali buildChayaGraha() {
        return new Kundali(kundaliOptions, objectsBuilder.buildLunarNodes());
    }

    public IKundali buildLagnaOnly() {
        return new Kundali(kundaliOptions, objectsBuilder.buildAscendant());
    }

    public IKundali buildForKundali() {
        ISweObjects sweObjects = objectsBuilder.buildSunMoon();
        return new Kundali(kundaliOptions, sweObjects.buildMarsKetu());
    }
    
    public IKundali completeBuild() {
        return new Kundali(kundaliOptions, objectsBuilder.completeBuild());
    }

}
