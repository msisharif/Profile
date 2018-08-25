package com.example.imrankhan.myloginsignup;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    MyDataBaseHelper myDataBaseHelper;
    userDetails userDetails_obj;

    private EditText full_name_editText, email_editText, dob_editText, gender_editText, userName_signUp_edtText, password_signUp_editText, security_q_a_editText;
    private Button  signUp_button;
    private Spinner spinner;

    String[] items = new String[]{"Please Select an Option!", "Tell a secrete Code!", "Tell a secrete Word!", "Tell a secrete Name!"};
    String security_q;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9C27B0")));
        getSupportActionBar().setLogo(R.drawable.action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        myDataBaseHelper = new MyDataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getWritableDatabase();

        userDetails_obj = new userDetails();


        full_name_editText = (EditText) findViewById(R.id.full_name_id);
        email_editText = (EditText) findViewById(R.id.email_id);
        dob_editText = (EditText) findViewById(R.id.dob_id);
        gender_editText = (EditText) findViewById(R.id.gender_id);
        userName_signUp_edtText = (EditText) findViewById(R.id.user_name_signUp_id);
        password_signUp_editText = (EditText) findViewById(R.id.password_signUp_id);
        security_q_a_editText = (EditText) findViewById(R.id.security_q_a_id);

        spinner = (Spinner) findViewById(R.id.spinner_id);

        signUp_button = (Button) findViewById(R.id.signUp_id);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                {
                    security_q = "Please Select a Option!";
                }
                else if(i == 1)
                {
                    security_q = "Tell a secrete Code!";
                }
                else if(i == 2)
                {
                    security_q = "Tell a secrete Word!";
                }
                else if(i == 3)
                {
                    security_q = "Tell a secrete Name!";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        signUp_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.signUp_id)
        {
            String full_name = full_name_editText.getText().toString();
            String email = email_editText.getText().toString();
            String dob = dob_editText.getText().toString();
            String gender = gender_editText.getText().toString();
            String user_name = userName_signUp_edtText.getText().toString();
            String password = password_signUp_editText.getText().toString();
            String security_a = security_q_a_editText.getText().toString();

            if(full_name.isEmpty() || email.isEmpty() || dob.isEmpty() || gender.isEmpty() || user_name.isEmpty() || password.isEmpty() || security_q.equals("Please Select a Option!") || security_a.isEmpty())
            {
                Toast.makeText(SignUpActivity.this, Html.fromHtml("<font color='#FF7F27' >(!) Please Insert Information Properly!</font>"), Toast.LENGTH_SHORT).show();
            }
            else
            {
                userDetails_obj.setFull_name(full_name);
                userDetails_obj.setEmail(email);
                userDetails_obj.setDob(dob);
                userDetails_obj.setGender(gender);
                userDetails_obj.setUserName(user_name);
                userDetails_obj.setPassword(password);
                userDetails_obj.setSecurity_q(security_q);
                userDetails_obj.setSecurity_a(security_a);

                Boolean insertion = myDataBaseHelper.insertData(userDetails_obj);

                if(insertion == false)
                {
                    Toast.makeText(SignUpActivity.this, Html.fromHtml("<font color='#ff6666' >(X) Unsuccessful!</font>"), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, Html.fromHtml("<font color='#2eb82e' >(√) Account Has Been Created Successfully!</font>"), Toast.LENGTH_SHORT).show();

                    full_name_editText.setText("");
                    email_editText.setText("");
                    dob_editText.setText("");
                    gender_editText.setText("");
                    userName_signUp_edtText.setText("");
                    password_signUp_editText.setText("");
                    security_q_a_editText.setText("");

                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        }

    }
}
