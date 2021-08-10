package com.example.coronainformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    String  URL = "https://www.hpb.health.gov.lk/api/get-current-statistical";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView lblVersion = (TextView) findViewById(R.id.lblVersion);
        final TextView lblDeveloper = (TextView) findViewById(R.id.lblDeveloper);
        final TextView lblSource = (TextView)  findViewById(R.id.lblSource);

        final Button btnClose = (Button) findViewById(R.id.btnClose);
        final Button btnRefresh = (Button) findViewById(R.id.btnRefresh);



        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Data Refreshed" , Toast.LENGTH_LONG).show();
                loadData();
            }
        });

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);

            lblVersion.setText(getResources().getString(R.string.app_name) + ": " + pInfo.versionName);
            lblDeveloper.setText(getResources().getString(R.string.developer));
            lblSource.setText(getResources().getString(R.string.source));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        loadData();

    }

    private String formatNumber(String value) {
        String strTemp;
        int temp = Integer.parseInt(value);
        strTemp = NumberFormat.getInstance().format(temp);
        return strTemp;
    }

    private void loadData () {
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
        final TextView lblUpdatedAt = (TextView) findViewById(R.id.lblUpdatedAt);


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
                                //   Toast.makeText(getApplicationContext(),jsonObject.getString("local_new_cases"),Toast.LENGTH_LONG).show();
                                lblNewCases.setText(formatNumber(jsonObject.getString("local_new_cases")));
                                lblTotalCases.setText(formatNumber(jsonObject.getString("local_total_cases")));
                                lblTotalDeaths.setText(formatNumber(jsonObject.getString("local_deaths")));
                                lblNewDeaths.setText(formatNumber(jsonObject.getString("local_new_deaths")));
                                lblTotInvInHost.setText(formatNumber(jsonObject.getString("local_total_number_of_individuals_in_hospitals")));
                                lblActiveCases.setText(formatNumber(jsonObject.getString("local_active_cases")));
                                lblTotalRecovered.setText(formatNumber(jsonObject.getString("local_recovered")));

                                lblGDeaths.setText(formatNumber(jsonObject.getString("global_deaths")));
                                lblGTotalRecovered.setText(formatNumber(jsonObject.getString("global_recovered")));
                                lblGNewCases.setText(formatNumber(jsonObject.getString("global_new_cases")));
                                lblGTotCases.setText(formatNumber(jsonObject.getString("global_total_cases")));
                                lblUpdatedAt.setText(getResources().getString(R.string.updatedAt) + jsonObject.getString("update_date_time"));


                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.somethingwentwrong) + e, Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}