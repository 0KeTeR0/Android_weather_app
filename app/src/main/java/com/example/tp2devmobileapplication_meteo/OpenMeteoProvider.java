package com.example.tp2devmobileapplication_meteo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OpenMeteoProvider implements IForecastProvider{
    private static final String API_URL = "https://api.open-meteo.com/v1/forecast";
    private static final String TAG = "OpenMeteoProvider";

    @Override
    public WeatherForecast getForecast(com.example.tp2devmobileapplication_meteo.Location location) {
        try {
            String urlString = API_URL + "?latitude=" + location.getLatitude() + "&longitude=" + location.getLongitude() +
                    "&current=temperature_2m,relative_humidity_2m,precipitation,weather_code,wind_speed_10m,wind_direction_10m" +
                    "&hourly=temperature_2m,relative_humidity_2m,precipitation,weather_code,wind_speed_10m,wind_direction_10m" +
                    "&timezone=Europe%2FLondon";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            JSONObject jsonObject = new JSONObject(jsonBuilder.toString());
            return parseWeatherForecast(jsonObject);
        } catch (Exception e) {
            Log.e(TAG, "Error fetching weather data", e);
            return null;
        }
    }

    private WeatherForecast parseWeatherForecast(JSONObject jsonObject) throws JSONException {
        JSONArray times = jsonObject.getJSONObject("hourly").getJSONArray("time");
        JSONArray temperatures = jsonObject.getJSONObject("hourly").getJSONArray("temperature_2m");
        JSONArray humidities = jsonObject.getJSONObject("hourly").getJSONArray("relative_humidity_2m");
        JSONArray precipitations = jsonObject.getJSONObject("hourly").getJSONArray("precipitation");
        JSONArray weatherCodes = jsonObject.getJSONObject("hourly").getJSONArray("weather_code");
        JSONArray windSpeeds = jsonObject.getJSONObject("hourly").getJSONArray("wind_speed_10m");
        JSONArray windDirections = jsonObject.getJSONObject("hourly").getJSONArray("wind_direction_10m");

        List<Weather> weathers = new ArrayList<>();
        for (int i = 0; i < times.length(); i++) {
            Weather weather = new Weather();
            weather.setTemperature((int) temperatures.getDouble(i));
            weather.setHumidity((int) humidities.getDouble(i));
            weather.setPrecipitation((float) precipitations.getDouble(i));
            weather.setWeatherCode(mapWeatherCode(weatherCodes.getInt(i)));
            weather.setWindSpeed((int) windSpeeds.getDouble(i));
            weather.setWindDirection(mapWindDirection(windDirections.getInt(i)));

            // Set day and hour
            String time = times.getString(i);
            int day = parseDayFromTime(time);
            int hour = parseHourFromTime(time);
            weather.setDay(day);
            weather.setHour(hour);

            weathers.add(weather);
        }

        return new WeatherForecast(weathers);
    }

    private WeatherCodes mapWeatherCode(int code) {
        switch (code) {
            case 0:
                return WeatherCodes.CLEAR_SKY;
            case 1:
            case 2:
            case 3:
                return WeatherCodes.PARTIAL_CLOUDED;
            case 45:
            case 48:
                return WeatherCodes.FOGGY_CLOUDED;
            case 51:
            case 53:
            case 55:
            case 56:
            case 57:
                return WeatherCodes.SMALL_RAIN;
            case 61:
            case 63:
            case 65:
            case 66:
            case 67:
            case 80:
            case 81:
            case 82:
                return WeatherCodes.HEAVY_RAIN;
            case 71:
            case 73:
            case 75:
            case 77:
            case 85:
            case 86:
                return WeatherCodes.SNOW;
            case 95:
            case 96:
            case 99:
                return WeatherCodes.THUNDERSTORM;
            default:
                return WeatherCodes.CLEAR_SKY;
        }
    }

    private String mapWindDirection(int direction) {
        // Add appropriate mapping if needed, for simplicity, returning degrees as string
        return String.valueOf(direction) + "°";
    }

    private int parseDayFromTime(String time) {
        // Parse day from ISO 8601 time string
        return Integer.parseInt(time.substring(8, 10)) - Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    private int parseHourFromTime(String time) {
        // Parse hour from ISO 8601 time string
        return Integer.parseInt(time.substring(11, 13));
    }
}
