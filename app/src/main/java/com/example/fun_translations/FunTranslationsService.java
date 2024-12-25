package com.example.fun_translations;

import android.view.translation.TranslationResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FunTranslationsService {
    @FormUrlEncoded
    @POST("translate/yoda.json")
    Call<TranslationResponse> translateToYoda(@Field("text") String text);
}