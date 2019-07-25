package edu.cnm.deepdive.abqtrailsclientside;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class UserRatingFragment extends Fragment {

  private RatingBar ratingBar;
  private TextView ratingScale;
  private EditText feedback;
  private Button sendFeedback;

  public static UserRatingFragment newInstance() {
    return new UserRatingFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    //Inflates the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_user_rating, container, false);
    ratingBar = view.findViewById(R.id.rating_bar);
    ratingScale = view.findViewById(R.id.rating_scale);
    feedback = view.findViewById(R.id.leave_feedback);
    ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        ratingScale.setText(String.valueOf(v));
        switch ((int) ratingBar.getRating()) {
          case 1:
            ratingScale.setText("Very bad.");
            break;
          case 2:
            ratingScale.setText("Needs some improvements");
            break;
          case 3:
            ratingScale.setText("Good");
            break;
          case 4:
            ratingScale.setText("Great");
            break;
          case 5:
            ratingScale.setText("Awesome");
            break;
          default:
            ratingScale.setText("");
        }
      }
    });

    sendFeedback = view.findViewById(R.id.submit_button);
    sendFeedback.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        //if (feedback.getText().toString().isEmpty()) {
        //  Toast.makeText(getContext(), "Please leave feedback.", Toast.LENGTH_SHORT).show();
        //} else {
          feedback.setText("");
          ratingBar.setRating(0);
          Toast.makeText(getContext(), "Thank you for sharing your feedback.", Toast.LENGTH_SHORT)
              .show();
        }
      });

    return view;
  }

}


