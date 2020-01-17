package com.example.amst_2ep_grupo8_soto_vargas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class Resultado_Heroe extends AppCompatActivity {
    String url;
    RequestQueue request_queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado__heroe);

        request_queue = Volley.newRequestQueue(this);
        url = getIntent().getExtras().getString("url");
        consultarHeroe(url);
    }

    public void consultarHeroe(final String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {

            @SuppressLint("WrongViewCast")
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    LinearLayout contenedorReslurado = (LinearLayout)findViewById(R.id.linealBusqueda);

                    for(int i = 0; i < jsonArray.length(); i++){
                        final JSONObject heroe = (JSONObject) jsonArray.get(i);
                        final String nombre = heroe.getString("name");
                        TextView nameTextView = new TextView(getApplicationContext());
                        final JSONObject powers = heroe.getJSONObject("powerstats");
                        final String fullName = heroe.getJSONObject("biography").getString("full-name");

                        nameTextView.setText(nombre);

                        nameTextView.setClickable(true);
                        nameTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getApplicationContext(), GraficaActivity.class);
                                try {
                                    i.putIntegerArrayListExtra("valueStats", getValueStats(powers));
                                    i.putStringArrayListExtra("nameStats", getNameStats(powers));
                                } catch (JSONException e) {
                                    i.putIntegerArrayListExtra("valueStats", new ArrayList<Integer>());
                                    i.putStringArrayListExtra("nameStats", new ArrayList<String>());
                                }
                                i.putExtra("name", nombre);
                                i.putExtra("fullName", fullName);

                                startActivity(i);

                            }
                        });
                        nameTextView.setTextColor(Color.BLACK);
                        nameTextView.setTextSize(20);
                        contenedorReslurado.addView(nameTextView);


                    }
                    ((TextView)findViewById(R.id.total_resultados)).setText("RESULTADOS: " + String.valueOf(jsonArray.length()) );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(getApplicationContext(), "No existen coincidencias en su busqueda, por favor intentar de nuevo.", Toast.LENGTH_LONG).show();
            }
        });
        request_queue.add(jsonObjectRequest);
    }

    public ArrayList<Integer> getValueStats(JSONObject jsonObject) throws JSONException {
        ArrayList<Integer> values = new ArrayList<>();
        Iterator<String> iterator = jsonObject.keys();
        while(iterator.hasNext()){
            String name = iterator.next();
            Integer value = jsonObject.getInt(name);
            values.add(value);
        }
        return values;
    }

    public ArrayList<String> getNameStats(JSONObject jsonObject) throws JSONException {
        ArrayList<String> names = new ArrayList<>();
        Iterator<String> iterator = jsonObject.keys();
        while(iterator.hasNext()){
            String name = iterator.next();
            names.add(name);
        }
        return names;
    }
}
