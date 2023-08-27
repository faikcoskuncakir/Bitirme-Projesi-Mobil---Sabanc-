package com.example.kisileruygulamasi.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kisileruygulamasi.R
import com.example.kisileruygulamasi.data.entity.Kisiler
import com.example.kisileruygulamasi.databinding.FragmentKisiDetayBinding
import com.example.kisileruygulamasi.databinding.FragmentKisiKayitBinding
import com.example.kisileruygulamasi.ui.adapter.RezervasyonlarAdapter
import com.example.kisileruygulamasi.ui.viewmodel.KisiKayitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

@AndroidEntryPoint
class KisiKayitFragment : Fragment() {
    private lateinit var binding: FragmentKisiKayitBinding
    private lateinit var viewModel: KisiKayitViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_kisi_kayit, container, false)
        binding.kisiKayitFragment = this
        binding.kisiKayitToolbarBaslik = "My Reservations"
        binding.rv2.layoutManager = LinearLayoutManager(requireContext())

        val benimRezervasyonlarım = ArrayList<Kisiler>()

        val adapter = RezervasyonlarAdapter(requireContext(), benimRezervasyonlarım)
        binding.rv2.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = RetrofitClient.apiService.getReservations()
                if (response.isNotEmpty()) {
                    benimRezervasyonlarım.clear()
                    benimRezervasyonlarım.addAll(response)
                    adapter.notifyDataSetChanged()
                } else {
                    // API yanıtı boş ise bir mesaj gösterilebilir
                    // toast veya log kullanılabilir
                }
            } catch (e: Exception) {
                // Handle exception here
                Log.e("ApiRequest", "Error: ${e.message}", e)
                // Hata mesajını kullanıcıya göstermek için toast veya diyalog kullanılabilir
            }
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:KisiKayitViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun kaydet(kisi_ad:String,kisi_tel:String){
        viewModel.kaydet(kisi_ad,kisi_tel)
    }

    interface ApiService {
        @GET("reservations")
        suspend fun getReservations(): List<Kisiler> // 'Kisiler' modelinize uygun bir veri türü olmalı
    }

    object RetrofitClient {
        private const val BASE_URL = "http://10.0.2.2:5000/"

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiService = retrofit.create(ApiService::class.java)
    }
}