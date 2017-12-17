package com.example.hp.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    TextView tvWelcome;
    CheckBox cbPizza,cbBurger,cbDosa,cbCoke;
    Button btnOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvWelcome= (TextView) findViewById(R.id.tvWelcome);
        cbBurger= (CheckBox) findViewById(R.id.cbBurger);
        cbCoke= (CheckBox) findViewById(R.id.cbCoke);
        cbDosa= (CheckBox) findViewById(R.id.cbDosa);
        cbPizza= (CheckBox) findViewById(R.id.cbPizza);
        btnOrder= (Button) findViewById(R.id.btnOrder);


        Intent i =getIntent();
        String welcome="Welcome "+i.getStringExtra("name");
        tvWelcome.setText(welcome);


        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String order="";
                if(cbPizza.isChecked())
                    order=order+" PIZZA ";

                if(cbDosa.isChecked())
                    order=order+" DOSA ";

                if(cbCoke.isChecked())
                    order=order+" COKE ";

                if(cbBurger.isChecked())
                    order=order+" BURGER ";

                Toast.makeText(SecondActivity.this, "Your Order\n"+order, Toast.LENGTH_SHORT).show();


                Intent n=new Intent(SecondActivity.this,ThirdActivity.class);
                n.putExtra("order",order);
                startActivity(n);




            }
        });
    }
}
