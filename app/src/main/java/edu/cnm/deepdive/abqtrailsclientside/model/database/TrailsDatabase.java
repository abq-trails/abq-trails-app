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

/**
 *
 *
 */
@Database(entities = Trail.class, version = 1)
public abstract class TrailsDatabase extends RoomDatabase {

  private static TrailsDatabase INSTANCE;

  /**
   * Returns instance of this database.
   * @param context this application context
   * @return instance of this database
   */
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

  /**
   *
   * @return
   */
  public abstract TrailDao trailDao();

 private static class PopulateDbTask extends AsyncTask<Void, Void, Void> {

   private final TrailsDatabase db;

   /**
    * Populates <code>TrailsDatabase</code>
    * @param db TrailsDatabase
    */
   PopulateDbTask(TrailsDatabase db) { // TODO Make public?
     this.db = db;
   }

   //TODO Replace preload with trail data pulled from server.
   @Override
   protected Void doInBackground(Void... voids) {
     Trail trail1 = new Trail();
     trail1.setName("Test Entry");
     trail1.setLength(5.2);
     db.trailDao().insert(trail1);
     return null;
   }
 }

}
