package com.example.hp.appathon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

// Initialization
    private TextView tvResult;
    private String line="";
    private String data="";
    private MaterialBetterSpinner materialBetterSpinner ;
    TextInputLayout tilnum;
    EditText etnum;
    Button btnSubmit;

    String[] SPINNER_DATA = {"trivia","math","date","year"};
    int mutex=0;
    String option=null;

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Seting the Screen Orientation
        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);


        //Binding
        tvResult= (TextView) findViewById(R.id.tvResult);
        materialBetterSpinner = (MaterialBetterSpinner)findViewById(R.id.material_spinner1);
        tilnum= (TextInputLayout) findViewById(R.id.tilnum);
        btnSubmit= (Button) findViewById(R.id.btnSubmit);
        etnum= (EditText) findViewById(R.id.etnum);

        tilnum.setHint("Enter Number or Data MM/DD");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, SPINNER_DATA);

        materialBetterSpinner.setAdapter(adapter);


        //to Check Internet Connectivity
        getConnectivityStatusString(this);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String op=etnum.getText().toString();

                Toast.makeText(MainActivity.this, "HELLOOO ", Toast.LENGTH_SHORT).show();
                if(op.length()==0)
                {
                    tilnum.setError("Please Enter a valid number");
                    tilnum.setErrorEnabled(true);
                }
                else
                 {
                       materialBetterSpinner.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            option = materialBetterSpinner.getText().toString();
                            option = op + "/" + option;
                            Toast.makeText(MainActivity.this, option, Toast.LENGTH_SHORT).show();
                            getData(option);
                            tvResult.setText("");

                        }
                    });
                }
            }
        });



        }





    public static  int getConnStatus(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }
    public static String getConnectivityStatusString(Context context) {
        int conn =MainActivity.getConnStatus(context);
        String status = null;
        if (conn == MainActivity.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == MainActivity.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == MainActivity.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }


    public void getData(String option)
    {
        JsonTask jt = new JsonTask();
        String url="http://numbersapi.com/random/"+ this.option;

        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        jt.execute(url);

    }



    public   class JsonTask extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... params) {

            try {
                URL url=new URL(params[0]);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                InputStream isb=conn.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(isb));

                data=reader.readLine();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvResult.setText(s);
        }
    }

    public void displayDetails(String s)
    {
        if(s!=null)
            tvResult.setText(s);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this Application?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.setTitle("Exit");
        alert.show();

    }
}
