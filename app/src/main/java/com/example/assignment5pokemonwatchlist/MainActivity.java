package com.example.assignment5pokemonwatchlist;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPokemon = findViewById(R.id.editTextPokemon);
        Button buttonAddPokemon = findViewById(R.id.buttonAddPokemon);

        buttonAddPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPokemonToWatchlist();
            }
        });

        // Initialize other UI components and set their listeners
    }

    private void addPokemonToWatchlist() {
        String pokemonNameOrId = editTextPokemon.getText().toString().trim();

        // Validate the input
        if (isValidPokemon(pokemonNameOrId)) {
            // TODO: Add the Pokemon to the watchlist
            // TODO: Show corresponding profile
        } else {
            // Display a Toast for invalid Pokemon
            Toast.makeText(this, "Invalid Pokemon", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPokemon(String input) {
        // TODO: Implement validation logic based on the provided criteria
        return true; // Placeholder, update as needed
    }

    // Implement other methods for watchlist and profile display
}
