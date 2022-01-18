/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022-01
 */
package org.jyotisa.api.ekadasi;

import org.jyotisa.api.IKundaliSequence;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public interface IEkadasi extends IKundaliSequence<IEkadasi> {
    @Override
    default double length() {
        return 24d;
    }

    // Ekadasis
    String EK01_CD = "EK1";
    String EK02_CD = "EK2";
    String EK03_CD = "EK3";
    String EK04_CD = "EK4";
    String EK05_CD = "EK5";
    String EK06_CD = "EK6";
    String EK07_CD = "EK7";
    String EK08_CD = "EK8";
    String EK09_CD = "EK9";
    String EK10_CD = "EK10";
    String EK11_CD = "EK11";
    String EK12_CD = "EK12";
    String EK13_CD = "EK13";
    String EK14_CD = "EK14";
    String EK15_CD = "EK15";
    String EK16_CD = "EK16";
    String EK17_CD = "EK17";
    String EK18_CD = "EK18";
    String EK19_CD = "EK19";
    String EK20_CD = "EK20";
    String EK21_CD = "EK21";
    String EK22_CD = "EK22";
    String EK23_CD = "EK23";
    String EK24_CD = "EK24";
    String EK25_CD = "EK25";
    String EK26_CD = "EK26";

}
