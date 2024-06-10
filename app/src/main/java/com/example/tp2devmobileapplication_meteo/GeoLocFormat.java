package com.example.tp2devmobileapplication_meteo;

/**
 * A simple class containing primitives for the correct display of GPS coordinates
 */
public class GeoLocFormat {

    /**
     * Provides latitude in a DD/DDD format
     * @param latitude latitude's value
     * @return A string containing the value in DD.DDDDD format
     */
    public static String latitudeDDD(double latitude)
    {
        return strDDD(latitude);
    }

    /**
     * Provides longitude in a DD.DDDDD format
     * @param longitude the value of longitude
     * @return A string containing the value in DD.DDDDD format
     */
    public static String longitudeDDD(double longitude)
    {
        return strDDD(longitude);
    }
	
	private static String strDDD(double value)
	{
		return String.format("%.6f",value);
	}

    /**
     * Provides latitude in DD°MM'SS" format
     * @param latitude the value of latitude
     * @return a chain containing latitude, e.g. N 47°25'15"
     */
    public static String latitudeDMS(double latitude)
    {
        // compute north/south
        String ns;
        if(latitude<0) {
            ns = "S";
            latitude = -latitude;
        }
        else{
            ns = "N";
        }
        // Calculate the 3 components degrees, minutes, seconds
        int degres = (int)(latitude);
        double reste = latitude-degres;
        int minutes = (int)(60*reste);
        reste = latitude-degres-minutes/60.0;
        int secondes = (int)(3600*reste);

        return String.format("%s %d°%02d'%02d\"",ns,degres,minutes,secondes);
    }

    /**
     * Provides longitude in the format DD°MM'SS"
     * @param longitude the value of longitude
     * @return a string containing the longitude, e.g. W 47°25'15"
     */
    public static String longitudeDMS(double longitude)
    {
        // calculate west/east
        String ew;
        if(longitude<0) {
            ew = "W";
            longitude = -longitude;
        }
        else{
            ew = "E";
        }
        // Calculate the 3 components degrees, minutes, seconds
        int degres = (int)(longitude);
        double reste = longitude-degres;
        int minutes = (int)(60*reste);
        reste = longitude-degres-minutes/60.0;
        int secondes = (int)(3600*reste);

        return String.format("%s %d°%02d'%02d\"",ew,degres,minutes,secondes);
    }
}
