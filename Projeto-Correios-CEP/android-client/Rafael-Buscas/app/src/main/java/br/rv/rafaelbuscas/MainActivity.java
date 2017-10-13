package br.rv.rafaelbuscas;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.planets_spinner) Spinner spinner;
    @BindView( R.id.table) TableLayout table;
    @BindView( R.id.input_busca) EditText input;
    @BindView( R.id.th0) TextView th0;
    @BindView( R.id.th1) TextView th1;
    @BindView( R.id.th2) TextView th2;
    @BindView( R.id.td0) TextView td0;
    @BindView( R.id.td1) TextView td1;
    @BindView( R.id.td2) TextView td2;
    @BindView( R.id.code) TextView code;
    Context context;
    private ArrayAdapter<CharSequence> adapter;
    private List<CharSequence> items;
    private String[] strings;
    private String urlCep = "http://rafaelbuscas.ddns.net/api/correios/json/cep?cep=";
    private String urlRastro1 = "http://www.json-generator.com/api/json/get/bQoNZLBLZu?indent=2";
    private String urlRastro2 = "http://httpbin.org/get";
    Map<String, String> params = new HashMap<>();
    JSONArray js = new JSONArray();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        strings = getResources().getStringArray(R.array.planets_array);
        items = new ArrayList<CharSequence>(Arrays.asList(strings));
        adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, items);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        table.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.botao_busca)
    public void buscar() {
        urlCep+=input.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.PUT,
                urlRastro1,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{
                                JSONObject cep = response.getJSONObject(0);

                                // Get the current student (json object) data
                                String cidade = cep.getString("cidade");
                                String bairro = cep.getString("bairro");
                                String uf = cep.getString("uf");
                            th0.setText("Cidade");
                            th1.setText("Bairro");
                            th2.setText("UF");
                            td0.setText(cidade);
                            td1.setText(bairro);
                            td2.setText(uf);
                            code.setText(input.getText().toString());
                            table.setVisibility(View.VISIBLE);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);

    }

//    @OnClick(R.id.clear_spinner_data)
//    void clearSpinnerData() {
//        adapter.clear();
//    }
//
//    @OnClick(R.id.set_spinner_data)
//    void setSpinnerData() {
//        items = new ArrayList<CharSequence>(Arrays.asList(strings));
//        adapter.addAll(items);
//
//    }
//
    @OnItemSelected(R.id.planets_spinner)
        //default callback : ITEM_SELECTED
    void onItemSelected(int position) {
        if(position==0){
            Toast.makeText(this, "CEP Selecionado", Toast.LENGTH_SHORT).show();
        }else if(position==1){
            Toast.makeText(this, "Rastro1 Selecionado", Toast.LENGTH_SHORT).show();

        }else if(position==2){
            Toast.makeText(this, "Rastro2 Selecionado", Toast.LENGTH_SHORT).show();

        }

    }
//
//    @OnItemSelected(value = R.id.my_spinner, callback = OnItemSelected.Callback.NOTHING_SELECTED)
//    void onNothingSelected() {
//        Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();
//    }

}
