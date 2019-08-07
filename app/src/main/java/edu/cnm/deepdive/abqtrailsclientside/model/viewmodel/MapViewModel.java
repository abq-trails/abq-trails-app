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

package edu.cnm.deepdive.abqtrailsclientside.model.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import edu.cnm.deepdive.abqtrailsclientside.model.dao.TrailDao;
import edu.cnm.deepdive.abqtrailsclientside.model.database.TrailsDatabase;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import java.util.List;

/**
 * Gets coordinates of trails.
 */
public class MapViewModel extends AndroidViewModel implements LifecycleObserver {

  private LiveData<List<Trail>> searchResult;
  private MutableLiveData<String> searchTerm = new MutableLiveData<>();
  private TrailDao dao;

  /**
   * Initializes this instance with the specified {@link Application}
   */
  public MapViewModel(@NonNull Application application) {
    super(application);
    dao = TrailsDatabase.getInstance(application).trailDao();
    searchResult = Transformations.switchMap(searchTerm, (searchFrag) -> dao.search(searchFrag));
  }

  /**
   * Returns a list of trails.
   */
  public LiveData<List<Trail>> getAllTrails() {
    return dao.getAll();
  }

  /**
   * Sets searchTerm.
   */
  public void setSearchTerm(String term) {
    this.searchTerm.setValue(term);
  }

  /**
   * Returns search result for a list of trails.
   */
  public LiveData<List<Trail>> getSearchResult() {
    return searchResult;
  }
//    public Trail getTrailByCabqId (long id) {
//       return dao.findByCabqIdSynchronous(id);
//    }
}