package com.example.tp2devmobileapplication_meteo;

/**
 * Interface to provider layer
 */
public interface IForecastProvider {
    /**
     * Gets the forecasts for the given location (for today & after)
     * @param location the location
     * @return the forecasts
     */
    WeatherForecast getForecast(Location location);
}
