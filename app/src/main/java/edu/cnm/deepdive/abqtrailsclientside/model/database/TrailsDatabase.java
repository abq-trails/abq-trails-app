/*
Copyright 2019 Denelle Britton Linebarger, Alana Chigbrow, Anita Martin, David Nelson

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

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

/**
 * ??
 */
@Database(entities = Trail.class, version = 1, exportSchema = true)
public abstract class TrailsDatabase extends RoomDatabase {

  private static TrailsDatabase INSTANCE;
  private static TrailViewModel viewModel;

  /**
   * Returns instance of this database.
   *
   * @param context this application context
   * @return instance of this database
   */
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

  /**
   * ??
   */
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
