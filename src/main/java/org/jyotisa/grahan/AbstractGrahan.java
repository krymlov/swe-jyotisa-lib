/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-12
*/

package org.jyotisa.grahan;


import org.jyotisa.api.grahan.IGrahan;
import org.swisseph.api.ISweGeoLocation;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-12
 */
public class AbstractGrahan implements IGrahan {
    private static final long serialVersionUID = 8356319762421243710L;

    protected final double[] coordinates;
    protected final double[] attributes = new double[20];
    protected final double[] occasions = new double[10];

    protected AbstractGrahan(ISweGeoLocation geo) {
        coordinates = geo.coordinates();
    }

    @Override
    public double[] coordinates() {
        return coordinates;
    }

    @Override
    public double[] occasions() {
        return occasions;
    }

    @Override
    public double[] attributes() {
        return attributes;
    }
}
