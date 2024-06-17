package com.example.tp2devmobileapplication_meteo;

import java.util.ArrayList;
import java.util.List;

/**
 * Composes forecast on several hours/days
 */
public class WeatherForecast implements IForecastProvider{
    private ArrayList<Weather> datas;

    public WeatherForecast(){
        this.datas = new ArrayList<>();
    }

    public WeatherForecast(List<Weather> weathers){
        this.datas = new ArrayList<>(weathers);
    }

    /**
     * Add a forecast at the list
     * @param w the forecast to add
     */
    public void addForecast(Weather w){
        datas.add(w);
    }

    /**
     * Get a forecast
     * @param i the position of forecast (0=first)
     * @return the weather
     */
    public Weather getForecast(int i){
        return datas.get(i);
    }

    /**
     * Gets the size (number of weather)
     * @return the size
     */
    public int getSize(){
        return datas.size();
    }

    @Override
    public WeatherForecast getForecast(Location location) {
        return null;
    }
}
