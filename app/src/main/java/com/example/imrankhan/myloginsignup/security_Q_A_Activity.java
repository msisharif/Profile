package com.example.imrankhan.myloginsignup;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class security_Q_A_Activity extends AppCompatActivity implements View.OnClickListener{

    MyDataBaseHelper myDataBaseHelper;
    private TextView security_q_text;
    private EditText editText1, editText2;
    private Button button;
    String username_intent, security_q, security_a, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security__q__a_);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9C27B0")));
        getSupportActionBar().setLogo(R.drawable.action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        myDataBaseHelper = new MyDataBaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        username_intent = bundle.getString("username_key");
        userDetails userDetails_obj = myDataBaseHelper.get_profile_information(username_intent);

        initializeInfo(bundle, userDetails_obj);

        security_q_text = (TextView) findViewById(R.id.q_text_2_id);
        editText1 = (EditText) findViewById(R.id.a_editText_1_id);
        editText2 = (EditText) findViewById(R.id.a_editText_2_id);
        button = (Button) findViewById(R.id.submit_id);

        security_q_text.setText("# "+security_q);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String textBox1 = editText1.getText().toString();
        String textBox2 = editText2.getText().toString();

        if(textBox1.isEmpty() || textBox2.isEmpty())
        {
            Toast.makeText(security_Q_A_Activity.this, Html.fromHtml("<font color='#FF7F27' >(!) Please Insert Information Properly!</font>"), Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(textBox1.equals(dob))
            {
                if(textBox2.equals(security_a))
                {
                    Intent intent = new Intent(security_Q_A_Activity.this, resetPasswordForForgotPassword.class);
                    intent.putExtra("username_key", username_intent);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(security_Q_A_Activity.this, Html.fromHtml("<font color='#ff6666' >(X) Security Question Answer Is Wrong!</font>"), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(security_Q_A_Activity.this, Html.fromHtml("<font color='#ff6666' >(X) Date Of Birth Is Wrong!</font>"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initializeInfo(Bundle bundle, userDetails userDetails_obj)
    {
        if(bundle != null) {
            security_q = userDetails_obj.getSecurity_q();
            security_a = userDetails_obj.getSecurity_a();
            dob = userDetails_obj.getDob();
        }
    }
}
