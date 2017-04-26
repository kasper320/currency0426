package com.sdaacademy.kasperek.andrzej.urlreader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private String urlText;
    private String firstCurrency;
    private String secondCurrency;
    private ArrayList<Currency> arrayList;
    private ArrayAdapter<Currency> customAdapter;
    @BindView(R.id.get)
    Button get;
    @BindView(R.id.urlEditText)
    EditText urlEditText;
    @BindView(R.id.show)
    ListView show;
    @BindView(R.id.fromEditText)
    EditText fromEditText;
    @BindView(R.id.toEditText)
    EditText toEditText;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        arrayList = new ArrayList<>();
        customAdapter = new ArrayAdapter<>(this, R.layout.single_row, arrayList);
        show.setAdapter(customAdapter);
        show.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position,
                                    long id) {
                arrayList.remove(position); // remove the item from the data list
                 customAdapter.notifyDataSetChanged();

            }
        });

    }

    @OnClick
    public void onClick(View view) {

        new MyAsync().execute(fromEditText.getText().toString().toUpperCase(), toEditText.getText().toString().toUpperCase());
        firstCurrency = String.valueOf(fromEditText.getText()).toUpperCase();
        secondCurrency = String.valueOf(toEditText.getText()).toUpperCase();
    }

    private class MyAsync extends AsyncTask<String, Void, String> {

        private String readStream;
        private Currency currency;
        private StringBuilder stringBuilder;


        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            URL url = null;
            String urlAddress = "http://api.fixer.io/latest?base=" + firstCurrency +"&symbols=" + secondCurrency;
            try {
                url = new URL(urlAddress);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                readStream = readStream(in);

            } catch (IOException e) {
                e.printStackTrace();
            } finally

            {
                urlConnection.disconnect();
            }

            return readStream;
        }

        private String readStream(InputStream in) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                String baseCurrency = jsonObject.getString("base");
                JSONObject rates = jsonObject.getJSONObject("rates");
                Iterator<String> keys = rates.keys();

                while (keys.hasNext()) {
                    currency = new Currency();
                    currency.setBaseCurrency(baseCurrency);
                    String key = keys.next();
                    currency.setTargetCurrency(key);
                    double currencyValue = Double.parseDouble(rates.getString(key));
                    currency.setValueOfCurrency(currencyValue);
                    arrayList.add(currency);
                    customAdapter.notifyDataSetChanged();

                }
                //show.setText(stringBuilder.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}







