package edu.cnm.deepdive.abqtrailsclientside.model.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import edu.cnm.deepdive.abqtrailsclientside.model.database.TrailsDatabase;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import edu.cnm.deepdive.abqtrailsclientside.service.AbqTrailsService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class TrailViewModel extends AndroidViewModel implements LifecycleObserver {

  private MutableLiveData<List<Trail>> trails;
  private CompositeDisposable pending = new CompositeDisposable();
  private AbqTrailsService service;
//  private TrailsDatabase db = TrailsDatabase.getInstance(getApplication());

  public TrailViewModel(@NonNull Application application) {
    super(application);
    service = AbqTrailsService.getInstance();
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
}