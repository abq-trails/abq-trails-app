package edu.cnm.deepdive.abqtrailsclientside;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.abqtrailsclientside.model.database.TrailsDatabase;

public class ABQTrailsApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    new Thread(() -> TrailsDatabase.getInstance(this).trailDao().delete()).start();
  }
}
