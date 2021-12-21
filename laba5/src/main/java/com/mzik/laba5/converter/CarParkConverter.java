package com.mzik.laba5.converter;

import com.mzik.laba5.model.CarPark;

import javax.persistence.AttributeConverter;
import java.util.Optional;

public class CarParkConverter implements AttributeConverter<CarPark, String> {

    @Override
    public String convertToDatabaseColumn(CarPark attribute) {
        return Optional.ofNullable(attribute)
                .map(it -> it.getCity() + " " + it.getCarParkName())
                .orElse("");
    }

    @Override
    public CarPark convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .map(CarParkConverter::convertToCarPark)
                .orElse(new CarPark("unknown", "unknown"));
    }

    private static CarPark convertToCarPark(String str) {
        var split = str.split(" ");
        if (split.length == 2)
            return new CarPark(split[0], split[1]);
        else
            return new CarPark("unknown", split[0]);
    }
}
