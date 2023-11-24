package com.example.assignment5pokemonwatchlist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.*;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonAddPokemon;
    private EditText editTextPokemon;
    private TextView searchBar, PokeName, pokedexIdTV, weightTV, heightTV, baseXPTV, moveTV, abilityTV,
            pokedexIDText, weightText, heightText, baseXPText, moveText, abilityText;
    private ImageView logoView;
    private ListView pokeList;
    private static final String URL = "https://pokeapi.co/docs/v2/pokemon/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());

        buttonAddPokemon = findViewById(R.id.buttonAddPokemon);
        buttonAddPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addPokemonToWatchlist();
            }
        });

        editTextPokemon = findViewById(R.id.editTextPokemon);
        searchBar = findViewById(R.id.searchBar);

        PokeName = findViewById(R.id.PokeName);
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
        pokeList = findViewById(R.id.pokeList);

    }


    private void addPokemonToWatchlist(Pokemon pokemon) {
        String pokemonNameOrId = editTextPokemon.getText().toString();

        // Validate the input
        if (isValidPokemon(pokemonNameOrId)) {
            // TODO: Add the Pokemon to the watchlist
            // TODO: Show corresponding profile
            addToWatchlist(pokemon);
        } else {
            // Display a Toast for invalid Pokemon
            Toast.makeText(this, "Invalid Pokemon", Toast.LENGTH_SHORT).show();

        }
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


    private void addToWatchlist(Pokemon pokemon){
        //TODO: add to list


        updateFields(pokemon);
    }

    private void updateFields(Pokemon pokemon){
        //TODO: update texts to display info for chosen pokemon
        PokeName.setText(pokemon.getName());
        pokedexIDText.setText(String.valueOf(pokemon.getId()));
        weightText.setText(String.valueOf(pokemon.getWeight()));
        heightText.setText(String.valueOf(pokemon.getHeight()));
    }

    //TODO: if pokemon selected from listview add info

    private void makeRequest(String pokemon){
        ANRequest req = AndroidNetworking.get(URL+"{pokemon}")
                .addPathParameter("pokemon", pokemon)
                .setPriority(Priority.LOW)
                .build();

    }
}
