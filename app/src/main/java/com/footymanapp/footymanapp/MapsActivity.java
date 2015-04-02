package com.footymanapp.footymanapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;

public class MapsActivity extends FragmentActivity implements LocationListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener
{
    private GoogleMap mMap;// Might be null if Google Play services APK is not available
    LatLng markerPos;
    static double latitude;
    static double longitude;
    /*public void onLocationChanged(Location location)
    {
        if (mMap != null)
        {
            drawMarker(location);
        }
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener((GoogleMap.OnMapLongClickListener) this);
        mMap.setOnMarkerDragListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();
    }
    /*private void drawMarker(Location location)
    {
        mMap.clear();
        //  convert the location object to a LatLng object that can be used by the map API
        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        // zoom to the current location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 16));
        // add a marker to the map indicating our current position
        mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("Lat:" + location.getLatitude() + "Lng:"+ location.getLongitude()));
    }*/
    private void setUpMapIfNeeded()
    {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null)
        {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null)
            {
                setUpMap();
            }
        }
    }

    private void setUpMap()
    {
        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);
        showAlert();
        LocationManager locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Get the name of the best provider
        String provider = String.valueOf(locationManager.getBestProvider(criteria, true));
        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);
        //Create a LatLng object for the current location
        LatLng myCoordinates = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myCoordinates, 12);
        mMap.animateCamera(yourLocation);
        //Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates));

        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setIndoorEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

    }
    public void showAlert()
    {
        AlertDialog.Builder mapAlert = new AlertDialog.Builder(this);
        mapAlert.setMessage("Scroll to pitch location.\nHold to drop pin.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        mapAlert.show();
    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void onMapLongClick(LatLng markerPos)
    {
        mMap.addMarker(new MarkerOptions()
                .position(markerPos)
                .draggable(true));
        latitude = markerPos.latitude;
        longitude = markerPos.longitude;
    }
    @Override
    public void onMarkerDragStart(Marker marker){}

    @Override
    public void onMarkerDrag(Marker marker) {}

    //@Override
    public void onMarkerDragEnd(Marker marker)
    {
        markerPos = marker.getPosition();
        latitude = markerPos.latitude;
        longitude = markerPos.longitude;
    }
    public void buttonOnClickSaveLocation(View v)
    {
        Log.i("myTag2", "Your new position is " + latitude + ", " + longitude);
        setLatitude(latitude);
        super.onBackPressed();
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }
    public static double getLatitude()
    {
        return latitude;
    }
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }
    public static double getLongitude()
    {
        return longitude;
    }
    @Override
    public void onMapClick(LatLng latLng){}
}
