package com.example.imrankhan.myloginsignup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    MyDataBaseHelper myDataBaseHelper;

    private EditText userName_eidtText, password_editText;
    private Button login_button, signUp_button;
    private TextView attempt_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9C27B0")));
        getSupportActionBar().setLogo(R.drawable.action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        myDataBaseHelper = new MyDataBaseHelper(this);

        userName_eidtText = (EditText) findViewById(R.id.user_name_id);
        password_editText = (EditText) findViewById(R.id.password_id);
        attempt_text = (TextView) findViewById(R.id.attempt_id);

        login_button = (Button) findViewById(R.id.login_id);
        signUp_button = (Button) findViewById(R.id.signUp_id);

        if(loadUsername() != null)
        {
            userName_eidtText.setText(loadUsername());
        }

        login_button.setOnClickListener(this);
        signUp_button.setOnClickListener(this);
        attempt_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String username = userName_eidtText.getText().toString();
        String password = password_editText.getText().toString();

        if(view.getId() == R.id.login_id)
        {
            if(username.isEmpty() || password.isEmpty())
            {
                Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#FF7F27' >(!) Please Insert Username & Password Properly!</font>"), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Boolean status = myDataBaseHelper.check_UserName_Password(username, password);
                if(status == true)
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("username", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("username_key", username);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), welcomeProfileActivity.class);
                    intent.putExtra("username_key", username);
                    startActivity(intent);

                    password_editText.setText("");
                }
                else
                {
                    Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#ff6666' >(X) Incorrect Username Or Password!</font>"), Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if(view.getId() == R.id.signUp_id)
        {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        }

        else if(view.getId() == R.id.attempt_id)
        {
            AlertDialog.Builder alertDialogueBuilder = new AlertDialog.Builder(this);
            alertDialogueBuilder.setIcon(R.drawable.question);
            alertDialogueBuilder.setTitle( Html.fromHtml("<font color='#006080'>Are You " + username + "?</font>"));
            alertDialogueBuilder.setNegativeButton("Cancel", null);
            alertDialogueBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Boolean status = myDataBaseHelper.check_UserName_Password(userName_eidtText.getText().toString(), "admin_msi_is_checking_username");

                    if(status == true)
                    {
                        AlertDialog.Builder alertDialogueBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogueBuilder.setIcon(R.drawable.question);
                        alertDialogueBuilder.setTitle( Html.fromHtml("<font color='#006080'>Do you want to answer some question ?</font>"));
                        alertDialogueBuilder.setNegativeButton("Cancel", null);
                        alertDialogueBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intent = new Intent(MainActivity.this, security_Q_A_Activity.class);
                                intent.putExtra("username_key", userName_eidtText.getText().toString());
                                startActivity(intent);
                            }}).create().show();

                    }
                    else{Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#ff6666' >(X) Invalid Username!</font>"), Toast.LENGTH_SHORT).show();}

                }
            }).create().show();
        }
    }

    public String loadUsername()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("username", Context.MODE_PRIVATE);
        String username_loaded = sharedPreferences.getString("username_key", null);
        return username_loaded;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogueBuilder = new AlertDialog.Builder(this);
                alertDialogueBuilder.setIcon(R.drawable.log_out_alert);
                alertDialogueBuilder.setTitle( Html.fromHtml("<font color='#006080'>Shut The App ?</font>"));
                alertDialogueBuilder.setNegativeButton(android.R.string.no, null);
                alertDialogueBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).create().show();
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
            Intent intent = new Intent(MainActivity.this, feedBackActivity.class);
            startActivity(intent);
        }

        else if(item.getItemId() == R.id.info_id)
        {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
