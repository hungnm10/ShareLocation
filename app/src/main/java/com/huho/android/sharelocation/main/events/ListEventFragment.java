
package com.huho.android.sharelocation.main.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.huho.android.sharelocation.R;
import com.huho.android.sharelocation.adapters.RecyclerAdapter;
import com.huho.android.sharelocation.adapters.viewholder.RecyclerAdapterTouchHelper;
import com.huho.android.sharelocation.asyntask.GetPublicChannelAsync;
import com.huho.android.sharelocation.asyntask.JoinChannel;
import com.huho.android.sharelocation.interfaces.IAsynTaskDelegate;
import com.huho.android.sharelocation.main.BottomMainActivity;
import com.huho.android.sharelocation.main.GMapsFollowLocationActivity;
import com.huho.android.sharelocation.utils.common.HidingScrollListener;

import com.huho.android.sharelocation.utils.common.ProgressHUD;
import com.huho.android.sharelocation.utils.common.SomeDialog;
import com.huho.android.sharelocation.utils.objects.Channel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 1/30/2016.
 */
public class ListEventFragment extends Fragment implements IAsynTaskDelegate {
    private ArrayList<Channel> mChannelList;

    private static final String TAG = "ListEventFragment";

    private IAsynTaskDelegate mIAsynTaskDelegate;

    SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerAdapter mRecyclerAdapter;

    private ProgressHUD hud;

    private FragmentManager fManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mChannelList = new ArrayList<>();
        hud = new ProgressHUD(getActivity());
        fManager = getActivity().getSupportFragmentManager();
        mIAsynTaskDelegate = this;
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red,R.color.light_blue,R.color.color_primary_green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetPublicChannelAsync(null, fManager, mIAsynTaskDelegate).execute();
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerAdapter = new RecyclerAdapter(mChannelList);
        recyclerView.setAdapter(mRecyclerAdapter);
        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                ((BottomMainActivity) getActivity()).hideToolBarAndFBA();
                EventFragment.hideTabBar();
            }

            @Override
            public void onShow() {
                ((BottomMainActivity) getActivity()).showToolBarAndFBA();
                EventFragment.showTabBar();
            }
        });

        RecyclerAdapter.OnItemClickListener onItemClickListener = new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int channelid) {
                joinChannel(channelid);
            }
        };
        mRecyclerAdapter.setOnItemClickListener(onItemClickListener);

        ItemTouchHelper.Callback callback = new RecyclerAdapterTouchHelper(mRecyclerAdapter,
                getActivity().getApplicationContext(), onItemClickListener);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
        new GetPublicChannelAsync(hud, fManager, mIAsynTaskDelegate).execute();
        return rootView;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void didSuccessWithJsonArray(JSONArray jsonArray) {
        Log.d(TAG, "jsonArray = " + jsonArray.toString());
        Channel channel = null;
        LatLng latLngSrc, latLngDes;
        mChannelList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                Log.d(TAG, "jsonobject = " + object.toString());
                double latSrc = object.getDouble("lat_src");
                double lngSrc = object.getDouble("lon_src");
                double latDes = object.getDouble("lat_des");
                double lngDes = object.getDouble("lon_des");
                int channelId = object.getInt("id");
                String timeStart = object.getString("time");
                //String timeEnd = object.getString("time_end");
                String timeEnd = " ";
                String name = object.getString("name");
                String description = object.getString("description");
                int adminId = object.getInt("admin_id");
                int maxMember = object.getInt("max");
                int currentMember = object.getInt("curr_users");
                // Boolean isPrivate = object.getBoolean("is_private");

                latLngSrc = new LatLng(latSrc, lngSrc);
                latLngDes = new LatLng(latDes, lngDes);
                channel = new Channel(channelId, name, description, timeStart, timeEnd, adminId, latLngSrc,
                        latLngDes, maxMember, currentMember, false); // isPrivate not updated
                                                      // yet

                mChannelList.add(channel);
                mRecyclerAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void didSuccessWithMessage(String message) {

    }

    @Override
    public void didFailWithMessage(String message) {
        SomeDialog dialog = new SomeDialog("Error", message, "OKay", "", null);
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    public void joinChannel(final int id) {
        Toast.makeText(getContext(), "id = " + id, Toast.LENGTH_SHORT).show();
        IAsynTaskDelegate asynTaskDelegate = new IAsynTaskDelegate() {
            @Override
            public void didSuccessWithMessage(String message) {
                Intent intent = new Intent(getActivity(), GMapsFollowLocationActivity.class);
                intent.putExtra("channel", id);
                getContext().startActivity(intent);
            }

            @Override
            public void didFailWithMessage(String message) {
                 Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void didSuccessWithJsonArray(JSONArray jsonArray) {

            }
        };
        new JoinChannel(hud, fManager, asynTaskDelegate).execute(id);
    }

}
