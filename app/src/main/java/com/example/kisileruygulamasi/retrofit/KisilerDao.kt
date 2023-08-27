package com.example.kisileruygulamasi.retrofit

import com.example.kisileruygulamasi.data.entity.CRUDCevap
import com.example.kisileruygulamasi.data.entity.KisilerCevap
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface KisilerDao {
    //http://kasimadalan.pe.hu/kisiler/tum_kisiler.php
    //http://kasimadalan.pe.hu/ -> base url
    //kisiler/tum_kisiler.php -> api url

    @GET("flights")
    suspend fun kisileriYukle() : KisilerCevap

    @POST("kisiler/insert_kisiler.php")
    @FormUrlEncoded
    suspend fun kaydet(@Field("kisi_ad")kisi_ad: String, @Field("kisi_tel")kisi_tel: String): CRUDCevap

    @POST("kisiler/update_kisiler.php")
    @FormUrlEncoded
    suspend fun guncelle(@Field("kisi_id")kisi_id: Int,
                         @Field("kisi_ad")kisi_ad: String,
                         @Field("kisi_tel")kisi_tel: String): CRUDCevap


    @GET("filter_flight/{filterLetter}")
    suspend fun ara(@Path("filterLetter") filterLetter: String): KisilerCevap

    @POST("kisiler/delete_kisiler.php")
    @FormUrlEncoded
    suspend fun sil(@Field("kisi_id")kisi_id: Int): CRUDCevap


}