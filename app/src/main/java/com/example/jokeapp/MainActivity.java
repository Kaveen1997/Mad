package com.example.jokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    String url ="https://official-joke-api.appspot.com/random_joke";
    TextView txtjokes,txtid,txttype,txtsetup,txtpunch;
    ProgressBar progressBar;
    Button close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        txtjokes=findViewById(R.id.textJokes);
        txtid =findViewById(R.id.txtID);
        txttype=findViewById(R.id.txtType);
        txtsetup=findViewById(R.id.txtSetup);
        txtpunch=findViewById(R.id.txtPunch);
        close=findViewById(R.id.btnclose);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        progressBar = findViewById(R.id.progressBar);
    }

    public void getJokes(View view) {

        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        int ID=0;
                        String type,setup,punch;

                        try {
                            ID = response.getInt("id");
                            type=response.getString("type");
                            setup=response.getString("setup");
                            punch=response.getString("punchline");

                            Joke joke=new Joke(ID,type,setup,punch);

                            txtid.setText(joke.getID()+"");
                            txtid.setVisibility(View.VISIBLE);
                            txttype.setText(joke.getType()+"");
                            txttype.setVisibility(View.VISIBLE);
                            txtsetup.setText(joke.getSetup()+"");
                            txtsetup.setVisibility(View.VISIBLE);
                            txtpunch.setText(joke.getPunchline()+"");
                            txtpunch.setVisibility(View.VISIBLE);

                            progressBar.setVisibility(View.INVISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        txtjokes.setText("Response: " +ID);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String err=error.toString();
                        txtjokes.setText("Cannot get data: " +error.toString());

                    }


                });
        queue.add(jsonObjectRequest);
    }
}
