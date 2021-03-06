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
import android.widget.Toast;

public class feedBackActivity extends AppCompatActivity implements View.OnClickListener{

    private Button send;
    private EditText name,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9C27B0")));
        getSupportActionBar().setLogo(R.drawable.action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        send = (Button) findViewById(R.id.send_id);

        name = (EditText) findViewById(R.id.name_id);
        message = (EditText) findViewById(R.id.message_id);

        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        try{

            String name_text = name.getText().toString();
            String message_text = message.getText().toString();

            if(name_text.isEmpty() && message_text.isEmpty())
            {
                Toast.makeText(feedBackActivity.this, Html.fromHtml("<font color='#FF7F27' >(!) Please insert your name & message!</font>"), Toast.LENGTH_SHORT).show();
            }

            else
            {
                if(name_text.isEmpty())
                {
                    Toast.makeText(feedBackActivity.this, Html.fromHtml("<font color='#FF7F27' >(!) Please type your name!</font>"), Toast.LENGTH_SHORT).show();
                }

                else if(message_text.isEmpty())
                {
                    Toast.makeText(feedBackActivity.this, Html.fromHtml("<font color='#FF7F27' >(!) Please give your feedback!</font>"), Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(view.getId() == R.id.send_id)
                    {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/email");

                        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"msicseewu@gmail.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                        intent.putExtra(Intent.EXTRA_TEXT,"Name : "+name_text+"\n\nMessage : "+message_text);
                        startActivity(Intent.createChooser(intent,"Share With"));
                    }
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Exection : "+e, Toast.LENGTH_SHORT).show();
        }

    }

}
