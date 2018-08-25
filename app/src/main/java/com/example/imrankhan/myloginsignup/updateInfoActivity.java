package com.example.imrankhan.myloginsignup;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class updateInfoActivity extends AppCompatActivity implements View.OnClickListener{

    MyDataBaseHelper myDataBaseHelper;
    userDetails userDetails_obj;

    String username_intent;

    private EditText full_name_editText, email_editText, dob_editText, gender_editText, contact_no_eidtText, address_editText, national_id_editText, credit_card_editText, bank_account_editText, driving_licence_editText, favourite_quotes_editText;
    private Button update_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9C27B0")));
        getSupportActionBar().setLogo(R.drawable.action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        myDataBaseHelper = new MyDataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getWritableDatabase();

        full_name_editText = (EditText) findViewById(R.id.full_name_id);
        email_editText = (EditText) findViewById(R.id.email_id);
        dob_editText = (EditText) findViewById(R.id.dob_id);
        gender_editText = (EditText) findViewById(R.id.gender_id);
        contact_no_eidtText = (EditText) findViewById(R.id.contact_no_id);
        address_editText = (EditText) findViewById(R.id.address_id);
        national_id_editText = (EditText) findViewById(R.id.national_id);
        credit_card_editText = (EditText) findViewById(R.id.card_details_id);
        bank_account_editText = (EditText) findViewById(R.id.bank_account_id);
        driving_licence_editText = (EditText) findViewById(R.id.driving_licence_id);
        favourite_quotes_editText = (EditText) findViewById(R.id.favourite_quotes_id);

        update_button = (Button) findViewById(R.id.update_id);

        Bundle bundle = getIntent().getExtras();

        username_intent = bundle.getString("username_key");
        userDetails_obj = myDataBaseHelper.get_profile_information(username_intent);

        full_name_editText.setText(userDetails_obj.getFull_name().toString());
        email_editText.setText(userDetails_obj.getEmail());
        dob_editText.setText(userDetails_obj.getDob());
        gender_editText.setText(userDetails_obj.getGender());
        contact_no_eidtText.setText(userDetails_obj.getContact_no());
        address_editText.setText(userDetails_obj.getAddress());
        national_id_editText.setText(userDetails_obj.getNational_id());
        credit_card_editText.setText(userDetails_obj.getCredit_card());
        bank_account_editText.setText(userDetails_obj.getBank_account());
        driving_licence_editText.setText(userDetails_obj.getDriving_licence());
        favourite_quotes_editText.setText(userDetails_obj.getFavourite_quotes());

        update_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.update_id)
        {
            userDetails_obj.setFull_name(full_name_editText.getText().toString());
            userDetails_obj.setEmail(email_editText.getText().toString());
            userDetails_obj.setDob(dob_editText.getText().toString());
            userDetails_obj.setGender(gender_editText.getText().toString());
            userDetails_obj.setContact_no(contact_no_eidtText.getText().toString());
            userDetails_obj.setAddress(address_editText.getText().toString());
            userDetails_obj.setNational_id(national_id_editText.getText().toString());
            userDetails_obj.setCredit_card(credit_card_editText.getText().toString());
            userDetails_obj.setBank_account(bank_account_editText.getText().toString());
            userDetails_obj.setDriving_licence(driving_licence_editText.getText().toString());
            userDetails_obj.setFavourite_quotes(favourite_quotes_editText.getText().toString());

            myDataBaseHelper.updateInfo(userDetails_obj, username_intent);

            finish();
        }

    }
}
