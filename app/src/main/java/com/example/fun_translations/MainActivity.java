package com.example.fun_translations;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private TranslationAdapter translationAdapter;
    FloatingActionButton fab;
    List<Translation> translations;
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

        String url = "https://api.funtranslations.com/";
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        recyclerView = findViewById(R.id.recyclerView);
        translations = new ArrayList<>();

        // Adicione itens à lista
        translations.add(new Translation("Olá, mundo!", "Autor 1"));
        translations.add(new Translation("Hello, world!", "Author 2"));

        translationAdapter = new TranslationAdapter(translations, position -> {
            // Remova o item da lista
            if (position >= 0 && position < translations.size()) {
                translations.remove(position);
            }
            translationAdapter.notifyItemRemoved(position);
            translationAdapter.notifyItemRangeChanged(position, translations.size());
        });

        recyclerView.setAdapter(translationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // Adiciona uma nova tradução ao clicar no FAB
            translations.add(new Translation("Nova tradução", "Novo autor"));
            translationAdapter.notifyItemInserted(translations.size() - 1);
            recyclerView.smoothScrollToPosition(translations.size() - 1);
            Toast.makeText(this, "Nova tradução adicionada", Toast.LENGTH_SHORT).show();
        });
    }
}