package edu.cnm.deepdive.abqtrailsclientside.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Review;

@Dao
public interface ReviewDao {

  @Insert
  long insert(Review review);

  @Update
  int update(Review... reviews);

  @Delete
  int delete(Review... reviews);


}