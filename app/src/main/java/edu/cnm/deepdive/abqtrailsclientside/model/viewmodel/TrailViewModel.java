package edu.cnm.deepdive.abqtrailsclientside.model.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.abqtrailsclientside.model.database.TrailsDatabase;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import java.util.List;

/**
 * Gets list of trails.
 */
public class TrailViewModel extends AndroidViewModel {

  private LiveData<List<Trail>> trails;

  /**
   * Initializes this instance with the specified {@link Application}
   */
  public TrailViewModel(@NonNull Application application) {
    super(application);
    TrailsDatabase db = TrailsDatabase.getInstance(application);
    trails = db.trailDao().getAll();
  }

  /**
   * Returns list of trails.
   * @return list of trails
   */
  public LiveData<List<Trail>> getTrails() {
    return trails;
  }
}