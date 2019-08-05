package edu.cnm.deepdive.abqtrailsclientside.model.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import edu.cnm.deepdive.abqtrailsclientside.service.AbqTrailsService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.util.LinkedList;
import java.util.List;

public class MapViewModel extends AndroidViewModel implements LifecycleObserver {

    private MutableLiveData<List<Trail>> trails;
    private CompositeDisposable pending = new CompositeDisposable();

    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Trail>> searchTrails(String term) {
        if (trails == null) {
            trails = new MutableLiveData<>();
        }
        if (term != null) {
            if (term.trim().isEmpty()) {
                term = "";
            }
            pending.add(
                    AbqTrailsService.getInstance().searchByName(term)
                            .subscribeOn(Schedulers.io())
                            .subscribe((result) -> {
                                this.trails.postValue(result);
                            }));
        } else {
            trails.setValue(new LinkedList<>());
        }
        return trails;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void disposePending() {
        pending.clear();
    }
}