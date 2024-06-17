package com.example.tp2devmobileapplication_meteo;

/**
 * Properties of a weather forecast
 */
public class Weather {
    private int day;
    private int hour;
    private WeatherCodes weatherCode;
    private int temperature;
    private float humidity;
    private int windSpeed;
    private String windDirection;
    private float precipitation;

    public Weather(int day, int hour, WeatherCodes weatherCodes, int temperature, float humidity, int windSpeed, String windDirection, float precipitation) {
        this.day = day;
        this.hour = hour;
        this.weatherCode = weatherCodes;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.precipitation = precipitation;
    }

    public Weather() {

    }

    /**
     * Get the day of the forecast
     * @return number of the day (relative to today). 0=today, 1=tomorrow...
     */
    public int getDay() {
        return day;
    }

    /**
     * Set the day
     * @param day (0=today)
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Get the hour of the forecast (relative to day)
     * @return the hour (between 0 and 23)
     */
    public int getHour() {
        return hour;
    }

    /**
     * Sets the hour
     * @param hour (between 0 and 23)
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * Get the code of the weather
     * @return the code
     */
    public WeatherCodes getWeatherCode() {
        return weatherCode;
    }

    /**
     * Sets the code of the weather
     * @param weatherCode
     */
    public void setWeatherCode(WeatherCodes weatherCode) {
        this.weatherCode = weatherCode;
    }

    /**
     * Get the temperature in °C
     * @return the temperature in °C
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * Sets the temperature
     * @param temperature in °C
     */
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    /**
     * Gets the humidity
     * @return humidity in % (45 = 45% humidity)
     */
    public float getHumidity() {
        return humidity;
    }

    /**
     * Sets the humidity
     * @param humidity in %
     */
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    /**
     * Gets the wind speed
     * @return wind speed in km/h
     */
    public int getWindSpeed() {
        return windSpeed;
    }

    /**
     * Sets the windspeed
     * @param windSpeed in km/h
     */
    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * Gets the direction of the wind
     * @return direction (N, E, NE, etc.)
     */
    public String getWindDirection() {
        return windDirection;
    }

    /**
     * Sets the direction of the wind
     * @param windDirection direction (N, E, NE, etc.)
     */
    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * Gets the amount of rain or snow
     * @return amount in mm
     */
    public float getPrecipitation() {
        return precipitation;
    }

    /**
     * Sets the amount of rain or snow
     * @param precipitation in mm
     */
    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }


}
