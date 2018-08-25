package com.example.imrankhan.myloginsignup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class welcomeProfileActivity extends AppCompatActivity {

    MyDataBaseHelper myDataBaseHelper;

    private ImageView welcome_profile_pic;
    private TextView welcome_name;
    private ProgressBar progressBar;

    String username_intent;
    int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_profile);

        myDataBaseHelper = new MyDataBaseHelper(this);

        welcome_profile_pic = (ImageView) findViewById(R.id.welcome_profile_pic_id);
        welcome_name = (TextView) findViewById(R.id.welcome_name_id);
        progressBar = (ProgressBar) findViewById(R.id.progress_id);

        setProfileWelcome();
        setProgressBar();
    }

    public void setProfileWelcome()
    {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            username_intent = bundle.getString("username_key");
            userDetails userDetails_obj = myDataBaseHelper.get_profile_information(username_intent);

            //setting up profile pic.
            String image_name = userDetails_obj.getProfile_pic();
            if (image_name.equals("man") || image_name.equals("woman") || image_name.equals("gender_unknown")) {
                welcome_profile_pic.setImageResource(getResources().getIdentifier(image_name, "drawable", this.getPackageName()));
            } else {
                String[] imageBundle = image_name.split("-");
                String profile_pic_name = imageBundle[0];
                String profile_pic_path = imageBundle[1];

                loadImageFromStorage(profile_pic_path, profile_pic_name);
            }

            welcome_name.setText(userDetails_obj.getFull_name());
        }
    }

    public void loadImageFromStorage(String path, String imageName)
    {
        try {
            File f=new File(path, imageName);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            welcome_profile_pic.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void setProgressBar()
    {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                doWork();
                startApp();
            }
        });

        thread.start();
    }

    public void doWork()
    {
        for(progress = 1; progress <= 100; progress = progress + 1)
        {
            try {
                Thread.sleep(80);
                progressBar.setProgress(progress);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startApp()
    {
        Intent intent = new Intent(getApplicationContext(), profileActivity.class);
        intent.putExtra("username_key", username_intent);
        startActivity(intent);
        finish();
    }


}
