package com.example.hp.practice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import static com.example.hp.practice.R.*;

public class MainActivity extends AppCompatActivity {

    String a[]={"Sumit","Shrinath","Namit","Amit"};
    ListView ls;
    Button btnpop;
    TextView tv;
    DatePicker datepicker;
    TimePicker timepicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);


        btnpop= (Button) findViewById(id.btnpop);

        datepicker= (DatePicker) findViewById(id.datePicker);
        timepicker= (TimePicker) findViewById(id.timePicker);

        tv= (TextView) findViewById(id.tv);

        btnpop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressBar = new ProgressDialog(MainActivity.
                        this);
                progressBar.setCancelable(true);//you can cancel it by pressing back button
                progressBar.setMessage("File downloading ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.setProgress(0);//initially progress is 0
                progressBar.setMax(100);//sets the maximum value 100
                progressBar.show();//displays the progress bar

                tv.setText(getCurrentTime());

    }
        });
    }


    public String getCurrentTime()
    {
        StringBuffer sb=new StringBuffer(" ");
        sb.append("Current Month: ");
        sb.append(datepicker.getMonth()+1+"\n");
        sb.append("Current Day :"+ datepicker.getDayOfMonth()+"\n  "+" ");
        String  time=" "+timepicker.getCurrentHour()+timepicker.getCurrentMinute();
        sb.append(time);


        return sb.toString();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK)
        {
            if(requestCode==100)
            {
                if(data!=null)
                {
                    String msg=data.getStringExtra("name");
                    tv.setText("Name is "+msg);
                }
            }
        }




    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");
        menu.add(0,v.getId(),0,"Call");
        menu.add(0,v.getId(),0,"Sms");
        menu.add(0,v.getId(),0,"Email");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle()=="Call")
        {
            Toast.makeText(getApplicationContext(), "Calling code", Toast.LENGTH_SHORT).show();
        }
        else if(item.getTitle()=="SMS")
        {
            Toast.makeText(getApplicationContext(), "Calling SMS", Toast.LENGTH_SHORT).show();
        }else if(item.getTitle()=="Email")
        {
            Toast.makeText(getApplicationContext(), "Caliing email", Toast.LENGTH_SHORT).show();
        }
        else
        return  false;

        return true;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case id.item1:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;

            case id.item2:
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;

            case id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
