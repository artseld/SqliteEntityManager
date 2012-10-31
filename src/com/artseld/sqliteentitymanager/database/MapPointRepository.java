package com.artseld.sqliteentitymanager.database;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Contact entity repository class
 */
public class MapPointRepository extends EntityRepositoryReflection implements EntityRepository {

    // Map Point table name
    private static final String TABLE_NAME = "map_point";

    // Map Point Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    private static MapPointRepository _instance;

    private MapPointRepository() {}

    public static MapPointRepository getInstance() {
        if (null == _instance) {
            _instance = new MapPointRepository();
        }
        return _instance;
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public String getKeyId() {
        return KEY_ID;
    }

    public String[] getKeys() {
        return new String[] { KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_LATITUDE, KEY_LONGITUDE };
    }

    public String getSQLCreateTable() {
        String SQL = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " VARCHAR(255),"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_LATITUDE + " DOUBLE,"
            + KEY_LONGITUDE + " DOUBLE"
            + ")";
        return SQL;
    }

    public ContentValues getContentValues(Entity entity) {
        MapPoint mapPoint = (MapPoint) entity;
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, mapPoint.getName());
        values.put(KEY_DESCRIPTION, mapPoint.getDescription());
        values.put(KEY_LATITUDE, mapPoint.getLatitude());
        values.put(KEY_LONGITUDE, mapPoint.getLongitude());
        return values;
    }

    public MapPoint buildEntityFromCursorData(Cursor cursor) {
        MapPoint mapPoint = new MapPoint();
        mapPoint.setName(cursor.getString(1)).setDescription(cursor.getString(2));
        mapPoint.setLatitude(Double.parseDouble(cursor.getString(3)));
        mapPoint.setLongitude(Double.parseDouble(cursor.getString(4)));
        setId(mapPoint, Integer.parseInt(cursor.getString(0)));
        return mapPoint;
    }

    public List<MapPoint> selectToList(Cursor cursor) {
        List<MapPoint> result = new ArrayList<MapPoint>();
        if (cursor.moveToFirst()) {
            do {
                MapPoint mapPoint = new MapPoint();
                mapPoint.setName(cursor.getString(1));
                mapPoint.setDescription(cursor.getString(2));
                mapPoint.setLatitude(Double.parseDouble(cursor.getString(3)));
                mapPoint.setLongitude(Double.parseDouble(cursor.getString(4)));
                setId(mapPoint, Integer.parseInt(cursor.getString(0)));
                result.add(mapPoint);
            } while (cursor.moveToNext());
        }
        return result;
    }

}
