package com.example.kisileruygulamasi.retrofit

class ApiUtils {
    companion object {
        val BASE_URL = "http://10.0.2.2:5000/"

        fun getKisilerDao() : KisilerDao {
            return RetrofitClient.getClient(BASE_URL).create(KisilerDao::class.java)
        }
    }
}