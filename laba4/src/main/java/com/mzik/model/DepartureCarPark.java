package com.mzik.model;

import lombok.Value;

@Value
public class DepartureCarPark {
    String city;
    String carParkName;

    @Override
    public String toString() {
        return city + " - " + carParkName;
    }
}
