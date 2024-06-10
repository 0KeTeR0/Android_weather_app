package com.example.tp2devmobileapplication_meteo;

import java.util.ArrayList;
import java.util.List;

public class HardcodedForecastProvider implements IForecastProvider {

    @Override
    public WeatherForecast getForecast(Location location) {
        WeatherForecast forecast = new WeatherForecast();

        // Ajout de prévisions "en dur"
        forecast.addForecast(new Weather(0, 22, WeatherCodes.CLEAR_SKY, 15, 70, 10, "N", 0));
        forecast.addForecast(new Weather(0, 23, WeatherCodes.PARTIAL_CLOUDED, 14, 72, 12, "NE", 0));
        forecast.addForecast(new Weather(1, 0, WeatherCodes.FOGGY_CLOUDED, 13, 80, 5, "E", 0));
        forecast.addForecast(new Weather(1, 1, WeatherCodes.SMALL_RAIN, 12, 85, 8, "SE", 2));

        return forecast;
    }
}

