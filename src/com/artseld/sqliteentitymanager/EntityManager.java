package com.artseld.sqliteentitymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.artseld.sqliteentitymanager.database.Entity;
import com.artseld.sqliteentitymanager.database.EntityRepository;
import com.artseld.sqliteentitymanager.database.MapPointRepository;

import java.lang.String;
import java.util.*;

public class EntityManager extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sqlite_em_db";

    public EntityManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Iterator<EntityRepository> it = getRepositories().listIterator();
        while (it.hasNext()) {
            db.execSQL(it.next().getSQLCreateTable());
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Iterator<EntityRepository> it = getRepositories().listIterator();
        while (it.hasNext()) {
            db.execSQL("DROP TABLE IF EXISTS " + it.next().getTableName());
        }
        onCreate(db);
    }

    // Get repositories list
    public List getRepositories() {
        List<EntityRepository> repositories = new ArrayList<EntityRepository>();
        repositories.add(MapPointRepository.getInstance());
        return repositories;
    }

    // Insert record associated with entity
    public int persist(Entity entity) {
        SQLiteDatabase db = this.getWritableDatabase();
        EntityRepository repository = entity.getRepository();
        ContentValues values = repository.getContentValues(entity);
        int result = (int) db.insert(repository.getTableName(), null, values);
        db.close();
        return result;
    }

    // Update record associated with entity
    public int update(Entity entity) {
        SQLiteDatabase db = this.getWritableDatabase();
        EntityRepository repository = entity.getRepository();
        ContentValues values = repository.getContentValues(entity);
        int result = db.update(repository.getTableName(), values, repository.getKeyId() + " = ?",
            new String[] { String.valueOf(entity.getId()) });
        db.close();
        return result;
    }

    // Remove record associated with entity
    public void remove(Entity entity) {
        SQLiteDatabase db = this.getWritableDatabase();
        EntityRepository repository = entity.getRepository();
        db.delete(repository.getTableName(), repository.getKeyId() + " = ?",
            new String[] { String.valueOf(entity.getId()) });
        db.close();
    }

    // Get entity by ID
    public Entity find(EntityRepository repository, int id) {
        Map<String, String> values = new HashMap<String, String>();
        values.put(repository.getKeyId(), String.valueOf(id));
        return findOneBy(repository, values);
    }

    // Get entity by specified field(s)
    public Entity findOneBy(EntityRepository repository, Map<String,String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> fieldsList = new ArrayList<String>();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            fieldsList.add(entry.getKey() + " = ?");
        }
        String[] fieldsArray = fieldsList.toArray(new String[values.size()]);
        String fields = AppCommon.implodeArray(fieldsArray, " AND ");
        String[] parameters = values.values().toArray(new String[values.size()]);
        Cursor cursor = db.query(repository.getTableName(), repository.getKeys(), fields,
            parameters, null, null, null, null);
        Entity entity;
        if (cursor != null && cursor.moveToFirst()) {
            entity = repository.buildEntityFromCursorData(cursor);
        } else {
            entity = null;
        }
        cursor.close();
        db.close();
        return entity;
    }

    // Get all records as array list
    public List<Entity> findAll(EntityRepository repository) {
        String selectQuery = "SELECT  * FROM " + repository.getTableName();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<Entity> result = repository.selectToList(cursor);
        cursor.close();
        db.close();
        return result;
    }

    // Calculate records count and return result
    public int count(EntityRepository repository) {
        String countQuery = "SELECT  * FROM " + repository.getTableName();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int result = cursor.getCount();
        cursor.close();
        db.close();
        return result;
    }

}
