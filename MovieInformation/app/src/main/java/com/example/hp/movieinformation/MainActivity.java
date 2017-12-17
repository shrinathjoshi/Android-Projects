package com.example.hp.movieinformation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText etMovieName;
    TextView tvResult;
    Button btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMovieName= (EditText) findViewById(R.id.etMovieName);
        tvResult= (TextView) findViewById(R.id.tvResult);
        btnSearch= (Button) findViewById(R.id.btnSearch);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String movieName = etMovieName.getText().toString();

                if(movieName.length()==0)
                {
                    etMovieName.setError("Movie Name empty");
                    etMovieName.requestFocus();
                    return;
                }

                ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

                if(!((networkInfo!=null)&&(networkInfo.isConnected())))
                {
                    Toast.makeText(MainActivity.this, "Check for Internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                Task1 t=new Task1();
                t.execute("http://www.omdbapi.com/?s="+movieName);

            }
        });


    }

    class Task1 extends AsyncTask<String,Void,String>
    {
        String jsonstr1 ="";
        String line="";

        @Override
        protected String doInBackground(String... s) {

            String res="";

            try
            {

                URL url=new URL(s[0]);
                HttpURLConnection con= (HttpURLConnection) url.openConnection();


                InputStream is=con.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br =new BufferedReader(isr);


                while((line=br.readLine())!=null)
                {
                    jsonstr1=jsonstr1+line+"\n";
                }

                if(jsonstr1!=null)
                {
                    JSONObject jsonObject=new JSONObject(jsonstr1);
                    JSONArray jsonArray=jsonObject.getJSONArray

                            ("Search");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject movie=jsonArray.getJSONObject(i);
                        String title=movie.getString("Title");
                        String year=movie.getString("Year");
                        res=res+title+" "+year+"\n";
                    }

                }


            }catch (Exception e)
            {

            }

            return res;
        }//end of doinBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvResult.setText(s);
        }
    }







}
