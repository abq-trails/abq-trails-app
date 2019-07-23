package edu.cnm.deepdive.abqtrailsclientside.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;

@Dao
public interface TrailDao {

  @Insert
  long insert(Trail trail);

}
