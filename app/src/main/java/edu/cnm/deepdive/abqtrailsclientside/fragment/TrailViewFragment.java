package edu.cnm.deepdive.abqtrailsclientside.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import edu.cnm.deepdive.abqtrailsclientside.R;
import edu.cnm.deepdive.abqtrailsclientside.model.dao.TrailDao;
import edu.cnm.deepdive.abqtrailsclientside.model.database.TrailsDatabase;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import edu.cnm.deepdive.abqtrailsclientside.model.viewmodel.TrailViewModel;

public class TrailViewFragment extends Fragment {

  private Context context;

  private TrailViewModel viewModel;

  private long trailId = 2L;

  private TrailsDatabase db = new TrailsDatabase() {
    @Override
    public TrailDao trailDao() {
      return null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
      return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
      return null;
    }

    @Override
    public void clearAllTables() {

    }
  };

  public static TrailViewFragment newInstance() {
    return new TrailViewFragment();
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    this.context = context;

  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    long trailId = getArguments() != null ? getArguments().getLong("trailId") : 0;
    final View view = inflater.inflate(R.layout.fragment_trail_view, container, false);
    final TrailViewModel viewModel = ViewModelProviders.of(this).get(TrailViewModel.class);
    viewModel.getTrails().observe(this, trails -> {
      final ArrayAdapter<Trail> adapter = new ArrayAdapter<>(context,
          android.R.layout.simple_list_item_1, trails);
      ListView ratingsListView = view.findViewById(R.id.ratings_cards);
      ratingsListView.setAdapter(adapter);
    });
    Button ratingsButton = view.findViewById(R.id.add_rating_button);
    viewModel.getTrails().observe(this, trails -> {
      ImageView horse = (db.trailDao().findById(trailId).isHorse()) ?
          view.findViewById(R.id.horse_marker_black) : view.findViewById(R.id.horse_marker_grey);

      ImageView bike = (db.trailDao().findById(trailId).isBike()) ?
          view.findViewById(R.id.bicycle_marker_black)
          : view.findViewById(R.id.bicycle_marker_grey);

      ImageView dog = (db.trailDao().findById(trailId).isDog()) ?
          view.findViewById(R.id.dog_marker_black) : view.findViewById(R.id.dog_marker_grey);


    });
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel = ViewModelProviders.of(this).get(TrailViewModel.class);
    // TODO: Use the ViewModel
  }

}
