//package edu.cnm.deepdive.abqtrailsclientside.fragment;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//import edu.cnm.deepdive.abqtrailsclientside.R;
//import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
//import edu.cnm.deepdive.abqtrailsclientside.model.viewmodel.TrailViewModel;
//import java.util.List;
//
//public class TrailFragment extends Fragment {
//
//  private Context context;
//
// public TrailFragment() {
//   //Required empty public constructor.
// }
//
//  @Override
//  public void onAttach(@NonNull Context context) {
//    super.onAttach(context);
//    this.context = context;
//  }
//
//  @Override
//  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//   final View view= inflater.inflate(R.layout.fragment_trail, container, false);
//   final ListView trailsListView = view.findViewById(R.id.trails_list);
//   final TrailViewModel viewModel = ViewModelProviders.of(getActivity()).get(TrailViewModel.class);
//
//   viewModel.getTrails().observe(this, (Observer) (trailList) -> {
//     final ArrayAdapter adapter =
//         new ArrayAdapter<Trail>(context, android.R.layout.simple_list_item_1,
//             (List<Trail>) trailList);
//     trailsListView.setAdapter(adapter);
//   });
//
//   return view;
//
//  }
//
//}
