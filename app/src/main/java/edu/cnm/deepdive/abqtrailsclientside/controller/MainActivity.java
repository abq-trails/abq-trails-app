package edu.cnm.deepdive.abqtrailsclientside.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import edu.cnm.deepdive.abqtrailsclientside.R;
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean handled = true;
        switch (item.getItemId()) {
            case R.id.action_maps:
                Intent intent = new Intent(this, MapActivity.class);
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
