package edu.cnm.deepdive.abqtrailsclientside;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.cnm.deepdive.abqtrailsclientside.fragment.UserRatingFragment;
import edu.cnm.deepdive.abqtrailsclientside.service.GoogleSignInService;

//David Nelson put this here to commit.
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        }
        if (id == R.id.action_reviews) {
            Intent intent = new Intent(this, );
            startActivity(intent);
        }
//        if (id == R.id.action_upload_profile) {
//            Intent intent = new Intent(this, );
//            startActivity(intent);
//        }
//        if (id == R.id.action_user_profile) {
//            Intent intent = new Intent(this, );
//            startActivity(intent);
//        }
//        if (id == R.id.action_settings) {
//            Intent intent = new Intent(this, );
//            startActivity(intent);
//        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        GoogleSignInService service = GoogleSignInService.getInstance();
        service.getClient().signOut().addOnCompleteListener((task) -> {
            service.setAccount(null);
            Intent intent = new Intent(this, GoogleSignInService.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

}
