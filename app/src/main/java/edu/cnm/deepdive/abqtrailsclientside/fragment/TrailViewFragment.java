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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import edu.cnm.deepdive.abqtrailsclientside.R;
import edu.cnm.deepdive.abqtrailsclientside.model.database.TrailsDatabase;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import edu.cnm.deepdive.abqtrailsclientside.model.viewmodel.TrailViewModel;

public class TrailViewFragment extends Fragment {


  private boolean horse;
  private boolean bike;
  private Context context;
  private TrailViewModel viewModel;
  private long cabqId;

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
    final View view = inflater.inflate(R.layout.fragment_trail_view, container, false);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel = ViewModelProviders.of(this).get(TrailViewModel.class);
    View view = getView();
    final TrailViewModel viewModel = ViewModelProviders.of(this).get(TrailViewModel.class);
    new Thread(() -> {
      TrailsDatabase db = TrailsDatabase.getInstance(getContext());
        horse = (db.trailDao().findByIdSynchronous(1L).isHorse());
        bike = (db.trailDao().findByIdSynchronous(1L).isBike());

    }).start();

    viewModel.getTrails().observe(this, trails -> {
      final ArrayAdapter<Trail> adapter = new ArrayAdapter<>(context,
          android.R.layout.simple_list_item_1, trails);
      assert view != null;

      ListView ratingsListView = view.findViewById(R.id.ratings_list);
      ratingsListView.setAdapter(adapter);
      //FIXME Get this to pull a LiveData list, and then throw into a viewable list, of all ratings
      // for the trail in question.

      ImageView horse = view.findViewById(R.id.horse_marker_black);
      if (!this.horse) {
        horse.setAlpha(0.5f);
      }
      ImageView bike = view.findViewById(R.id.bicycle_marker_black);
      if(!this.bike) {
        bike.setAlpha(0.5f);
      }
      Button ratingsButton = view.findViewById(R.id.add_rating_button);
      ratingsButton.setOnClickListener( (trail) ->{
        UserRatingFragment ratingFragment = new UserRatingFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
            .replace(((ViewGroup)getView().getParent()).getId(), ratingFragment)
            .addToBackStack(null)
            .commit();

        //FIXME Get this to send the trail's long id (or whichever id we end up using when we get
        // this app finally talking to itself) to the rating fragment so that we can send a rating
        // about the right trail to the server.
      });



    });
  }

  //TODO Check if it is wiser to use reactivex, return object
  // pulled from the db in a callback and then throw it back on the UI thread

}


