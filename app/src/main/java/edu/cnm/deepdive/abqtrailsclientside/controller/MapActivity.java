package edu.cnm.deepdive.abqtrailsclientside.controller;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonLineString;
import com.google.maps.android.data.geojson.GeoJsonLineStringStyle;
import com.google.maps.android.data.geojson.GeoJsonMultiLineString;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;
import edu.cnm.deepdive.abqtrailsclientside.R;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import edu.cnm.deepdive.abqtrailsclientside.model.viewmodel.MapViewModel;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

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
    GeoJsonLineStringStyle lineStringStyle = new GeoJsonLineStringStyle();
    lineStringStyle.setColor(Color.rgb(34, 139, 34));
    lineStringStyle.setLineStringWidth(6);
    try {
      if (layer != null && map != null) {
        layer.removeLayerFromMap();
      }
      layer = new GeoJsonLayer(map, R.raw.empty_geojson, this);
      for (Trail trail : trails) {
        Map<String, Object> geometry = gson.fromJson(trail.getCoordinates(),
            new TypeToken<Map<String, Object>>() {
            }.getType());
        String type = (String) geometry.get("type");
        Object rawCoordinates = geometry.get("coordinates");
        Log.d(type, rawCoordinates.toString());
        if (type.equals("LineString")) {
          LatLng trailHead = null;
          List<List<Double>> coordinates = (List<List<Double>>) rawCoordinates;
          List<LatLng> coordList = new LinkedList<>();
          for (List<Double> pair : coordinates) {
            LatLng point = new LatLng(pair.get(1), pair.get(0));
            if (trailHead == null) {
              trailHead = point;
            }
            coordList.add(point);
          }
          GeoJsonFeature feature = new GeoJsonFeature(new GeoJsonLineString(coordList),
              trail.getName(), null, null);
//          GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
//          pointStyle.setTitle(trail.getName());
//          feature.setPointStyle(pointStyle);
          feature.setLineStringStyle(lineStringStyle);
          layer.addFeature(feature);
          map.addMarker(new MarkerOptions().position(trailHead)
              .title(trail.getName())
              .snippet("")
              .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        } else if (type.equals("MultiLineString")) {
          LatLng trailHead = null;
          List<List<List<Double>>> coordinates = (List<List<List<Double>>>) rawCoordinates;
          List<GeoJsonLineString> lineList = new LinkedList<>();
          for (List<List<Double>> line : coordinates) {
            List<LatLng> coordList = new LinkedList<>();
            for (List<Double> pair : line) {
              LatLng point = new LatLng(pair.get(1), pair.get(0));
              if (trailHead == null) {
                trailHead = point;
              }
              coordList.add(point);
            }
            lineList.add(new GeoJsonLineString(coordList));
          }
          GeoJsonFeature feature = new GeoJsonFeature(new GeoJsonMultiLineString(lineList),
              trail.getName(), null, null);
//          GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
//          pointStyle.setTitle(trail.getName());
//          feature.setPointStyle(pointStyle);
          feature.setLineStringStyle(lineStringStyle);
          layer.addFeature(feature);
          map.addMarker(new MarkerOptions().position(trailHead)
              .title(trail.getName())
              .snippet("")
              .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
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