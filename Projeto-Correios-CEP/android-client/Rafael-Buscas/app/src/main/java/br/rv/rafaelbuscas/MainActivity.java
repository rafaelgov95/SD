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
    int atual;
    private ArrayAdapter<CharSequence> adapter;
    private List<CharSequence> items;
    private String[] strings;
    private String urlCep = "http://rafaelbuscas.ddns.net/api/correios/json/cep/";
    private String urlRastroSoap = "http://rafaelbuscas.ddns.net/api/correios/json/objeto/";
    private String urlRastroalt = "http://rafaelbuscas.ddns.net/api/correios/json/aobjeto/";
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        if(atual==0){
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    urlCep+=input.getText().toString(),
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
                                Toast.makeText(context, "Sucesso !", Toast.LENGTH_SHORT).show();

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
            requestQueue.add(jsonArrayRequest);

        }else if(atual==1){
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    urlRastroSoap+=input.getText().toString(),
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // Process the JSON
                            try{
                                JSONObject soap = response.getJSONObject(0);
                                JSONObject  evento = soap.getJSONObject("objeto").getJSONObject("evento");
                                  // Get the current student (json object) data
                                String descricao = evento.getString("descricao");
                                String cidade = evento.getString("cidade");
                                String data = evento.getString("data");
                                th0.setText("Situação");
                                th1.setText("Local");
                                th2.setText("Data");
                                td0.setText(descricao);
                                td1.setText(cidade);
                                td2.setText(data);
                                code.setText(input.getText().toString());
                                table.setVisibility(View.VISIBLE);
                                Toast.makeText(context, "Sucesso !", Toast.LENGTH_SHORT).show();

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
            requestQueue.add(jsonArrayRequest);

        }else if(atual==2){
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    urlRastroalt+=input.getText().toString(),
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // Process the JSON
                            try{
                                JSONObject rest = response.getJSONObject(0);

                                // Get the current student (json object) data
                                String situacao = rest.getString("situacao");
                                String local = rest.getString("local");
                                String data = rest.getString("data");
                                th0.setText("Situação");
                                th1.setText("Local");
                                th2.setText("Data");
                                td0.setText(situacao);
                                td1.setText(local);
                                td2.setText(data);
                                code.setText(input.getText().toString());
                                table.setVisibility(View.VISIBLE);
                                Toast.makeText(context, "Sucesso !", Toast.LENGTH_SHORT).show();

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
            requestQueue.add(jsonArrayRequest);

        }




        // Add JsonArrayRequest to the RequestQueue

    }

    @OnItemSelected(R.id.planets_spinner)
        //default callback : ITEM_SELECTED
    void onItemSelected(int position) {
        code.setText("");
        table.setVisibility(View.INVISIBLE);

        if(position==0){
            atual=position;
            Toast.makeText(this, "CEP Selecionado", Toast.LENGTH_SHORT).show();
        }else if(position==1){
            atual=position;
            Toast.makeText(this, "Rastreio SOAP Selecionado", Toast.LENGTH_SHORT).show();
        }else if(position==2){
            atual=position;
            Toast.makeText(this, "Rastreio REST Selecionado", Toast.LENGTH_SHORT).show();

        }

    }

}
