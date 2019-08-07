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
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import java.util.List;

/**
 * Gets coordinates of trails.
 */
public class MapViewModel extends AndroidViewModel implements LifecycleObserver {

  private LiveData<List<Trail>> searchResult;
  private MutableLiveData<String> searchTerm = new MutableLiveData<>();
  private TrailDao dao;

  public MapViewModel(@NonNull Application application) {
    super(application);
    dao = TrailsDatabase.getInstance(application).trailDao();
    searchResult = Transformations.switchMap(searchTerm, (searchFrag) -> dao.search(searchFrag));
  }
  /**
   * Initializes this instance with the specified {@link Application}
   */
  public MapViewModel(@NonNull Application application) {
    super(application);
  }

  public LiveData<List<Trail>> getAllTrails() {
    return dao.getAll();
  }

  public void setSearchTerm(String term) {
    this.searchTerm.setValue(term);
  }
  /**
   * Returns a list of trails searched for by user.
   *
   * @param term term inputted by user.
   * @return a list of trails.
   */
  public LiveData<List<Trail>> getCoordinates(String term) {
    if (mapCoordinates == null) {
      mapCoordinates = new MutableLiveData<>();
    }
    if (term != null) {
      if (term.trim().isEmpty()) {
        term = "coordinates";
      }
      pending.add(
          AbqTrailsService.getInstance().searchById(term)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe((searchCoordinates) -> {
                // List<Trail> mapping = AbqTrailsService.getInstance().searchByCoordinates("q");
                List<Trail> trails = new LinkedList<>();
                mapCoordinates.setValue(trails);
              }));
    } else {
      mapCoordinates.setValue(new LinkedList<>());
    }
    return mapCoordinates;
  }

  public LiveData<List<Trail>> getSearchResult() {
    return searchResult;
  }

//    public Trail getTrailByCabqId (long id) {
//       return dao.findByCabqIdSynchronous(id);
//    }
  /**
   * Disposes obsolete thread references when activity?? stops.
   */
  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  public void disposePending() {
    pending.clear();
  }
}