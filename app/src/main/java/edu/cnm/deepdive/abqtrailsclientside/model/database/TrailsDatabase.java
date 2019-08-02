package edu.cnm.deepdive.abqtrailsclientside.model.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import edu.cnm.deepdive.abqtrailsclientside.model.dao.TrailDao;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import edu.cnm.deepdive.abqtrailsclientside.service.AbqTrailsService;
import io.reactivex.Observable;
import java.util.LinkedList;
import java.util.List;

@Database(entities = Trail.class, version = 1)
public abstract class TrailsDatabase extends RoomDatabase {

  private static TrailsDatabase INSTANCE;

  public static TrailsDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TrailsDatabase.class,
          "trails_room").fallbackToDestructiveMigration().addCallback(new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
          super.onCreate(db);
          new PopulateDbTask(INSTANCE).execute();
        }
      }).build();
    }
    return INSTANCE;
  }

  public abstract TrailDao trailDao();

 private static class PopulateDbTask extends AsyncTask<Void, Void, Void> {

   private final TrailsDatabase db;

   PopulateDbTask(TrailsDatabase db) {
     this.db = db;
   }

   //TODO Replace preload with trail data pulled from server.
   @Override
   protected Void doInBackground(Void... voids) {
//     Trail trail1 = new Trail();
//     trail1.setName("Test Entry");
//     trail1.setLength(5.2);
//     db.trailDao().insert(trail1);


     AbqTrailsService service = AbqTrailsService.getInstance();
     List<Trail> trails = service.listTrails();
     for (Trail trail : trails) {
      Trail newTrail = new Trail();
      newTrail.setCabqId(trail.getCabqId());
      newTrail.setName(trail.getName());
      newTrail.setCoordinates(trail.getCoordinates());
      newTrail.setBike(trail.isBike());
      newTrail.setHorse(trail.isHorse());
      newTrail.setRating(trail.getRating());
      db.trailDao().insert(newTrail);
     }

     return null;
   }
 }

}
