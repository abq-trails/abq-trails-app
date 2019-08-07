//Copyright 2019 Denelle Britton Linebarger, Alana Chigbrow, Anita Martin, David Nelson
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.

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


/**
 * Performs CRUD operations on {@link Trail} entity instances.
 */
@Dao
public interface TrailDao {

  /**
   * Inserts instance of {@link Trail} into database.
   *
   * @param trail instance of trail
   * @return instance of trail
   */
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  long insert(Trail trail);

  /**
   * Returns the {@link Trail} from the database.
   *
   * @return {@link LiveData}  list of all trails
   */
  @Query("SELECT * FROM Trail")
  LiveData<List<Trail>> getAll();

  /**
   * Returns the {@link Trail} specified by the <code>id</code> from the database.
   *
   * @param id {@link Trail} primary key value
   * @return trail instance.
   */
  @Query("SELECT * FROM Trail WHERE trail_id = :id")
  LiveData<Trail> findById(Long id);

  /**
   * Returns a list of trails using search ?
   *
   * @param searchString search term?
   * @return trail instance
   */
  @Query("SELECT * FROM Trail WHERE trail_name LIKE :searchString")
  LiveData<List<Trail>> search(String searchString);

  /**
   * Returns a trail using id.
   *
   * @param id of trail.
   * @return trail.
   */
  @Query("SELECT * FROM Trail WHERE trail_id = :id")
  Trail findByIdSynchronous(long id);

  /**
   * Returns a trail using cabqId.
   *
   * @param cabqId id for trail
   * @return trail.
   */
  @Query("SELECT * FROM Trail WHERE cabq_id = :cabqId")
  Trail findByCabqIdSynchronous(long cabqId);

  /**
   * Returns a trail using cabqId.
   *
   * @param cabqId id of trail.
   * @return trail.
   */
  @Query("SELECT * FROM Trail WHERE cabq_id = :cabqId")
  LiveData<Trail> findByCabqId(long cabqId);

  /**
   * Updates trail data.
   *
   * @param trails trails
   * @return updated trail data.
   */
  @Update
  int update(Trail... trails);


  //Only used in ABQTrailsApplication.java to start db.
  @Delete
  int delete(Trail... trail);


}
