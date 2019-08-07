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
import edu.cnm.deepdive.abqtrailsclientside.model.dao.ReviewDao;
import edu.cnm.deepdive.abqtrailsclientside.model.dao.TrailDao;
import edu.cnm.deepdive.abqtrailsclientside.model.database.TrailsDatabase;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Review;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import edu.cnm.deepdive.abqtrailsclientside.service.AbqTrailsService;
import edu.cnm.deepdive.abqtrailsclientside.service.GoogleSignInService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class TrailViewModel extends AndroidViewModel implements LifecycleObserver {

  private MutableLiveData<List<Trail>> trails;
  private CompositeDisposable pending = new CompositeDisposable();
  private AbqTrailsService service;
  private TrailsDatabase db = TrailsDatabase.getInstance(getApplication());
  private TrailDao trailDao;
  private ReviewDao reviewDao;
  private String oauthHeader;

  public TrailViewModel(@NonNull Application application) {
    super(application);
    service = AbqTrailsService.getInstance();
    oauthHeader = String.format(BuildConfig.AUTHORIZATION_FORMAT,
        GoogleSignInService.getInstance().getAccount().getIdToken());
    db = TrailsDatabase.getInstance(application);
    trailDao = db.trailDao();
    reviewDao = db.reviewDao();

  }

  public LiveData<List<Trail>> getTrails() {
    if (trails == null) {
      trails = new MutableLiveData<>();
    }
    pending.add(
        service.listTrails()
            .subscribeOn(Schedulers.io())
            .subscribe((result) -> trails.postValue(result))
    );
    return trails;
  }

  @OnLifecycleEvent(Event.ON_STOP)
  private void deletePending() {
    pending.clear();
  }

  public void postReview(String message, int rating) {

    new Thread(() -> {
      Review review = new Review();
      review.setRating(rating);
      review.setReview(message);
      review.setCabqId(10);
      review.setUsername("test");
      reviewDao.insert(review);
//TODO FIGURE OUT WHY THE BELOW WILL NOT POST A REVIEW

//      pending.add(
//          service.create(review)
//              .subscribeOn(Schedulers.io())
//              .subscribe());
    }).start();

  }
}