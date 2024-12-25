package com.example.fun_translations;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TraduzirActivity extends AppCompatActivity {

    private EditText edTxtMultiLine;
    private RadioGroup radioGroup;
    private Button btnTraduzir;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_traduzir);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edTxtMultiLine = findViewById(R.id.edTxtMultiLine);
        radioGroup = findViewById(R.id.radioGroup);
        btnTraduzir = findViewById(R.id.btnTraduzir);
        String url = "https://api.funtranslations.com/";
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnTraduzir.setOnClickListener(view -> {
            String mensagem = edTxtMultiLine.getText().toString();
            if (mensagem.isEmpty()) {
                Toast.makeText(this, "Por favor, insira um texto", Toast.LENGTH_SHORT).show();
                return;
            }
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton rd = findViewById(selectedId);
            String idioma = rd.getText().toString();

            // Criar Intent para retornar os valores
            Intent resultIntent = new Intent();

            // Realizar a tradução
            FunTranslationsService service = retrofit.create(FunTranslationsService.class);
            Call<TranslationResponse> call;
            switch (idioma) {
                case "Yoda":
                    call = service.translateToYoda(mensagem);
                    break;
                case "Minions":
                    call = service.translateToMinion(mensagem);
                    break;
                case "Doge":
                    call = service.translateToDoge(mensagem);
                    break;
                case "Groot":
                    call = service.translateToGroot(mensagem);
                    break;
                default:
                    call = service.translateToShakespeare(mensagem);
                    break;
            }
            call.enqueue(new retrofit2.Callback<TranslationResponse>() {
                @Override
                public void onResponse(Call<TranslationResponse> call, retrofit2.Response<TranslationResponse> response) {
                    if (response.isSuccessful()) {
                        TranslationResponse translationResponse = response.body();
                        Toast.makeText(TraduzirActivity.this, "Tradução realizada com sucesso!!! " + translationResponse.contents.translated, Toast.LENGTH_SHORT).show();

                        resultIntent.putExtra("translation", translationResponse);

                        // Configurar o resultado e finalizar a Activity
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(TraduzirActivity.this, "Erro na resposta da API", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<TranslationResponse> call, Throwable throwable) {
                    Toast.makeText(TraduzirActivity.this, "Erro na tradução!!!", Toast.LENGTH_SHORT).show();
                    // Finalizar a Activity
                    setResult(RESULT_CANCELED, resultIntent);
                    finish();
                }
            });


        });
    }
}