package com.example.coronainformation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    String URL = "https://www.hpb.health.gov.lk/api/get-current-statistical";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView  lblNewCases = (TextView) findViewById(R.id.lblNewCases);
        final TextView lblTotalCases = (TextView)findViewById(R.id.lblTotalCases);
        final TextView lblTotalDeaths = (TextView) findViewById(R.id.lblTotalDeaths);
        final TextView lblNewDeaths = (TextView) findViewById(R.id.lblNewDeaths);

        final TextView lblTotInvInHost = (TextView) findViewById(R.id.textView8);
        final TextView lblActiveCases= (TextView) findViewById(R.id.lblActiveCases) ;
        final TextView lblTotalRecovered = (TextView) findViewById(R.id.lblTotalRecovered) ;

        final TextView lblGDeaths = (TextView) findViewById(R.id.lblGTotDeaths);
        final TextView lblGTotalRecovered = (TextView) findViewById(R.id.lblGTotRecovered);
        final TextView lblGNewCases = (TextView) findViewById(R.id.lblGNewCases);
        final TextView lblGTotCases = (TextView) findViewById(R.id.lblGTotCases);





        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      //  Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();

                        if (response != null) {
                            try {
                              //  JSONArray jsonArray   = response.getJSONArray("data");
                                JSONObject jsonObject = response.getJSONObject("data");
                                Toast.makeText(getApplicationContext(),jsonObject.getString("local_new_cases"),Toast.LENGTH_LONG).show();
                                lblNewCases.setText(jsonObject.getString("local_new_cases"));
                                lblTotalCases.setText(jsonObject.getString("local_total_cases"));
                                lblTotalDeaths.setText(jsonObject.getString("local_deaths"));
                                lblNewDeaths.setText(jsonObject.getString("local_new_deaths"));
                                lblTotInvInHost.setText(jsonObject.getString("local_total_number_of_individuals_in_hospitals"));
                                lblActiveCases.setText(jsonObject.getString("local_active_cases"));
                                lblTotalRecovered.setText(jsonObject.getString("local_recovered"));

                                lblGDeaths.setText(jsonObject.getString("global_deaths"));
                                lblGTotalRecovered.setText(jsonObject.getString("global_recovered"));
                                lblGNewCases.setText(jsonObject.getString("global_new_cases"));
                                lblGTotCases.setText(jsonObject.getString("global_total_cases"));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }

        );

        requestQueue.add(jsonObjectRequest);



    }
}