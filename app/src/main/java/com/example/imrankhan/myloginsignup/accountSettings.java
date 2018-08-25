package com.example.imrankhan.myloginsignup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class accountSettings extends AppCompatActivity implements View.OnClickListener{

    MyDataBaseHelper myDataBaseHelper;

    private ImageView change_profile_pic, change_password, delete_account, logout;
    String username_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9C27B0")));
        getSupportActionBar().setLogo(R.drawable.action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        myDataBaseHelper = new MyDataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getWritableDatabase();

        change_profile_pic = (ImageView) findViewById(R.id.change_profile_pic_id);
        change_password = (ImageView) findViewById(R.id.change_password_id);
        delete_account = (ImageView) findViewById(R.id.delete_account_id);
        logout = (ImageView) findViewById(R.id.logout_id);

        Bundle bundle = getIntent().getExtras();
        username_intent = bundle.getString("username_key");

        change_profile_pic.setOnClickListener(this);
        change_password.setOnClickListener(this);
        delete_account.setOnClickListener(this);
        logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.change_profile_pic_id)
        {
            Intent intent = new Intent(accountSettings.this, changeProfilePicActivity.class);
            intent.putExtra("username_key", username_intent);
            startActivity(intent);
        }

        else if(view.getId() == R.id.change_password_id)
        {
            Intent intent = new Intent(accountSettings.this, changePasswordActivity.class);
            intent.putExtra("username_key", username_intent);
            startActivity(intent);
        }

        else if(view.getId() == R.id.logout_id)
        {
            AlertDialog.Builder alertDialogueBuilder = new AlertDialog.Builder(this);
            alertDialogueBuilder.setIcon(R.drawable.exit);
            alertDialogueBuilder.setTitle( Html.fromHtml("<font color='#006080'>Log Out ?</font>"));
            alertDialogueBuilder.setNegativeButton("Cancel", null);
            alertDialogueBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(accountSettings.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }).create().show();
        }

        else if(view.getId() == R.id.delete_account_id)
        {
            AlertDialog.Builder alertDialogueBuilder = new AlertDialog.Builder(this);
            alertDialogueBuilder.setIcon(R.drawable.warning);
            alertDialogueBuilder.setTitle( Html.fromHtml("<font color='#006080'>Delete Account ?</font>"));
            alertDialogueBuilder.setNegativeButton("Cancel", null);
            alertDialogueBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    myDataBaseHelper.deleteAccount(username_intent);

                    LayoutInflater layoutInflater = getLayoutInflater();
                    View customToastView = layoutInflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_id));

                    Toast toast = new Toast(accountSettings.this);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setView(customToastView);
                    toast.show();

                    Intent intent = new Intent(accountSettings.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }).create().show();
        }
    }
}