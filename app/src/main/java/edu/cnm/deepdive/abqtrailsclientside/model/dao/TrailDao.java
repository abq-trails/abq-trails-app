package edu.cnm.deepdive.abqtrailsclientside.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import java.util.List;

@Dao
public interface TrailDao {

  @Insert
  long insert(Trail trail);

  @Query("SELECT * FROM Trail")
  LiveData<List<Trail>> getAll();

  @Query("SELECT * FROM Trail WHERE trail_id = :id")
  Trail findById(Long id);

  @Query("SELECT * FROM Trail WHERE trail_name LIKE :searchString")
  Trail search(String searchString);

  //Only used in ABQTrailsApplication.java to start db.
  @Delete
  int delete(Trail... trail);


}
