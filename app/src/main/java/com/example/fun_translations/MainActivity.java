package com.example.fun_translations;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private TranslationAdapter translationAdapter;
    FloatingActionButton fab;
    private final List<TranslationResponse> translationResponses = new ArrayList<>();

    // Realiza uma tradução com o retorno da activity
    private final ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // Recuperar os valores retornados
                    TranslationResponse translationResponse = result.getData().getParcelableExtra("translation");
                    if(translationResponse == null){
                        Toast.makeText(MainActivity.this, "Falha em adicionar nova tradução", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    translationResponses.add(translationResponse);
                    translationAdapter.notifyItemInserted(translationResponses.size() - 1);
                    Toast.makeText(MainActivity.this, "Nova tradução adicionada", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        recyclerView = findViewById(R.id.recyclerView);

        translationAdapter = new TranslationAdapter(translationResponses, position -> {
            // Remova o item da lista
            translationResponses.remove(position);
            translationAdapter.notifyItemRemoved(position);
            translationAdapter.notifyItemRangeChanged(position, translationResponses.size());
        });

        recyclerView.setAdapter(translationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                LinearLayout.VERTICAL);  // ou LinearLayout.HORIZONTAL, dependendo do seu layout
        recyclerView.addItemDecoration(dividerItemDecoration);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // Adiciona uma nova tradução ao clicar no FAB
            Intent intent = new Intent(MainActivity.this, TraduzirActivity.class);
            activityLauncher.launch(intent);
        });
    }
}