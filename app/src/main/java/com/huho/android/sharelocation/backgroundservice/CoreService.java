
package com.huho.android.sharelocation.backgroundservice;

import android.util.Log;

import com.huho.android.sharelocation.SharedPreferencesManager;
import com.huho.android.sharelocation.utils.objects.Channel;
import com.huho.android.sharelocation.utils.objects.Member;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Created by hung.nq1 on 3/3/2016.
 */
public class CoreService {
    private static final String TAG = "Tracker - CoreService";

    public static CoreService instance;

    public static final String BASE_URL = "http://testsharelocation.orgfree.com/";

    public static final String GETLOCATION_URL = "getlocation.php";

    public static final String LOGIN_URL = "login.php";

    public static final String REGISTER_URL = "register.php";

    public static final String CREATECHANNEL_URL = "createchannel.php";

    public static final String GETPUBLICCHANNEL_URL = "getpublicchannels.php";

    public static final String GETMYCHANNEL_URL = "getmychannels.php";

    public static final String JOINCHANNEL_URL = "joinchannel.php";

    public static final String UPLOADIMAGE_URL = "imageupload.php";

    public static CoreService getInstance() {
        if (instance == null) {
            instance = new CoreService();
        }
        return instance;
    }

    public String sendMemberLocation(Member member) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String email = SharedPreferencesManager.getInstance().getUserEmail();
        String pass = SharedPreferencesManager.getInstance().getUserPassword();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", pass));
        params.add(new BasicNameValuePair("cid", member.getmChannelName()));
        params.add(new BasicNameValuePair("lat", member.getmLatLng().latitude + ""));
        params.add(new BasicNameValuePair("lon", member.getmLatLng().longitude + ""));
        return postJsonData(GETLOCATION_URL, params);
    }

    public String sendLogin(String email, String password) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        return postJsonData(LOGIN_URL, params);
    }

    public String sendRegister(String email, String password, String name) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("name", name));
        return postJsonData(REGISTER_URL, params);
    }

    public String createChannel(Channel channel) {
        String email = SharedPreferencesManager.getInstance().getUserEmail();
        String pass = SharedPreferencesManager.getInstance().getUserPassword();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", pass));
        params.add(new BasicNameValuePair("name", channel.getName()));
        params.add(new BasicNameValuePair("description", channel.getDescription()));
        params.add(new BasicNameValuePair("time", channel.getTime()));
        params.add(new BasicNameValuePair("time_end", channel.getmTimeEnd()));
        params.add(new BasicNameValuePair("lat_src", channel.getLocationSrc().latitude + ""));
        params.add(new BasicNameValuePair("lon_src", channel.getLocationSrc().longitude + ""));
        params.add(new BasicNameValuePair("lat_des", channel.getLocationDes().latitude + ""));
        params.add(new BasicNameValuePair("lon_des", channel.getLocationDes().longitude + ""));
        params.add(new BasicNameValuePair("sec", channel.getIsPrivate() ? "private" : "public"));
        params.add(new BasicNameValuePair("max", channel.getMaxMember() + ""));
        return postJsonData(CREATECHANNEL_URL, params);
    }

    public String getGetPublicChannel() {
        String email = SharedPreferencesManager.getInstance().getUserEmail();
        String pass = SharedPreferencesManager.getInstance().getUserPassword();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", pass));
        return postJsonData(GETPUBLICCHANNEL_URL, params);
    }

    public String getMyChannel() {
        String email = SharedPreferencesManager.getInstance().getUserEmail();
        String pass = SharedPreferencesManager.getInstance().getUserPassword();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", pass));
        return postJsonData(GETMYCHANNEL_URL, params);
    }

    public String joinChannel(int cid) {
        String email = SharedPreferencesManager.getInstance().getUserEmail();
        String pass = SharedPreferencesManager.getInstance().getUserPassword();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", pass));
        params.add(new BasicNameValuePair("cid", cid + ""));
        return postJsonData(JOINCHANNEL_URL, params);
    }

    public String upLoadImage(String imageBase64, String fileName) {
        String email = SharedPreferencesManager.getInstance().getUserEmail();
        String pass = SharedPreferencesManager.getInstance().getUserPassword();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", pass));
        params.add(new BasicNameValuePair("image", imageBase64));
        params.add(new BasicNameValuePair("image_name", fileName));
        return postJsonData(UPLOADIMAGE_URL, params);
    }

    private String postJsonData(String url, List<NameValuePair> params) {
        long start_time = System.currentTimeMillis();
        Log.i(TAG, "BEGIN TIME CONECT" + String.valueOf(start_time));
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_URL);
        builder.append(url);
        Log.w(TAG, "URL " + builder.toString());

        HttpPost postMethod = new HttpPost(builder.toString());
        ///// Set time out
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 20 * 1000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 20 * 1000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpClient client = new DefaultHttpClient(httpParameters);

        try {
            postMethod.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            HttpResponse response = client.execute(postMethod);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                builder.setLength(0);
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(TAG, "Getting Json failed");
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        Log.i(TAG, "PostRequest" + builder.toString());
        long end_time = System.currentTimeMillis();
        Log.i(TAG, "END TIME CONECT" + String.valueOf(end_time));
        double difference = (end_time - start_time) / 1000;
        Log.i(TAG, "TIME CONECT" + String.valueOf(difference));
        return builder.toString();
    }
}
