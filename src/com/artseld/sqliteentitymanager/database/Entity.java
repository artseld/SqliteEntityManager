package com.artseld.sqliteentitymanager.database;

/**
 * Entity interface
 */
public interface Entity {

    public EntityRepository getRepository();

    public int getId();
}
