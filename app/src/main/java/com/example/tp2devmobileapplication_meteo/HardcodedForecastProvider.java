package com.example.tp2devmobileapplication_meteo;

import java.util.ArrayList;
import java.util.List;

public class HardcodedForecastProvider implements IForecastProvider {

    @Override
    public WeatherForecast getForecast(Location location) {
        WeatherForecast forecast = new WeatherForecast();

        // jour 0, heure 22
        forecast.addForecast(new Weather(0, 22, WeatherCodes.CLEAR_SKY, 25, 60, 10, "N", 0));
        // jour 0, heure 23
        forecast.addForecast(new Weather(0, 23, WeatherCodes.PARTIAL_CLOUDED, 22, 65, 8, "NE", 0));
        // jour 1, heure 0
        forecast.addForecast(new Weather(1, 0, WeatherCodes.FOGGY_CLOUDED, 18, 70, 5, "E", 0));
        // jour 1, heure 1
        forecast.addForecast(new Weather(1, 1, WeatherCodes.SMALL_RAIN, 17, 85, 12, "SE", 1));

        forecast.addForecast(new Weather(1, 2, WeatherCodes.HEAVY_RAIN, 15, 90, 15, "S", 5));
        forecast.addForecast(new Weather(1, 3, WeatherCodes.SNOW, 0, 95, 20, "SW", 10));
        forecast.addForecast(new Weather(1, 4, WeatherCodes.THUNDERSTORM, 12, 80, 25, "W", 7));

        return forecast;
    }
}

