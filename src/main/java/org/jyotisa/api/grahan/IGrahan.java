/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-12
*/

package org.jyotisa.api.grahan;

import java.io.Serializable;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2019-12
 */
public interface IGrahan extends Serializable {
    /**
     * @return double[3] containing the longitude, latitude and
     *         height of the geographic position for which {@link IGrahan} should be calculated. 
     *         Eastern longitude and northern latitude is given by positive values, western longitude 
     *         and southern latitude by negative values.
     */
    double[] getCoordinates();
    
    double[] getOccasions();
    double[] getAttributes();
}
