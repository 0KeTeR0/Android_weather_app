package com.example.tp2devmobileapplication_meteo;

/**
 * A location on the earth
 */
public class Location {
    private String city;
    private float latitude;
    private float longitude;

    public Location(String city, float lat, float lgt) {
        this.city = city;
        this.latitude = lat;
        this.longitude = lgt;
    }


    /**
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return the latitude (degrees)
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude
     * @param latitude latitude in degrees
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return longitude in degrees
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude
     * @param longitude longitude in degrees
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
