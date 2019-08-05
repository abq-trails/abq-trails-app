package edu.cnm.deepdive.abqtrailsclientside;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.abqtrailsclientside.model.dao.TrailDao;
import edu.cnm.deepdive.abqtrailsclientside.model.database.TrailsDatabase;
import edu.cnm.deepdive.abqtrailsclientside.service.GoogleSignInService;

public class ABQTrailsApplication extends Application {

//  TrailsDatabase db = TrailsDatabase.getInstance(this);

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    GoogleSignInService.setContext(this);

    new Thread(() -> TrailsDatabase.getInstance(this).trailDao().delete()).start();
//    new Thread(() -> TrailsDatabase.getInstance(this).trailDao().findByCabqIdSynchronous(1));
  }
}
