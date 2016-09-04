
package com.huho.android.sharelocation.main.gmaps;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.huho.android.sharelocation.R;
import com.huho.android.sharelocation.utils.common.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks {

    private static final String EDITTEXT_HINT_LEFT_PADDING = "      ";

    private GoogleMap mMap;

    private LatLng mLatLng;

    private String mAddress;

    private GoogleApiClient mGoogleApiClient;

    private boolean isSetCurrentLocation;

    @InjectView(R.id.clear_icon)
    public ImageView mClearIcon;

    @Optional
    @InjectView(R.id.search_icon)
    public ImageView mSearchIcon;

    @InjectView(R.id.current_btn_area)
    public LinearLayout mImageBtnCurrent;

    @InjectView(R.id.location_sources_current_location_edit_text)
    public EditText mEditText;

    @InjectView(R.id.custom_cancel_button)
    public Button mCancelBtn;

    @InjectView(R.id.custom_done_button)
    public Button mDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.inject(this);
        this.buildGoogleApiClient();
        isSetCurrentLocation = true;
        // Obtain the SupportMapFragment and get notified when the map is ready
        // to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Utils.stringIsNullOrEmpty(s.toString())) {
                    mSearchIcon.setVisibility(View.GONE);
                    mClearIcon.setVisibility(View.VISIBLE);
                    mDone.setEnabled(true);
                } else {
                    mSearchIcon.setVisibility(View.VISIBLE);
                    mClearIcon.setVisibility(View.GONE);
                    mCancelBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addApi(LocationServices.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Double lat = latLng.latitude;
                Double lng = latLng.longitude;
                getAddress(lat, lng);
                drawPin(lat, lng);
            }
        });
        mGoogleApiClient.connect();
    }

    private void drawPin(double markerLatitude, double markerLongitude) {
        if (mMap == null) {
            return;
        }

        mMap.clear();

        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(markerLatitude, markerLongitude));
        marker.visible(true);
        mMap.addMarker(marker);

        CameraUpdate cameraUpdate;

        cameraUpdate = CameraUpdateFactory
                .newLatLngZoom(new LatLng(markerLatitude, markerLongitude), 14.0f);

        mMap.animateCamera(cameraUpdate);
    }

    public void getAddress(final double lat, final double lng) {
        mLatLng = new LatLng(lat, lng);
        final Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        new AsyncTask<Geocoder, Void, List<Address>>() {
            @Override
            protected List<Address> doInBackground(Geocoder... params) {
                List<Address> addr = null;
                try {
                    addr = geocoder.getFromLocation(lat, lng, 1);
                } catch (IOException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
                return addr;
            }

            @Override
            protected void onPostExecute(List<Address> result) {
                if (result != null) {
                    List<Address> Address = result;
                    if (!Address.isEmpty()) {
                        Address addr = Address.get(0);
                        if (addr != null) {
                            int addressLines = addr.getMaxAddressLineIndex();
                            for (int i = 0; i <= addressLines; i++) {
                                String addressLine = addr.getAddressLine(i);
                                if (!TextUtils.isEmpty(addressLine)) {
                                    if (i == 0)
                                        mAddress = addressLine;
                                    else
                                        mAddress += ", " + addressLine;
                                }
                            }
                        }
                        mEditText.setText(mAddress);
                        isSetCurrentLocation = false;
                    }

                }
                super.onPostExecute(result);
            }
        }.execute(geocoder);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (isSetCurrentLocation && location != null) {
            Double lat = location.getLatitude();
            Double lng = location.getLongitude();
            getAddress(lat, lng);
            drawPin(lat, lng);
        } else {

        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest mLocationRequest = createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,
                this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @OnClick(R.id.current_btn_area)
    public void getCurrentLocation() {
        isSetCurrentLocation = true;
        mEditText.setText("");
        mEditText.setHint(EDITTEXT_HINT_LEFT_PADDING + "Searching");
        mSearchIcon.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.location_sources_current_location_edit_text)
    public void clearQuerry() {
        mEditText.setText("");
        mEditText.setHint(EDITTEXT_HINT_LEFT_PADDING + "Search");
        mSearchIcon.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.custom_cancel_button)
    public void cancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick(R.id.custom_done_button)
    public void done(){
        Intent result = new Intent();
        result.putExtra("Address",mAddress);
        result.putExtra("lat",mLatLng.latitude);
        result.putExtra("lng",mLatLng.longitude);
        setResult(RESULT_OK,result);
        finish();
    }
}
