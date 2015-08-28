package com.customtabs.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tejoa on 20/05/2014.
 */
public class Location implements Serializable {

    /**
     * generated
     */
    private static final long serialVersionUID = 2153015407138524370L;

    public String name;


    public Double longitude;
    public Double latitude;
    public String description;
    public List<ImagePair> imagePairs;
    public List<Attractions> attractions;


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
