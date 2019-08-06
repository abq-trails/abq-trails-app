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

public class MapViewModel extends AndroidViewModel implements LifecycleObserver {

  private LiveData<List<Trail>> searchResult;
  private MutableLiveData<String> searchTerm = new MutableLiveData<>();
  private TrailDao dao;

  public MapViewModel(@NonNull Application application) {
    super(application);
    dao = TrailsDatabase.getInstance(application).trailDao();
    searchResult = Transformations.switchMap(searchTerm, (searchFrag) -> dao.search(searchFrag));
  }

  public LiveData<List<Trail>> getAllTrails() {
    return dao.getAll();
  }

  public void setSearchTerm(String term) {
    this.searchTerm.setValue(term);
  }

  public LiveData<List<Trail>> getSearchResult() {
    return searchResult;
  }

//    public Trail getTrailByCabqId (long id) {
//       return dao.findByCabqIdSynchronous(id);
//    }
}