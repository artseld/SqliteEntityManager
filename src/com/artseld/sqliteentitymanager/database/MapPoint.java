package com.artseld.sqliteentitymanager.database;

/**
 * MapPoint entity class
 */
public class MapPoint implements Entity {

    private int     _id;
    private String  _name;
    private String  _description;
    private double  _latitude;
    private double  _longitude;

    public MapPointRepository getRepository() {
        return MapPointRepository.getInstance();
    }

    public MapPoint() {}

    public MapPoint(String name, String description) {
        this._name = name;
        this._description = description;
    }

    public MapPoint(String name, String description, double latitude, double longitude) {
        this._name = name;
        this._description = description;
        this._latitude = latitude;
        this._longitude = longitude;
    }

    public int getId() {
        return this._id;
    }

    public String getName() {
        return this._name;
    }

    public MapPoint setName(String name) {
        this._name = name;
        return this;
    }

    public String getDescription() {
        return this._description;
    }

    public MapPoint setDescription(String description) {
        this._description = description;
        return this;
    }

    public double getLatitude() {
        return this._latitude;
    }

    public MapPoint setLatitude(double latitude) {
        this._latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return this._longitude;
    }

    public MapPoint setLongitude(double longitude) {
        this._longitude = longitude;
        return this;
    }

}
