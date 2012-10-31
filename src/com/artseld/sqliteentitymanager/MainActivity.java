package com.artseld.sqliteentitymanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.artseld.sqliteentitymanager.database.Entity;
import com.artseld.sqliteentitymanager.database.MapPoint;
import com.artseld.sqliteentitymanager.database.MapPointRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        EntityManager em = new EntityManager(this);
        AppCommon.initDB(em);

        String log = "";

        // Reading all map points
        log += "Reading all map points ...\n";
        List<Entity> mapPoints = em.findAll(MapPointRepository.getInstance());

        for (Entity entity : mapPoints) {
            MapPoint item = (MapPoint) entity;
            log += "Id: " + item.getId() + ", Name: " + item.getName() + ", Description: " + item.getDescription()
                + ", Latitude: " + item.getLatitude() + ", Longitude: " + item.getLongitude() + "\n";
        }

        // Read concrete entity
        Map<String, String> criteria = new HashMap<String, String>();
        criteria.put("name", "Home");
        criteria.put("description", "Minsk, Belarus");
        MapPoint me = (MapPoint) em.findOneBy(MapPointRepository.getInstance(), criteria);
        log += "Find One By: ";
        if (me != null) {
            log += me.getName();
        } else {
            log += "Not found";
        }
        log += "\n";

        TextView logText = (TextView) findViewById(R.id.log);
        logText.setText(log);
    }
}
