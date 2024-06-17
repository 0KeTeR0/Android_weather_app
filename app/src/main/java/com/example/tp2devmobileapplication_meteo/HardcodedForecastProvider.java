package com.example.tp2devmobileapplication_meteo;

import java.util.ArrayList;
import java.util.List;

public class HardcodedForecastProvider implements IForecastProvider {

    @Override
    public WeatherForecast getForecast(com.example.tp2devmobileapplication_meteo.Location location) {
        List<Weather> weathers = new ArrayList<>();
        // Add various weather conditions
        weathers.add(new Weather(0, 0,  WeatherCodes.CLEAR_SKY, 16,0.2f, 15 , "176°", 2));
        weathers.add(new Weather(0, 1,  WeatherCodes.PARTIAL_CLOUDED, 26,0.6f, 10 , "90°", 0));

        return new WeatherForecast(weathers);
    }
}

