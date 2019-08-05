package edu.cnm.deepdive.abqtrailsclientside.model.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import edu.cnm.deepdive.abqtrailsclientside.model.dao.TrailDao;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import edu.cnm.deepdive.abqtrailsclientside.model.viewmodel.TrailViewModel;
import edu.cnm.deepdive.abqtrailsclientside.service.AbqTrailsService;
import io.reactivex.schedulers.Schedulers;

@Database(entities = Trail.class, version = 1, exportSchema = true)
public abstract class TrailsDatabase extends RoomDatabase {

  private static TrailsDatabase INSTANCE;
  private static TrailViewModel viewModel;

  public static TrailsDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TrailsDatabase.class,
          "trails_room").fallbackToDestructiveMigration().addCallback(new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
          super.onCreate(db);
//          new PopulateDbTask(INSTANCE).execute();
          INSTANCE.getOnlineDb();
        }
      }).build();
    }
    return INSTANCE;
  }

  public abstract TrailDao trailDao();

  private void getOnlineDb() {

    TrailDao dao = trailDao();
     AbqTrailsService service = AbqTrailsService.getInstance();
     service.listTrails()
         .subscribeOn(Schedulers.io())
         .subscribe((trails) -> {
           for (Trail trail : trails) {
             long id = dao.insert(trail);
             if (id < 0) {
               Trail existing = dao.findByCabqIdSynchronous(trail.getCabqId());
               existing.setCoordinates(trail.getCoordinates());
               existing.setRating(trail.getRating());
               existing.setHorse(trail.isHorse());
               existing.setBike(trail.isBike());
               existing.setName(trail.getName());
               existing.setLength(trail.getLength());
               dao.update(existing);
             }
           }
         });
  }
}
