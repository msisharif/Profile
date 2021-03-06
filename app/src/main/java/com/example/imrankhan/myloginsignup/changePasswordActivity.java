package com.example.imrankhan.myloginsignup;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class changePasswordActivity extends AppCompatActivity {

    MyDataBaseHelper myDataBaseHelper;

    private EditText old_password_editText, new_password_editText, retype_password_editText;
    private Button update_password_button;
    private String old_pass_text, new_pass_text, retype_pass_text;
    private String username_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9C27B0")));
        getSupportActionBar().setLogo(R.drawable.action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        myDataBaseHelper = new MyDataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getWritableDatabase();

        old_password_editText = (EditText) findViewById(R.id.old_password_id);
        new_password_editText = (EditText) findViewById(R.id.new_password_id);
        retype_password_editText = (EditText) findViewById(R.id.retype_password_id);
        update_password_button = (Button) findViewById(R.id.update_password_id);

        Bundle bundle = getIntent().getExtras();
        username_intent = bundle.getString("username_key");

        update_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                old_pass_text = old_password_editText.getText().toString();
                new_pass_text = new_password_editText.getText().toString();
                retype_pass_text = retype_password_editText.getText().toString();

                if(old_pass_text.isEmpty() || new_pass_text.isEmpty() || retype_pass_text.isEmpty())
                {
                    Toast.makeText(changePasswordActivity.this, Html.fromHtml("<font color='#FF7F27' >(!) Please Complete The Input Box!</font>"), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean status = myDataBaseHelper.check_UserName_Password(username_intent, old_pass_text);

                    if(status == true)
                    {
                        if(new_pass_text.equals(retype_pass_text))
                        {
                            myDataBaseHelper.updatePassword(username_intent, new_pass_text);
                            Toast.makeText(changePasswordActivity.this, Html.fromHtml("<font color='#2eb82e' >(√) Password Updated Successfully!</font>"), Toast.LENGTH_SHORT).show();

                            finish();
                        }
                        else
                        {
                            Toast.makeText(changePasswordActivity.this, Html.fromHtml("<font color='#FF7F27' >(!) Please Enter Your Retype Password Appropriately!</font>"), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(changePasswordActivity.this, Html.fromHtml("<font color='#ff6666' >(!) Please Enter Your Old Password Appropriately!</font>"), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
