package edu.cnm.deepdive.abqtrailsclientside.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import java.util.List;

@Dao
public interface TrailDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  long insert(Trail trail);

  @Query("SELECT * FROM Trail")
  LiveData<List<Trail>> getAll();

  @Query("SELECT * FROM Trail WHERE trail_id = :id")
  LiveData<Trail> findById(Long id);

  @Query("SELECT * FROM Trail WHERE trail_name LIKE :searchString")
  LiveData<List<Trail>> search(String searchString);

  @Query("SELECT * FROM Trail WHERE trail_id = :id")
  Trail findByIdSynchronous(long id);

  @Query("SELECT * FROM Trail WHERE cabq_id = :cabqId")
  Trail findByCabqIdSynchronous(long cabqId);

  @Query("SELECT * FROM Trail WHERE cabq_id = :cabqId")
  LiveData<Trail> findByCabqId(long cabqId);

  @Update
  int update(Trail... trails);

  //Only used in ABQTrailsApplication.java to start db.
  @Delete
  int delete(Trail... trail);


}
