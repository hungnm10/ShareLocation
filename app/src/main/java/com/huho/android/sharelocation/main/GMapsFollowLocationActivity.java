package com.huho.android.sharelocation.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.huho.android.sharelocation.SharedPreferencesManager;
import com.huho.android.sharelocation.asyntask.DownloadImageAsync;
import com.huho.android.sharelocation.asyntask.GetMemberLocationAsync;
import com.huho.android.sharelocation.db.ShareLocationDBControll;
import com.huho.android.sharelocation.interfaces.IAsynTaskDelegate;
import com.huho.android.sharelocation.utils.common.SomeDialog;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.huho.android.sharelocation.R;
import com.huho.android.sharelocation.utils.common.Utils;
import com.huho.android.sharelocation.utils.objects.Member;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public class GMapsFollowLocationActivity extends ActionBarActivity implements
        OnMapReadyCallback, ConnectionCallbacks, LocationListener, IAsynTaskDelegate {

    // =========================================================================
    // Properties
    // =========================================================================

    private static final String TAG = "FollowLocationActivity";
    private static Context mContext;
    private boolean isFirstMessage = true;
    private MenuItem mFollowButton;
    private static ConcurrentHashMap<String, Member> memberList;
    // Google Maps
    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private MarkerOptions mMarkerOptions;
    private LatLng mLatLng;
    private String channelName;
    private GoogleApiClient mGoogleApiClient;
    private FragmentManager fManager;
    private ArrayList<String> listMemberName;
    private ListPopupWindow popup;
    private ArrayAdapter<String> adapter;
    private String memFollow = "None";

    // =========================================================================
    // Activity Life Cycle
    // =========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmaps_view);
        mContext = getApplicationContext();
        ShareLocationDBControll dbControll = new ShareLocationDBControll(mContext);
        // Get Channel Name
        Intent intent = getIntent();
        channelName = intent.getExtras().getInt("channel", -1) + "";
        fManager = this.getSupportFragmentManager();
        Log.d(TAG, "Passed Channel Name: " + channelName);
        this.buildGoogleApiClient();
        // Set up View: Map & Action Bar
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageView follow_button = (ImageView) findViewById(R.id.follow_button);
        listMemberName = new ArrayList<String>();
        listMemberName.add("None");
        adapter = new ArrayAdapter<String>(this, R.layout.simple_dropdown_listitem, listMemberName);
        popup = new ListPopupWindow(this, null, android.R.attr.actionDropDownStyle);
        popup.setAdapter(adapter);
        popup.setAnchorView(follow_button);
        popup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                memFollow = listMemberName.get(position);
                Toast.makeText(getApplicationContext(), "Follow " + memFollow, Toast.LENGTH_SHORT).show();
                popup.dismiss();
            }
        });
        follow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopFollowingLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.follow, menu);
        mFollowButton = menu.findItem(R.id.follow_locations);
        return true;
    }

    // =========================================================================
    // Map CallBacks
    // =========================================================================

    @Override
    public void onMapReady(GoogleMap map) {
        mGoogleMap = map;
        mGoogleMap.setMyLocationEnabled(true);
        Log.d(TAG, "Map Ready");
        startFollowingLocation();
    }

    // =========================================================================
    // Button CallBacks
    // =========================================================================

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void startFollowingLocation() {
        mGoogleApiClient.connect();
        initializePolyline();
    }

    private void stopFollowingLocation() {
        stopLocationUpdates();
        isFirstMessage = true;
    }

    // =========================================================================
    // Map Editing Methods
    // =========================================================================

    private void initializePolyline() {
        mGoogleMap.clear();
        memberList = new ConcurrentHashMap<>();
        mMarkerOptions = new MarkerOptions();
    }

    private void updatePolyline(PolylineOptions polylineOptions) {
        mGoogleMap.addPolyline(polylineOptions);
    }

    private void updateCamera(LatLng latLng) {
        mGoogleMap
                .animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, SharedPreferencesManager.getInstance().getZoomLevel()));
    }

    private void updateMarker(Member member) {
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        TextView numTxt = (TextView) marker.findViewById(R.id.num_txt);
        numTxt.setText(String.valueOf(member.getmName()));
        mMarker = member.getMarker(this, mGoogleMap);
        mMarker.showInfoWindow();
    }

    @Override
    public void didSuccessWithMessage(String message) {

    }

    @Override
    public void didFailWithMessage(String message) {
        SomeDialog dialog = new SomeDialog("Error", message, "OKay",
                "", null);
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void didSuccessWithJsonArray(JSONArray jsonArray) {
        Log.d(TAG, "jsonArray = " + jsonArray.toString());
        Member member = null;
        LatLng mLatLng;
        PolylineOptions mPolyline;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                String hash = "";
                double mLat = object.getDouble("lat");
                double mLng = object.getDouble("lon");
                String name = object.getString("name");
                String email = object.getString("email");
                String urlImage = object.getString("image");
                hash = object.getString("hash");
                mLatLng = new LatLng(mLat, mLng);
                member = memberList.get(email);
                if (member != null) {
                    member.setmLatLng(mLatLng);
                } else {
                    member = ShareLocationDBControll.getMemberFromDB(email);
                    if (member == null) {
                        member = new Member();
                        member.setmName(name);
                        member.setmEmail(email);
                        member.setmChannelName(channelName);
                        ShareLocationDBControll.insertOrUpdateMember(member);
                    }
                    member.setmLatLng(mLatLng);
                }

                if (!hash.equals(member.getmHashImg())) {
                    member.setmHashImg(hash);
                    member.setmUrlImage(urlImage);
                    UpdateImageMember(member);
                }
                memberList.put(email, member);
                if (!listMemberName.contains(member.getmName())) {
                    listMemberName.add(member.getmName());
                    adapter.notifyDataSetChanged();
                }
                final Member finalMember = member;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (memFollow.equals(finalMember.getmName()))
                            updateCamera(finalMember.getmLatLng());
                        updateMarker(finalMember);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location Detected");
        mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        if(isFirstMessage){
            updateCamera(mLatLng);
            isFirstMessage = false;
        }
        String email = SharedPreferencesManager.getInstance().getUserEmail();
        String pass = SharedPreferencesManager.getInstance().getUserPassword();

        Member member = new Member();
        member.setmChannelName(channelName);
        member.setmLatLng(mLatLng);
        String resultTest = null;
        new GetMemberLocationAsync(fManager, this).execute(member);
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this).addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Connected to Google API for Location Management");
        LocationRequest mLocationRequest = createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private LocationRequest createLocationRequest() {
        Log.d(TAG, "Building request");
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    public void UpdateImageMember(final Member m) {
        String url = m.getmUrlImage();

        new DownloadImageAsync(new IAsynTaskDelegate() {
            @Override
            public void didSuccessWithMessage(String message) {
                Member newMember = m;
                newMember.setmUrlImage(message);
                Toast.makeText(getApplicationContext(),newMember.getmEmail() + message, Toast.LENGTH_SHORT).show();
                ShareLocationDBControll.insertOrUpdateMember(newMember);
            }

            @Override
            public void didFailWithMessage(String message) {

            }

            @Override
            public void didSuccessWithJsonArray(JSONArray jsonArray) {

            }
        }).execute(url);
    }
}
