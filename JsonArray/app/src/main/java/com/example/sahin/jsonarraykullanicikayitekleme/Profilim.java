package com.example.sahin.jsonarraykullanicikayitekleme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profilim extends AppCompatActivity {

    TextView tvad,tvsoyad,tvmail,tvtel,tvad2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilim);
        Bundle extras=getIntent().getExtras();
        tvad=(TextView)findViewById(R.id.tvadi);
        tvmail=(TextView)findViewById(R.id.tvemail);
        tvsoyad=(TextView)findViewById(R.id.tvsoyadi);
        tvtel=(TextView)findViewById(R.id.telefon);
        String valuemail=extras.getString("mail");
        String valuekisitelefon=extras.getString("kisitelefon");
        String valuekisiadi=extras.getString("kisiadi");
        String valuekisisoyadi=extras.getString("kisisoyadi");
        tvad.setText(valuekisiadi);
        tvsoyad.setText(valuekisisoyadi);
        tvtel.setText(valuekisitelefon);
        tvmail.setText(valuemail);
    }
}
