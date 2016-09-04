
package com.huho.android.sharelocation.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.huho.android.sharelocation.R;
import com.huho.android.sharelocation.asyntask.UpLoadImageAsync;
import com.huho.android.sharelocation.interfaces.IAsynTaskDelegate;
import com.huho.android.sharelocation.utils.common.ProgressHUD;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sev_user on 4/25/2016.
 */
public class SettingsActivity extends FragmentActivity {

    private ImageView imgAvatar;

    private Button btnUpLoad;

    private String path = "";

    private String filename = "";

    private IAsynTaskDelegate iAsynTaskUpLoadImage;

    private ProgressHUD hud;

    private FragmentManager fManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        btnUpLoad = (Button) findViewById(R.id.btnUpLoad);
        btnUpLoad.setEnabled(false);
        hud = new ProgressHUD(this);
        fManager = getSupportFragmentManager();

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                pickImageIntent.setType("image/*");
                pickImageIntent.putExtra("crop", "true");
                pickImageIntent.putExtra("outputX", 160);
                pickImageIntent.putExtra("outputY", 230);
                pickImageIntent.putExtra("aspectX", 1);
                pickImageIntent.putExtra("aspectY", 1.5f);
                pickImageIntent.putExtra("scale", true);
                pickImageIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                startActivityForResult(pickImageIntent, 100);
            }
        });

        iAsynTaskUpLoadImage = new IAsynTaskDelegate() {
            @Override
            public void didSuccessWithMessage(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void didFailWithMessage(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void didSuccessWithJsonArray(JSONArray jsonArray) {

            }
        };

        btnUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bm = BitmapFactory.decodeFile(path);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                new UpLoadImageAsync(hud, fManager, iAsynTaskUpLoadImage).execute(new String[] {
                        encodedImage, filename
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    FileOutputStream fos = null;
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
                        filename = System.currentTimeMillis() + ".jpg";
                        path = Environment.getExternalStorageDirectory() + File.separator
                                + filename;
                        File file = new File(path);

                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            fos = new FileOutputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            fos.write(stream.toByteArray());
                            fos.close();
                            btnUpLoad.setEnabled(true);
                            Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        imgAvatar.setImageBitmap(photo);

                        Log.d("LOG", path);
                    }
                }
                break;
            default:
                break;
        }
    }
}
