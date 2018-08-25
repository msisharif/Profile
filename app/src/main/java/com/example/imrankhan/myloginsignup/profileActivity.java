package com.example.imrankhan.myloginsignup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class profileActivity extends AppCompatActivity implements View.OnClickListener{

    MyDataBaseHelper myDataBaseHelper;

    private ImageView setImage;
    private TextView full_name_text, email_text, dob_text, gender_text, username_text, contact_no_text, address_text ,national_id_text, credit_card_text, bank_account_text, driving_licence_text, favourite_quotes_text;
    private ImageButton update_info_button, account_button;

    String username_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9C27B0")));
        getSupportActionBar().setLogo(R.drawable.action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        myDataBaseHelper = new MyDataBaseHelper(this);

        setImage = (ImageView) findViewById(R.id.profile_pic_id);
        full_name_text = (TextView) findViewById(R.id.full_name_view_id);
        email_text = (TextView) findViewById(R.id.email_view_id);
        dob_text = (TextView) findViewById(R.id.dob_view_id);
        gender_text = (TextView) findViewById(R.id.gender_view_id);
        national_id_text = (TextView) findViewById(R.id.national_id_view_id);
        username_text = (TextView) findViewById(R.id.username_view_id);
        contact_no_text = (TextView) findViewById(R.id.contact_no_view_id);
        address_text = (TextView) findViewById(R.id.address_view_id);
        credit_card_text = (TextView) findViewById(R.id.credit_card_view_id);
        bank_account_text = (TextView) findViewById(R.id.bank_account_view_id);
        driving_licence_text = (TextView) findViewById(R.id.driving_licence_view_id);
        favourite_quotes_text = (TextView) findViewById(R.id.favourite_quotes_view_id);

        update_info_button = (ImageButton) findViewById(R.id.update_info_button_id);
        account_button = (ImageButton) findViewById(R.id.account_button_id);

        setProfile();

        update_info_button.setOnClickListener(this);
        account_button.setOnClickListener(this);

    }

    public void setProfile()
    {
        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            username_intent = bundle.getString("username_key");
            userDetails userDetails_obj = myDataBaseHelper.get_profile_information(username_intent);

            //setting up profile pic.
            String image_name = userDetails_obj.getProfile_pic();
            if(image_name.equals("man")  || image_name.equals("woman")  || image_name.equals("gender_unknown"))
            {
                setImage.setImageResource(getResources().getIdentifier(image_name, "drawable", this.getPackageName()));
            }
            else
            {
                String[] imageBundle = image_name.split("-");
                String profile_pic_name = imageBundle[0];
                String profile_pic_path = imageBundle[1];

                loadImageFromStorage(profile_pic_path, profile_pic_name);
            }

            full_name_text.setText(userDetails_obj.getFull_name());
            email_text.setText(userDetails_obj.getEmail());
            dob_text.setText(userDetails_obj.getDob());
            gender_text.setText(userDetails_obj.getGender());
            username_text.setText(userDetails_obj.getUserName());
            contact_no_text.setText(userDetails_obj.getContact_no());
            address_text.setText(userDetails_obj.getAddress());
            national_id_text.setText(userDetails_obj.getNational_id());
            credit_card_text.setText(userDetails_obj.getCredit_card());
            bank_account_text.setText(userDetails_obj.getBank_account());
            driving_licence_text.setText(userDetails_obj.getDriving_licence());
            favourite_quotes_text.setText(userDetails_obj.getFavourite_quotes());
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.update_info_button_id)
        {
            Intent intent = new Intent(profileActivity.this, updateInfoActivity.class);
            intent.putExtra("username_key", username_intent);
            startActivity(intent);
        }
        else  if(view.getId() == R.id.account_button_id)
        {
            Intent intent = new Intent(profileActivity.this, accountSettings.class);
            intent.putExtra("username_key", username_intent);
            startActivity(intent);
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        setProfile();
    }

    private void loadImageFromStorage(String path, String imageName)
    {

        try {
            File f=new File(path, imageName);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            setImage.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.share_id)
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");

            String subject = "SHARE";
            String body = "Share this app.\nApplication Link (Link Will Be Updated Soon...)";

            intent.putExtra(Intent.EXTRA_SUBJECT,subject);
            intent.putExtra(Intent.EXTRA_TEXT,body);

            startActivity(Intent.createChooser(intent, "Share With"));
        }

        else if(item.getItemId() == R.id.feedback_id)
        {
            Intent intent = new Intent(profileActivity.this, feedBackActivity.class);
            startActivity(intent);
        }

        else if(item.getItemId() == R.id.info_id)
        {
            Intent intent = new Intent(profileActivity.this, About.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogueBuilder = new AlertDialog.Builder(this);
        alertDialogueBuilder.setIcon(R.drawable.exit);
        alertDialogueBuilder.setTitle( Html.fromHtml("<font color='#006080'>Your Account Will Be Logged Out ?</font>"));
        alertDialogueBuilder.setNegativeButton("Cancel", null);
        alertDialogueBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(profileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).create().show();
    }
}
