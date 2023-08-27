package com.example.kisileruygulamasi.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.kisileruygulamasi.R
import com.example.kisileruygulamasi.databinding.FragmentAnasayfaBinding
import com.example.kisileruygulamasi.databinding.FragmentKisiDetayBinding
import com.example.kisileruygulamasi.ui.viewmodel.KisiDetayViewModel
import com.example.kisileruygulamasi.ui.viewmodel.KisiKayitViewModel
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

@AndroidEntryPoint
class KisiDetayFragment : Fragment() {
    private lateinit var binding: FragmentKisiDetayBinding
    private lateinit var viewModel: KisiDetayViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kisi_detay, container, false)
        binding.kisiDetayFragment = this
        binding.kisiDetayToolbarBaslik = "Flight Details"

        val bundle: KisiDetayFragmentArgs by navArgs()
        val gelenKisi = bundle.kisi
        binding.kisiNesnesi = gelenKisi

        // Set click listener for the button to call sendReservation
        binding.buttonGuncelle.setOnClickListener {
            try {
                // Launch a coroutine within the fragment's lifecycle scope
                viewLifecycleOwner.lifecycleScope.launch {
                    sendReservation(
                        gelenKisi.flight_number,
                        gelenKisi.destination_place,
                        gelenKisi.flight_date,
                        gelenKisi.flight_company,
                        gelenKisi.flight_cost,
                        gelenKisi.aircraft_type,
                        gelenKisi.flight_time,
                        gelenKisi.air_quality
                    )
                }
            } catch (e: Exception) {
                // Handle exceptions
            }
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: KisiDetayViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun guncelle(kisi_id:Int,kisi_ad:String,kisi_tel:String){
        viewModel.guncelle(kisi_id, kisi_ad, kisi_tel)
    }

    interface ApiService {
        @POST("/reservations")
        suspend fun postReservation(@Body reservationData: ReservationData): Response<ResponseBody>
    }

    data class ReservationData(
        @SerializedName("flight_number") val flightNumber: Int,
        @SerializedName("destination_place") val destinationPlace: String,
        @SerializedName("flight_date") val flightDate: String,
        @SerializedName("flight_company") val flightCompany: String,
        @SerializedName("flight_cost") val flightCost: String,
        @SerializedName("aircraft_type") val aircraftType: String,
        @SerializedName("flight_time") val flightTime: String,
        @SerializedName("air_quality") val airQuality: String,
    )

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    suspend fun sendReservation
                (
                    bir:Int,
                    iki:String,
                    üç:String,
                    dört:String,
                    beş:String,
                    altı:String,
                    yedi:String,
                    sekiz:String
                )
    {
        val reservationData = ReservationData(
            flightNumber = bir,
            destinationPlace = iki,
            flightDate = üç,
            flightCompany = dört,
            flightCost = beş,
            aircraftType = altı,
            flightTime = yedi,
            airQuality = sekiz
        )

        try {
            val response = apiService.postReservation(reservationData)
            if (response.isSuccessful) {
                // Başarılı durumda işlemler
            } else {
                // Başarısız durumda işlemler
            }
        } catch (e: Exception) {
            // Hata durumunda işlemler
        }
    }

}