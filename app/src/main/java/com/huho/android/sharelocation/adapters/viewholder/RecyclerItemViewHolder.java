
package com.huho.android.sharelocation.adapters.viewholder;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.google.android.gms.maps.model.LatLng;
import com.huho.android.sharelocation.R;
import com.huho.android.sharelocation.ShareLocationApplication;
import com.huho.android.sharelocation.adapters.RecyclerAdapter;
import com.huho.android.sharelocation.utils.objects.Channel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    @InjectView(R.id.itemNameChannel)
    public TextView mItemNameChannel;

    @InjectView(R.id.itemDesChannel)
    public TextView mItemDesChannel;

    @InjectView(R.id.itemRomNum)
    public TextView mCurrentMember;

    @InjectView(R.id.itemAddressChannel)
    public TextView mTvAddress;

    String mAddress = "";

    private int mChannelId;

    private final RecyclerAdapter.OnItemClickListener mOnItemClickListener;

    private Channel mChannel;

    public RecyclerItemViewHolder(final View parent,
            RecyclerAdapter.OnItemClickListener onItemClickListener) {
        super(parent);
        ButterKnife.inject(this,parent);
        mOnItemClickListener = onItemClickListener;
        parent.setOnClickListener(this);
    }

    public static RecyclerItemViewHolder newInstance(View parent,
            RecyclerAdapter.OnItemClickListener onItemClickListener) {
        return new RecyclerItemViewHolder(parent, onItemClickListener);
    }

    public void setItemNameChannel(CharSequence text) {
        mItemNameChannel.setText(text);
    }

    public void setChannel(Channel channel) {
        this.mChannel = channel;
        mItemNameChannel.setText(channel.getName());
        mItemDesChannel.setText(channel.getDescription());
        mCurrentMember.setText(channel.getmCurrentUser()+"");
        mChannelId = channel.getId();

        final LatLng des = channel.getLocationDes();
        if (des != null && des.latitude != 0 && des.longitude != 0) {
            final Geocoder geocoder = new Geocoder(ShareLocationApplication.getInstance(),
                    Locale.getDefault());
            new AsyncTask<Geocoder, Void, List<Address>>() {
                @Override
                protected List<Address> doInBackground(Geocoder... params) {
                    List<Address> addr = null;
                    try {
                        addr = geocoder.getFromLocation(des.latitude, des.longitude, 1);
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
                            mTvAddress.setText(mAddress);
                        }

                    }
                    super.onPostExecute(result);
                }
            }.execute(geocoder);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(mChannelId);
    }
}
