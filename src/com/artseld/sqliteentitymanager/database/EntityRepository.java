package com.artseld.sqliteentitymanager.database;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

/**
 * Entity repository interface
 */
public interface EntityRepository {

    public String getTableName();
    public String getKeyId();
    public String[] getKeys();

    public String getSQLCreateTable();

    public ContentValues getContentValues(Entity entity);
    public Entity buildEntityFromCursorData(Cursor cursor);
    public List selectToList(Cursor cursor);
}
