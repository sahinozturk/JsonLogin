package com.example.sahin.jsonarraykullanicikayitekleme;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    EditText ad,soyad,tel,mail,sifre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ad = (EditText) findViewById(R.id.ad);
        soyad = (EditText) findViewById(R.id.soyad);
        tel = (EditText) findViewById(R.id.telefon);
        mail = (EditText) findViewById(R.id.mail);
        sifre = (EditText) findViewById(R.id.sifre);
    }

    public void Kaydet(View view)
    {
        String ad = this.ad.getText().toString();
        String soyad = this.soyad.getText().toString();
        String tel = this.tel.getText().toString();
        String mail = this.mail.getText().toString();
        String sifre = this.sifre.getText().toString();



        if(ad.isEmpty() || soyad.isEmpty() || tel.isEmpty() || mail.isEmpty() || sifre.isEmpty())
        {
            Toast.makeText(MainActivity.this, "Lütfen Boş Alanları Doldurunuz", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String url = "http://jsonbulut.com/json/userRegister.php?ref=cb226ff2a31fdd460087fedbb34a6023&" +
                    "userName="+ad+"&" +
                    "userSurname="+soyad+"&" +
                    "userPhone="+tel+"&" +
                    "userMail="+mail+"&" +
                    "userPass="+sifre+"";

            this.ad.setText("");
            this.soyad.setText("");
            this.tel.setText("");
            this.mail.setText("");
            this.sifre.setText("");

            new jsonData(url,MainActivity.this).execute();
        }

    }

    public void btngoster(View view)
    {
        Intent inten=new Intent(MainActivity.this,Login.class);
        startActivity(inten);
    }

    static class jsonData extends AsyncTask<Void,Void,Void>
    {
        String url = "";
        String data = "";
        Context cnx;

        ProgressDialog pro;
        public jsonData (String url, Context cnx)
        {
            this.url = url;
            this.cnx = cnx;
            pro = new ProgressDialog(cnx);
            pro.setMessage("İşlem yaplıyor. Lütfen Bekleyiniz.");
            pro.show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                data = Jsoup.connect(url).ignoreContentType(true).
                        get().body().text();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                Log.e("Data Json Hatası", "doinBackground: ", e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("Gelen Data", data);
            try
            {
                JSONObject obj = new JSONObject(data);
                boolean durum = obj.getJSONArray("user").getJSONObject(0).getBoolean("durum");
                String mesaj = obj.getJSONArray("user").getJSONObject(0).getString("mesaj");

                if (durum)
                {
                    Toast.makeText(cnx, mesaj, Toast.LENGTH_SHORT).show();
                    String kid =  obj.getJSONArray("user").getJSONObject(0).getString("kullaniciId");
                    Log.e("kid", kid);
                }
                else
                {
                    Toast.makeText(cnx, mesaj, Toast.LENGTH_SHORT).show();
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            pro.dismiss();
        }

    }

}
