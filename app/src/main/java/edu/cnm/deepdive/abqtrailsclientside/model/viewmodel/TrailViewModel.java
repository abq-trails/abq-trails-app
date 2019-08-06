package edu.cnm.deepdive.abqtrailsclientside.model.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.Transformations;
import edu.cnm.deepdive.abqtrailsclientside.BuildConfig;
import edu.cnm.deepdive.abqtrailsclientside.model.Review;
import edu.cnm.deepdive.abqtrailsclientside.model.dao.TrailDao;
import edu.cnm.deepdive.abqtrailsclientside.model.database.TrailsDatabase;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import edu.cnm.deepdive.abqtrailsclientside.service.AbqTrailsService;
import edu.cnm.deepdive.abqtrailsclientside.service.GoogleSignInService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class TrailViewModel extends AndroidViewModel implements LifecycleObserver {

  private MutableLiveData<Long> cabqId = new MutableLiveData<>();
  private LiveData<Trail> trail;
  private MutableLiveData<List<Review>> reviews;
  private CompositeDisposable pending = new CompositeDisposable();
  private AbqTrailsService service;
  private TrailsDatabase db;
  private TrailDao trailDao;
  private String oauthHeader;

  public TrailViewModel(@NonNull Application application) {
    super(application);
    service = AbqTrailsService.getInstance();
    oauthHeader = String.format(BuildConfig.AUTHORIZATION_FORMAT,
        GoogleSignInService.getInstance().getAccount().getIdToken());
    db = TrailsDatabase.getInstance(application);
    trailDao = db.trailDao();
    trail = Transformations.switchMap(cabqId, (id) -> trailDao.findById(id));

  }

  public void setCabqId(long cabqId) {
    this.cabqId.setValue(cabqId);
  }

  public LiveData<Trail> getTrail() {
    return trail;
  }

  public LiveData<List<Review>> getReviews(long cabqId) {
    if (reviews == null) {
      reviews = new MutableLiveData<>();
    }
    pending.add(
        service.searchByCabqId(cabqId)
            .subscribeOn(Schedulers.io())
            .subscribe((result) -> reviews.postValue(result))
    );
    return reviews;
  }

  @OnLifecycleEvent(Event.ON_STOP)
  private void deletePending() {
    pending.clear();
  }
}