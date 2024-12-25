package com.example.fun_translations;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FunTranslationsService {
    @FormUrlEncoded
    @POST("translate/yoda.json")
    Call<TranslationResponse> translateToYoda(@Field("text") String text);

    @FormUrlEncoded
    @POST("translate/minion.json")
    Call<TranslationResponse> translateToMinion(@Field("text") String text);

    @FormUrlEncoded
    @POST("translate/doge.json")
    Call<TranslationResponse> translateToDoge(@Field("text") String text);

    @FormUrlEncoded
    @POST("translate/groot.json")
    Call<TranslationResponse> translateToGroot(@Field("text") String text);

    @FormUrlEncoded
    @POST("translate/shakespeare.json")
    Call<TranslationResponse> translateToShakespeare(@Field("text") String text);
}