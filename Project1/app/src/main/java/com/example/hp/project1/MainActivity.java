package com.example.hp.project1;

import android.content.Intent;
import android.media.MediaCodec;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etName,etPhone,etEmail,etCity,etReview;
    Spinner spnSubject;
    RatingBar rbRating;
    RadioGroup  rgGender;
    RadioButton rbMale,rbFemale;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName= (EditText) findViewById(R.id.etName);
        etPhone= (EditText) findViewById(R.id.etPhone);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etCity= (EditText) findViewById(R.id.etCity);
        etReview= (EditText) findViewById(R.id.etReview);

        spnSubject= (Spinner) findViewById(R.id.spnSubject);

        rbRating= (RatingBar) findViewById(R.id.rbRating);

        rgGender= (RadioGroup) findViewById(R.id.rgGender);

        rbMale= (RadioButton) findViewById(R.id.rbMale);
        rbFemale= (RadioButton) findViewById(R.id.rbFemale);

        btnSubmit= (Button) findViewById(R.id.btnSubmit);


        final ArrayList<String>  subject=new ArrayList<>();
        subject.add("Java");
        subject.add("Android");
        subject.add("Oracle");
        subject.add("Sap");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subject);
        spnSubject.setAdapter(adapter);







        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=etName.getText().toString();
                String phone=etPhone.getText().toString();
                String email=etEmail.getText().toString();

                String gender =rgGender.getCheckedRadioButtonId()==R.id.rbMale?"Male":"Female";

                String City=etCity.getText().toString();

                String sub=subject.get(spnSubject.getSelectedItemPosition());
                String rating=String.valueOf(rbRating.getRating());
                String review=etReview.getText().toString();

                if(name.length()==0)
                {
                    etName.setError("NAme is Empty");
                    Toast.makeText(MainActivity.this, "Name is Empty", Toast.LENGTH_SHORT).show();
                    etName.requestFocus();
                    return;
                }

                if(phone.length()!=10)
                {
                    Toast.makeText(MainActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                    etPhone.setText("");
                    etPhone.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(MainActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    etEmail.setText("");
                    etEmail.requestFocus();
                    return;

                }

                String msg="Name : "+name+"\n" +"Phone No : "+phone+"\n"
                        +"Email Id "+email+"\n"+"Gender : "+gender+ "\n"
                        +"City : "+City+"\n"+"Subject : "+ sub+"\n"
                        +"Rating : "+rating +"\n"+"Review : "+ review;


                Toast.makeText(MainActivity.this,msg, Toast.LENGTH_SHORT).show();


                Intent i =new Intent(MainActivity.this,ShareActivity.class);
                i.putExtra("msg",msg);
                i.putExtra("email",email);
                startActivity(i);


            }
        });




    }
}
