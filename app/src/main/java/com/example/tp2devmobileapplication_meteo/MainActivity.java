package com.example.tp2devmobileapplication_meteo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private TextView pageTitle, coordinate, dateTimeLabel, temperatureLabel, humidityLabel, windStrengthLabel, windDirectionLabel, rainLevelLabel;
    private ImageButton gpsButton, searchButton, nextButton, prevButton;
    private Location localisation;
    private EditText searchField;
    private ImageView imageMeteo;

    private FusedLocationProviderClient fusedLocationClient;

    private int currentForecastIndex = 0;
    private WeatherForecast currentForecast;

    private static final String TAG = "OpenMeteoProvider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchField = findViewById(R.id.search_field);
        ImageButton searchButton = findViewById(R.id.search_button);
        ImageButton gpsButton = findViewById(R.id.gps_button);
        ImageButton prevButton = findViewById(R.id.prev_button);
        ImageButton nextButton = findViewById(R.id.next_button);
        coordinate = findViewById(R.id.coordinate);
        dateTimeLabel = findViewById(R.id.dateTime_label);
        temperatureLabel = findViewById(R.id.temperature_label);
        humidityLabel = findViewById(R.id.humidity_label);
        windStrengthLabel = findViewById(R.id.WindStrengh_label);
        windDirectionLabel = findViewById(R.id.WindDirection_label);
        rainLevelLabel = findViewById(R.id.RainLevel_label);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        initializeDateTimeLabel();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocation();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentForecast != null && currentForecastIndex > 0) {
                    currentForecastIndex--;
                    Weather previousWeather = currentForecast.getForecast(currentForecastIndex);
                    showWeather(previousWeather);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentForecast != null && currentForecastIndex < currentForecast.getSize() - 1) {
                    currentForecastIndex++;
                    Weather nextWeather = currentForecast.getForecast(currentForecastIndex);
                    showWeather(nextWeather);
                }
            }
        });

    }

    private void initializeDateTimeLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        dateTimeLabel.setText(currentDateAndTime);
    }

    private void performSearch() {
        new Thread(() -> {
            String city = searchField.getText().toString();
            com.example.tp2devmobileapplication_meteo.Location location = new com.example.tp2devmobileapplication_meteo.Location(city, 47.311f, 8.069f);
            if(localisation != null){
                location = new com.example.tp2devmobileapplication_meteo.Location(city, (float) localisation.getLatitude(), (float) localisation.getLongitude());
            }
            IForecastProvider forecastProvider = new OpenMeteoProvider();
            currentForecast = forecastProvider.getForecast(location);

            com.example.tp2devmobileapplication_meteo.Location finalLocation = location;
            runOnUiThread(() -> {
                if (currentForecast != null && currentForecast.getSize() > 0) {
                    currentForecastIndex = 0;
                    Weather firstWeather = currentForecast.getForecast(currentForecastIndex);
                    showWeather(firstWeather);
                    showLocation(finalLocation);
                } else {
                    // Handle the case where no forecast data is available
                    Log.e(TAG, "No forecast data available");
                }
            });
        }).start();
    }

    private void showWeather(Weather weather) {
        String formattedDateTime = realDay(weather.getDay(), weather.getHour());
        dateTimeLabel.setText(formattedDateTime);
        temperatureLabel.setText(getString(R.string.Temperature_Label) + ": " + weather.getTemperature() + "°C");
        humidityLabel.setText(getString(R.string.Humidity_Label) + ": " + weather.getHumidity() + "%");
        windStrengthLabel.setText(getString(R.string.windStrength_Label) + ": " + weather.getWindSpeed() + " km/h");
        windDirectionLabel.setText(getString(R.string.windDirection_Label) + ": " + weather.getWindDirection());
        rainLevelLabel.setText(getString(R.string.rainLevel_Label) + ": " + weather.getPrecipitation() + " mm");
        ImageView weatherImage = findViewById(R.id.image_meteo);

        switch (weather.getWeatherCode()) {
            case CLEAR_SKY:
                weatherImage.setImageResource(R.drawable.sunny);
                break;
            case PARTIAL_CLOUDED:
                weatherImage.setImageResource(R.drawable.partial_clouded);
                break;
            case FOGGY_CLOUDED:
                weatherImage.setImageResource(R.drawable.cloudy);
                break;
            case SMALL_RAIN:
                weatherImage.setImageResource(R.drawable.small_rain);
                break;
            case HEAVY_RAIN:
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case SNOW:
                weatherImage.setImageResource(R.drawable.snow);
                break;
            case THUNDERSTORM:
                weatherImage.setImageResource(R.drawable.thunder);
                break;
            default:
                weatherImage.setImageResource(R.drawable.sunny);
                break;
        }
    }

    private String realDay(int day, int hour) {
        Calendar c = Calendar.getInstance();
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, getResources().getConfiguration().locale);
        c.setTime(new Date());
        c.add(Calendar.DATE, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        return df.format(c.getTime()) + String.format(" - %02d h", c.get(Calendar.HOUR_OF_DAY));
    }


    private void showLocation(com.example.tp2devmobileapplication_meteo.Location location) {
        String latDMS = GeoLocFormat.latitudeDMS(location.getLatitude());
        String lonDMS = GeoLocFormat.longitudeDMS(location.getLongitude());
        coordinate.setText(getString(R.string.Coordinate_Label) + ": " + location.getCity() + " (" + latDMS + ", " + lonDMS + ")");
    }

    private void requestLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLastLocation();
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnCompleteListener(this, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    performLocationSearch(location);
                    localisation = location;
                } else {
                    // Handle the case where no location is found or task failed
                }
            }
        });
    }

    private void performLocationSearch(Location location) {
        searchField.setText("");  // netoie le champs search
        com.example.tp2devmobileapplication_meteo.Location loc = new com.example.tp2devmobileapplication_meteo.Location("", (float) location.getLatitude(), (float) location.getLongitude());
        //IForecastProvider forecastProvider = new OpenMeteoProvider();
        //WeatherForecast forecast = forecastProvider.getForecast(loc);

        showLocation(loc);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                // Handle the case where permission was not granted
            }
        }
    }

}