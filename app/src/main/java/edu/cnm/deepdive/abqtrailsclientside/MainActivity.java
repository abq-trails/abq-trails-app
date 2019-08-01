package edu.cnm.deepdive.abqtrailsclientside;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

//David Nelson put this here to commit.
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_maps) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
//    } else if (id == R.id.action_settings) {
//      // Hack needs to be removed.
//      FragmentManager manager = getSupportFragmentManager();
//      Fragment fragment = UserRatingFragment.newInstance();
//      String tag = fragment.getClass().getSimpleName() + "";
//      if (manager.findFragmentByTag(tag) != null) {
//        manager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//      }
//      FragmentTransaction transaction = manager.beginTransaction();
//      transaction.replace(R.id.container, fragment, tag);
//      transaction.addToBackStack(tag);
//      transaction.commit();
//      // End of hack.
      return true;
    }
    // TODO add back in when all is combined
//    if (id == R.id.action_reviews) {
//      Intent intent = new Intent(this, MapsActivity.class);
//      startActivity(intent);    }
//    if (id == R.id.action_upload_profile) {
//      Intent intent = new Intent(this, MapsActivity.class);
//      startActivity(intent);    }
//    if (id == R.id.action_user_profile) {
//      Intent intent = new Intent(this, MapsActivity.class);
//      startActivity(intent);    }
      return super.onOptionsItemSelected(item);

  }

}
