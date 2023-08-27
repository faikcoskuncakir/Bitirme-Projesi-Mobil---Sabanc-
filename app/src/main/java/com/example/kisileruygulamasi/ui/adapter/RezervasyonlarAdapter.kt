package com.example.kisileruygulamasi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kisileruygulamasi.R
import com.example.kisileruygulamasi.data.entity.Kisiler
import com.example.kisileruygulamasi.databinding.CardTasarim2Binding

class RezervasyonlarAdapter(var mContext:Context, var rezervasyonlarListesi:List<Kisiler>)
    : RecyclerView.Adapter<RezervasyonlarAdapter.CardTasarimTutucu2>() {

    inner class CardTasarimTutucu2(var tasarim: CardTasarim2Binding): RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu2 {
        val binding = CardTasarim2Binding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardTasarimTutucu2(binding)
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu2, position: Int) {
        val rezervasyon = rezervasyonlarListesi.get(position)
        val t = holder.tasarim

        t.textViewCard2Destination.text = rezervasyon.destination_place
        t.textViewCard2FlightTime.text = rezervasyon.flight_date
    }

    override fun getItemCount(): Int {
        return rezervasyonlarListesi.size
    }

}