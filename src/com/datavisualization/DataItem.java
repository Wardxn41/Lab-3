package com.datavisualization;

public class DataItem {
    private String siteName;
    private String country;
    private int year;
    private String category;
    private String removalDate;
    private double latitude;
    private double longitude;

    public DataItem(String siteName, String country, int year, String category, String removalDate, double latitude, double longitude) {
        this.siteName = siteName;
        this.country = country;
        this.year = year;
        this.category = category;
        this.removalDate = removalDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getSiteName() { return siteName; }
    public String getCountry() { return country; }
    public int getYear() { return year; }
    public String getCategory() { return category; }
    public String getRemovalDate() { return removalDate; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    @Override
    public String toString() {
        return siteName + " | " + country + " | " + year + " | " + category +
                " | Inscription: " + removalDate +
                " | Lat: " + latitude + " | Lon: " + longitude;
    }
}

