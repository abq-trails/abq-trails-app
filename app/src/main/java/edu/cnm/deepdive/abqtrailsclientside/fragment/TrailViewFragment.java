/*
Copyright 2019 Denelle Britton Linebarger, Alana Chigbrow, Anita Martin, David Nelson

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

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
import edu.cnm.deepdive.abqtrailsclientside.R;
import edu.cnm.deepdive.abqtrailsclientside.model.database.TrailsDatabase;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import edu.cnm.deepdive.abqtrailsclientside.model.viewmodel.TrailViewModel;

/**
 * Uses service class to provide access to the database.
 */
public class TrailViewFragment extends Fragment {


  private Context context;

  private TrailViewModel viewModel;

  /**
   * Creates a new instance of the TrailViewFragment fragment.
   * @return TrailViewFragment fragment.
   */
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

    new Thread(() -> {
      TrailsDatabase db = TrailsDatabase.getInstance(getContext());
      View view = getView();

      final TrailViewModel viewModel = ViewModelProviders.of(this).get(TrailViewModel.class);
      viewModel.getTrails().observe(this, trails -> {

        final ArrayAdapter<Trail> adapter = new ArrayAdapter<>(context,
            android.R.layout.simple_list_item_1, trails);

        assert view != null;

        ListView ratingsListView = view.findViewById(R.id.ratings_cards);
        ratingsListView.setAdapter(adapter);

        ImageView horse = (db.trailDao().findByCabqIdSynchronous(1L).isHorse()) ?
            view.findViewById(R.id.horse_marker_black) : view.findViewById(R.id.horse_marker_grey);

        ImageView bike = (db.trailDao().findByCabqIdSynchronous(1L).isBike()) ?
            view.findViewById(R.id.bicycle_marker_black)
            : view.findViewById(R.id.bicycle_marker_grey);
      });

      Button ratingsButton = view.findViewById(R.id.add_rating_button);

    }).start();
  }

  //use reactivex, return object pulled from the db in a callback
  //then throw it back on the UI thread

}


