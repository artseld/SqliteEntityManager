package com.artseld.sqliteentitymanager.database;

import java.lang.reflect.Field;

/**
 * Entity reflection methods
 */
abstract class EntityRepositoryReflection {

    protected void setId(Entity entity, Integer value) {
        try {
            Class<?> c = entity.getClass();
            Field f = c.getDeclaredField("_id");
            f.setAccessible(true);
            f.setInt(entity, value); // IllegalAccessException
            // production code should handle these exceptions more gracefully
        } catch (NoSuchFieldException x) {
            x.printStackTrace();
        } catch (IllegalArgumentException x) {
            x.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        }
    }

}
