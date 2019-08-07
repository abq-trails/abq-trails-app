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
import edu.cnm.deepdive.abqtrailsclientside.R;

/**
 * Provides user with the ability to rate trails and leave reviews of trails.
 */
public class UserRatingFragment extends Fragment {

  private RatingBar ratingBar;
  /**
   * Stars for users to rate trail
   */
  private TextView ratingScale;
  /**
   * Users rate trails on a scale if 1-5
   */
  private EditText feedback;
  /**
   * Comment box for users to leave a comment.
   */
  private Button sendFeedback; /** Button for submitting feedback. */

  /**
   * Creates a new instance of the UserRatingFragment fragment.
   * @return UserRatingFragment fragment.
   */
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

    sendFeedback = view.findViewById(R.id.submit_button); /**     */

    /**
     *  Listens for rating change events.
     */
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


