package edu.cnm.deepdive.abqtrailsclientside.controller;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonGeometryCollection;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonLineString;
import com.google.maps.android.data.geojson.GeoJsonMultiLineString;
import edu.cnm.deepdive.abqtrailsclientside.R;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import edu.cnm.deepdive.abqtrailsclientside.model.viewmodel.MapViewModel;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

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
  private GoogleMap map;
  private GeoJsonLayer layer;
  private Gson gson;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    gson = new Gson();
    setContentView(R.layout.activity_map);
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }


  @Override
  public void onMapReady(GoogleMap googleMap) {
    map = googleMap;
    setupViewModel();
    // Default on opening when maps is clicked
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(35.0844, -106.6504));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.0844, -106.6504), 10));
        googleMap.addMarker(marker);
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
    viewModel.getAllTrails().observe(this, (trails) -> {
      addTrailsToMap(trails);
    });
    viewModel.getSearchResult().observe(this, (trails) -> {
      addTrailsToMap(trails);
    });
  }

  private void addTrailsToMap(List<Trail> trails) {
    try {
      if (layer != null && map != null) {
        layer.removeLayerFromMap();
      }
      layer = new GeoJsonLayer(map, R.raw.empty_geojson, this);
      for (Trail trail : trails) {
        Map<String, Object> geometry = gson.fromJson(trail.getCoordinates(),
            new TypeToken<Map<String, Object>>() {}.getType());
        String type = (String) geometry.get("type");
        Object rawCoordinates = geometry.get("coordinates");
        Log.d(type, rawCoordinates.toString());
        if (type.equals("LineString")) {
          List<List<Double>> coordinates = (List<List<Double>>) rawCoordinates;
          List<LatLng> coordList = new LinkedList<>();
          for (List<Double> pair : coordinates) {
            coordList.add(new LatLng(pair.get(1), pair.get(0)));
          }
          GeoJsonFeature feature = new GeoJsonFeature(new GeoJsonLineString(coordList), trail.getName(), null, null);
          layer.addFeature(feature);
        } else if (type.equals("MultiLineString")) {
          List<List<List<Double>>> coordinates = (List<List<List<Double>>>) rawCoordinates;
          List<GeoJsonLineString> lineList = new LinkedList<>();
          for (List<List<Double>> line : coordinates) {
            List<LatLng> coordList = new LinkedList<>();
            for (List<Double> pair : line) {
              coordList.add(new LatLng(pair.get(1), pair.get(0)));
            }
            lineList.add(new GeoJsonLineString(coordList));
          }
          GeoJsonFeature feature = new GeoJsonFeature(new GeoJsonMultiLineString(lineList), trail.getName(), null, null);
          layer.addFeature(feature);
        }
      }
      layer.addLayerToMap();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
}