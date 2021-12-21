package com.mzik.model;

import lombok.Value;

@Value
public class ArrivalCarPark {
    String city;
    String carParkName;

    @Override
    public String toString() {
        return city + " - " + carParkName;
    }
}
