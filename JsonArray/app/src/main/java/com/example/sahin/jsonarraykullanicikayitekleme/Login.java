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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;

public class Login extends AppCompatActivity {

    EditText etmail,etsifre;
    String mail;
    String sifre;
    Intent in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void opYenikayit(View view)
    {

        in=new Intent(Login.this,MainActivity.class);
        startActivity(in);
    }

    public void opGiris(View view)
    {
        etmail=(EditText)findViewById(R.id.txtloginmail);
        etsifre=(EditText)findViewById(R.id.txtloginsifre);
        mail=etmail.getText().toString();
        sifre=etsifre.getText().toString();

        String url = "http://jsonbulut.com/json/userLogin.php?ref=cb226ff2a31fdd460087fedbb34a6023&" +
                "userEmail="+mail+
                "&userPass="+sifre+
                "&face=no";
        etmail.setText("");
        etsifre.setText("");



        new jsonDatax(url,Login.this).execute();

    }

    class jsonDatax extends AsyncTask<Void,Void,Void>
    {
        String url = "";
        String data = "";
        Context cnx;

        ProgressDialog pro;
        public jsonDatax(String url, Context cnx)
        {
            this.url = url;
            this.cnx = cnx;
            pro = new ProgressDialog(cnx);
            pro.setMessage("İşlem yaplıyor. Lütfen Bekleyiniz.");
            pro.show();
            Log.e("x","Burasi ikinci kısım");
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

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("Gelen Data", data);
            try
            {
                JSONObject obj = new JSONObject(data);
                Log.e("Gelen obje", obj.toString());
                boolean durum = obj.getJSONArray("user").getJSONObject(0).getBoolean("durum");
                String kisiadi = obj.getJSONArray("user").getJSONObject(0).getJSONObject("bilgiler").getString("userName");
                String kisisoyadi = obj.getJSONArray("user").getJSONObject(0).getJSONObject("bilgiler").getString("userSurname");
                String kisitelefon = obj.getJSONArray("user").getJSONObject(0).getJSONObject("bilgiler").getString("userPhone");




                if (durum)
                {
                    Log.e("x","Buraya girdimi");
                    Toast.makeText(cnx, "Profil sayfasına yönlendiriliyorsunuz", Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(Login.this,Profilim.class);
                    in.putExtra("mail",mail);
                    in.putExtra("kisitelefon",kisitelefon);
                    in.putExtra("kisiadi",kisiadi);
                    in.putExtra("kisisoyadi",kisisoyadi);
                    startActivity(in);


                }
                else
                {
                    Toast.makeText(cnx, "Girilen bilgiler yanlış", Toast.LENGTH_SHORT).show();
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
