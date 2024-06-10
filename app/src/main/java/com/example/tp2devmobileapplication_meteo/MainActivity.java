package com.example.tp2devmobileapplication_meteo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView pageTitle, coordinate, dateTimeLabel, temperatureLabel, humidityLabel, windStrengthLabel, windDirectionLabel, rainLevelLabel;
    private ImageButton gpsButton, searchButton, nextButton, prevButton;
    private EditText searchField;
    private ImageView imageMeteo;

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
        coordinate = findViewById(R.id.coordinate);
        dateTimeLabel = findViewById(R.id.dateTime_label);
        temperatureLabel = findViewById(R.id.temperature_label);
        humidityLabel = findViewById(R.id.humidity_label);
        windStrengthLabel = findViewById(R.id.WindStrengh_label);
        windDirectionLabel = findViewById(R.id.WindDirection_label);
        rainLevelLabel = findViewById(R.id.RainLevel_label);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        String city = searchField.getText().toString();
        Location location = new Location(city, 47.311f, 5.069f);
        IForecastProvider forecastProvider = new HardcodedForecastProvider();
        WeatherForecast forecast = forecastProvider.getForecast(location);

        if (forecast.getSize() > 0) {
            Weather firstWeather = forecast.getForecast(0);
            showWeather(firstWeather);
            showLocation(location);
        }
    }

    private void showWeather(Weather weather) {
        temperatureLabel.setText("Temperature: " + weather.getTemperature() + "°C");
        humidityLabel.setText("Humidity: " + weather.getHumidity() + "%");
        windStrengthLabel.setText("Wind Speed: " + weather.getWindSpeed() + " km/h");
        windDirectionLabel.setText("Wind Direction: " + weather.getWindDirection());
        rainLevelLabel.setText("Rain Level: " + weather.getPrecipitation() + " mm");
    }

    private void showLocation(Location location) {
        String latDMS = GeoLocFormat.latitudeDMS(location.getLatitude());
        String lonDMS = GeoLocFormat.longitudeDMS(location.getLongitude());
        coordinate.setText("Location: " + location.getCity() +
                " (" + latDMS + ", " + lonDMS + ")");
    }
}