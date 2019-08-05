package edu.cnm.deepdive.abqtrailsclientside.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import edu.cnm.deepdive.abqtrailsclientside.R;
import edu.cnm.deepdive.abqtrailsclientside.model.viewmodel.MapViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xffF9A825;
    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);
    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);
    private MapViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Default on opening when maps is clicked
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(35.0844, -106.6504));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.0844, -106.6504), 10));
        // Hard Coded in an example trail
        MarkerOptions marker2 = new MarkerOptions();
        marker2.position(new LatLng(35.16430139231649, -106.46370012666549));
        marker2.title("140a");
        marker2.snippet("Distance: blah blah ");
        marker2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        googleMap.addMarker(marker2);
        //Hard coded in some coordinates trail.
        Polygon polygon = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(35.16430139231649, -106.46370012666549),
                        new LatLng(35.16415681961822, -106.46402087050099),
                        new LatLng(35.16407902961823, -106.46422204050096),
                        new LatLng(35.16406830961823, -106.46424081050095),
                        new LatLng(35.16391005961822, -106.46474238050092),
                        new LatLng(35.16389932961822, -106.46475580050091),
                        new LatLng(35.16389127961822, -106.4647692105009),
                        new LatLng(35.16379710695103, -106.4648969014523)
                ));
        polygon.setTag("Trail 140a");
    }
    // Hard coded in some features to the the trail lines.
    private void stylePolygon(Polygon polygon) {
        String type = "";
        // Get the data object stored with the polygon.
        if (polygon.getTag() != null) {
            type = polygon.getTag().toString();
        }
        List<PatternItem> pattern = null;
        int strokeColor = COLOR_BLACK_ARGB;
        int fillColor = COLOR_WHITE_ARGB;

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "alpha":
                // Apply a stroke pattern to render a dashed line, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_GREEN_ARGB;
                fillColor = COLOR_PURPLE_ARGB;
                break;
            case "beta":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_BETA;
                strokeColor = COLOR_ORANGE_ARGB;
                fillColor = COLOR_BLUE_ARGB;
                break;
        }
        polygon.setStrokePattern(pattern);
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setStrokeColor(strokeColor);
        polygon.setFillColor(fillColor);
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        viewModel.searchTrails(null).observe(this, (trails) -> {

       });
    }

}