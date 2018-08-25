package com.example.imrankhan.myloginsignup;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class changeProfilePicActivity extends AppCompatActivity implements View.OnClickListener{

    MyDataBaseHelper myDataBaseHelper;

    private ImageView image;
    private Button choose_button, upload_button, delete_button;
    private Bitmap bitmap;

    String username_intent, imagePath;

    private static final int PICK_FROM_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile_pic);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9C27B0")));
        getSupportActionBar().setLogo(R.drawable.action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        myDataBaseHelper = new MyDataBaseHelper(this);

        image = (ImageView) findViewById(R.id.change_pic_id);
        choose_button = (Button) findViewById(R.id.choose_button_id);
        upload_button = (Button) findViewById(R.id.upload_button_id);
        delete_button = (Button) findViewById(R.id.delete_button_id);

        Bundle bundle = getIntent().getExtras();
        username_intent = bundle.getString("username_key");

        userDetails userDetails_obj = myDataBaseHelper.get_profile_information(username_intent);

        //setting up profile pic.
        String image_name = userDetails_obj.getProfile_pic();
        if(image_name.equals("man")  || image_name.equals("woman")  || image_name.equals("gender_unknown"))
        {
            image.setImageResource(getResources().getIdentifier(image_name, "drawable", this.getPackageName()));
        }
        else
        {
            String[] imageBundle = image_name.split("-");
            String profile_pic_name = imageBundle[0];
            String profile_pic_path = imageBundle[1];

            loadImageFromStorage(profile_pic_path, profile_pic_name);
        }

        choose_button.setOnClickListener(this);
        upload_button.setOnClickListener(this);
        delete_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.choose_button_id)
        {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 0);
            intent.putExtra("aspectY", 0);
            intent.putExtra("outputX", 155);
            intent.putExtra("outputY", 195);
            try {
                intent.putExtra("return-data", true);
                startActivityForResult(Intent.createChooser(intent,"Select An Image."), PICK_FROM_GALLERY);
            } catch (ActivityNotFoundException e) {}
        }

        else if(view.getId() == R.id.upload_button_id)
        {
            if(bitmap == null)
            {
                Toast.makeText(changeProfilePicActivity.this, Html.fromHtml("<font color='#FF7F27' >(!) Please Pick a new Photo!</font>"), Toast.LENGTH_SHORT).show();
            }
            else
            {
                imagePath = saveToInternalStorage(bitmap);
                String imageNameAndPath = username_intent+"-"+imagePath;
                myDataBaseHelper.changeProfilepic(imageNameAndPath, username_intent);
                Toast.makeText(changeProfilePicActivity.this, Html.fromHtml("<font color='#2eb82e' >(√) Profile Picture Uploaded Successfully!</font>"), Toast.LENGTH_SHORT).show();
            }
        }

        else if(view.getId() == R.id.delete_button_id)
        {
            userDetails userDetails_obj = myDataBaseHelper.get_profile_information(username_intent);
            String image_name = userDetails_obj.getProfile_pic();

            if(image_name.equals("man") || image_name.equals("woman") || image_name.equals("gender_unknown"))
            {
                Toast.makeText(changeProfilePicActivity.this, Html.fromHtml("<font color='#FF7F27' >(!) Sorry No Profile Picture Founded!</font>"), Toast.LENGTH_SHORT).show();
            }
            else
            {
                AlertDialog.Builder alertDialogueBuilder = new AlertDialog.Builder(this);
                alertDialogueBuilder.setIcon(R.drawable.question);
                alertDialogueBuilder.setTitle( Html.fromHtml("<font color='#006080'>Delete Profile Picture?</font>"));
                alertDialogueBuilder.setNegativeButton("Cancel", null);
                alertDialogueBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        File dir = getFilesDir();
                        File file = new File(dir, username_intent);
                        file.delete();
                        myDataBaseHelper.defaultProfilePic(username_intent);
                        finish();
                        overridePendingTransition( 0, 0);
                        startActivity(getIntent());
                        overridePendingTransition( 0, 0);

                    }
                }).create().show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                bitmap = extras.getParcelable("data");
                image.setImageBitmap(bitmap);
            }
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory, username_intent);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private void loadImageFromStorage(String path, String imageName)
    {

        try {
            File f=new File(path, imageName);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            image.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
