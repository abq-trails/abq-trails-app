package edu.cnm.deepdive.abqtrailsclientside.model.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import edu.cnm.deepdive.abqtrailsclientside.service.AbqTrailsService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.util.LinkedList;
import java.util.List;

public class MapViewModel extends AndroidViewModel implements LifecycleObserver {

    private MutableLiveData<List<Trail>> mapCoordinates;
    private CompositeDisposable pending = new CompositeDisposable();

    public MapViewModel(@NonNull Application application) {
        super(application);
    }

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
                                List<Trail> mapping = AbqTrailsService.getInstance().searchByCoordinates("q");
                                List<Trail> trails = new LinkedList<>();
                                mapCoordinates.setValue(trails);
                            }));
        } else {
            mapCoordinates.setValue(new LinkedList<>());
        }
        return mapCoordinates;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void disposePending() {
        pending.clear();
    }
}