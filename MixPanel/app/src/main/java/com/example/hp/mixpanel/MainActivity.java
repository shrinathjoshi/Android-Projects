package com.example.hp.mixpanel;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mixpanel.android.mpmetrics.MixpanelAPI;


public class MainActivity extends AppCompatActivity {

     TextView tvPayInvoice;
     TextInputLayout tilPaymentamount,tilNameOnCard,tilCardNumber,tilExpirey,tilSsnNo,tilZipCode;
     Button btnPay;
     MixpanelAPI mixpanel = null;
     int cond=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPayInvoice= (TextView) findViewById(R.id.tvPayInvoice);
        tilPaymentamount= (TextInputLayout) findViewById(R.id.tilPaymentAmount);
        tilCardNumber= (TextInputLayout) findViewById(R.id.tilCardNumber);
        tilExpirey= (TextInputLayout) findViewById(R.id.tilExpirey);
        tilNameOnCard= (TextInputLayout) findViewById(R.id.tilNameOnCard);
        tilSsnNo= (TextInputLayout) findViewById(R.id.tilSsnNo);
        tilZipCode= (TextInputLayout) findViewById(R.id.tilZipCode);
        btnPay= (Button) findViewById(R.id.btnPay);


        String projectToken = "af64bf6bf57b887d97762b7971a324c8"; // e.g.: "1ef7e30d2a58d27f4b90c42e31d6d7ad"
        mixpanel = MixpanelAPI.getInstance(this, projectToken);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String amount=tilPaymentamount.getEditText().toString();
                final String cardNo=tilCardNumber.getEditText().toString();
                final String Expirey=tilExpirey.getEditText().toString();
                final String name=tilNameOnCard.getEditText().toString();
                final String ssn=tilSsnNo.getEditText().toString();
                final String zip=tilZipCode.getEditText().toString();


                    btnPay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if(amount.length()==0)
                            {
                                cond=0;
                                tilPaymentamount.setErrorEnabled(true);
                                tilPaymentamount.setError("Amount cannot be Empty");
                                Toast.makeText(MainActivity.this, "\"Amount cannot be Empty\"", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if(cardNo.length()==0)
                            {
                                cond=0;

                                tilPaymentamount.setErrorEnabled(true);
                                tilPaymentamount.setError("Card Number cannot be Empty");
                                Toast.makeText(MainActivity.this, "Card Number cannot be Empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(Expirey.length()==0)
                            {
                                cond=0;

                                tilPaymentamount.setErrorEnabled(true);
                                tilPaymentamount.setError("Expirey Date cannot be Empty");
                                Toast.makeText(MainActivity.this, "Expirey Data cannot be Empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(name.length()==0)
                            {
                                cond=0;
                                tilPaymentamount.setErrorEnabled(true);
                                tilPaymentamount.setError("Name cannot be Empty");
                                Toast.makeText(MainActivity.this, "Card Number cannot be Empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(ssn.length()==0)
                            {
                                cond=0;

                                tilPaymentamount.setErrorEnabled(true);
                                tilPaymentamount.setError("Security Number cannot be Empty");
                                Toast.makeText(MainActivity.this, "Card Number cannot be Empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(zip.length()==0)
                            {
                                cond=0;

                                tilPaymentamount.setErrorEnabled(true);
                                tilPaymentamount.setError("Zip Number cannot be Empty");
                                Toast.makeText(MainActivity.this, "Card Number cannot be Empty", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            mixpanel.track("Button Click",null);

                                Toast.makeText(MainActivity.this, "Transaction Sucessful", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       mixpanel.flush();
    }
}

