package com.example.assignment5pokemonwatchlist;


import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonAddPokemon;
    private EditText editTextPokemon;
    private TextView pokeName, pokedexIdTV, weightTV, heightTV, baseXPTV, moveTV, abilityTV,
            pokedexIDText, weightText, heightText, baseXPText, moveText, abilityText;
    private ImageView logoView;
    private ListView pokeListView;
    private static String URL = "https://pokeapi.co/docs/v2/pokemon/";
    LinkedList<String> pokemonList = new LinkedList<>();
    ArrayAdapter<String> adapter;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());

        buttonAddPokemon = findViewById(R.id.buttonAddPokemon);
        buttonAddPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pokemon = editTextPokemon.getText().toString();
                isValidPokemon(pokemon);
                getPoke(pokemon);
                pokeName.setText(pokemon);
                pokemonList.add(pokemon);
            }
        });

        editTextPokemon = findViewById(R.id.editTextPokemon);

        pokeName = findViewById(R.id.PokeName);
        pokedexIdTV = findViewById(R.id.pokedexIdTV);
        weightTV = findViewById(R.id.weightTV);
        heightTV = findViewById(R.id.heightTV);
        baseXPTV = findViewById(R.id.baseXPTV);
        moveTV = findViewById(R.id.moveTV);
        abilityTV = findViewById(R.id.abilityTV);

        pokedexIDText = findViewById(R.id.pokedexIDText);
        weightText = findViewById(R.id.weightText);
        heightText = findViewById(R.id.heightText);
        baseXPText = findViewById(R.id.baseXPText);
        moveText = findViewById(R.id.moveText);
        abilityText = findViewById(R.id.abilityText);

        logoView = findViewById(R.id.logoView);
        pokeListView = findViewById(R.id.pokeList);
        pokeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pokemon = (String) parent.getItemAtPosition(position);

                pokeName.setText(pokemon);
                getPoke(pokemon);
            }
        });

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, pokemonList);
        pokeListView.setAdapter(adapter);

        mQueue = Volley.newRequestQueue(this);
    }


    private boolean isValidPokemon(String input) {
        // check for invalid characters
        if (input.matches(".*[%&*()!;:<>\"].*")) {
            return false;
        }
        // check if it's a number
        if (input.matches("\\d+")) {
            int number = Integer.parseInt(input);
            // check if the number is negative or greater than 1010
            if (number < 0 || number > 1010) {
                return false;
            }
        }
        // all checks passed
        return true;
    }

    private void makeRequest(String pokemon){
        ANRequest req = AndroidNetworking.get(URL+"{pokemon}")
                .addPathParameter("pokemon", pokemon)
                .setPriority(Priority.LOW)
                .build();
        req.getAsObjectList(Pokemon.class, new ParsedRequestListener<List<Pokemon>>() {
            @Override
            public void onResponse(List<Pokemon> pokemons) {
                for (Pokemon p : pokemons) {
                    pokedexIDText.setText(p.getId());
                    weightText.setText(p.getWeight());
                    heightText.setText(p.getHeight());
                    baseXPText.setText(p.getBase());
                    Toast.makeText(getApplicationContext(),"API Call Successful", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onError(ANError anError) {
                // handle error
                Toast.makeText(getApplicationContext(),"Error on getting data ", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getPoke(String p){
        PokemonHelper.getPokemonInfo(getApplicationContext(), p, new PokemonHelper.PokemonCallback() {
            @Override
            public void onSuccess(String n, int w, int h, String t) {
                heightText.setText(h);
                weightText.setText(w);
                baseXPText.setText(t);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void makeReq(String pokemon){
        String url = URL + pokemon;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("forms");
                    String heightimport = response.getString("height");
                    String weightimport = response.getString("weight");
                    String baseimport = response.getString("base_experience");
                    heightText.setText(heightimport);
                    weightText.setText(weightimport);
                    baseXPText.setText(baseimport);

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject nameObj = jsonArray.getJSONObject(i);

                        String nameimport = nameObj.getString("name");
                        pokeName.setText(nameimport);
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error on getting data ", Toast.LENGTH_LONG).show();
            }
        });
    }
//    private void addPokemonToWatchlist(Pokemon pokemon) {
//        String pokemonNameOrId = editTextPokemon.getText().toString();
//
//        // Validate the input
//        if (isValidPokemon(pokemonNameOrId)) {
//            // TODO: Add the Pokemon to the watchlist
//            // TODO: Show corresponding profile
//            addToWatchlist(pokemon);
//        } else {
//            // Display a Toast for invalid Pokemon
//            Toast.makeText(this, "Invalid Pokemon", Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//


//    private void addToWatchlist(Pokemon pokemon){
//        //TODO: add to list
//
//
//        updateFields(pokemon);
//    }
//
//    private void updateFields(Pokemon pokemon){
//        //TODO: update texts to display info for chosen pokemon
//        pokeName.setText(pokemon.getName());
//        pokedexIDText.setText(String.valueOf(pokemon.getId()));
//        weightText.setText(String.valueOf(pokemon.getWeight()));
//        heightText.setText(String.valueOf(pokemon.getHeight()));
//    }
//


}