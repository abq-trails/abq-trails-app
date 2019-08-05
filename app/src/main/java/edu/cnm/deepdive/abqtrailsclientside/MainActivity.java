package edu.cnm.deepdive.abqtrailsclientside;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import edu.cnm.deepdive.abqtrailsclientside.fragment.UserRatingFragment;
import edu.cnm.deepdive.abqtrailsclientside.service.GoogleSignInService;

/**
 * Manages navigation between activities and fragments??
 */
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean handled = true;
        switch (item.getItemId()) {
            case R.id.action_maps:
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_reviews:
                FragmentManager manager = getSupportFragmentManager();
                Fragment fragment = UserRatingFragment.newInstance();
                String tag = fragment.getClass().getSimpleName() + "";
                if (manager.findFragmentByTag(tag) != null) {
                    manager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container, fragment, tag);
                transaction.addToBackStack(tag);
                transaction.commit();
                break;
            case R.id.action_signout:
                signOut();
                break;
            default:
                handled = super.onOptionsItemSelected(item);
        }
        return handled;
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
