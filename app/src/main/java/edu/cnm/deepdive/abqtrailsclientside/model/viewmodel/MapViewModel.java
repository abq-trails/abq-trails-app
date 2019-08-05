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

/**
 * Gets coordinates of trails.
 */
public class MapViewModel extends AndroidViewModel implements LifecycleObserver {

    private MutableLiveData<List<Trail>> mapCoordinates;
    private CompositeDisposable pending = new CompositeDisposable();

    /**
     * Initializes this instance with the specified {@link Application}
     */
    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Adds result of search to database??
     * @param term term inputted by ??
     * @return result of search
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

    /**
     * Disposes obsolete thread references when activity?? stops.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void disposePending() {
        pending.clear();
    }
}