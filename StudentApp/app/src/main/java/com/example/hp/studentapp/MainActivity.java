package com.example.hp.studentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etRollno,etName;
    Button btnAdd,btnView, btnDelete,btnUpdate;
    TextView tvStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etRollno= (EditText) findViewById(R.id.etRollno);
        etName= (EditText) findViewById(R.id.etName);
        btnAdd= (Button) findViewById(R.id.btnAdd);
        btnDelete= (Button) findViewById(R.id.btnDelete);
        btnUpdate= (Button) findViewById(R.id.btnUpdate);
        btnView= (Button) findViewById(R.id.btnView);

        tvStudent= (TextView) findViewById(R.id.tvStudent);

        final DbHandler dbh=new DbHandler(this);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int rno=Integer.parseInt(etRollno.getText().toString());
                String name=etName.getText().toString();

                dbh.addStudent(rno,name);
                etName.setText("");
                etRollno.setText("");
            }

        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data=dbh.viewStudent();
                tvStudent.setText(data);
            }
        });




        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rno=Integer.parseInt(etRollno.getText().toString());
                String name=etName.getText().toString();

                dbh.updateStudent(rno,name);
                etName.setText("");
                etRollno.setText("");

            }
        });



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int rno=Integer.parseInt(etRollno.getText().toString());
                dbh.deleteStudent(rno);

                etName.setText("");
                etRollno.setText("");
            }
        });
    }
}
