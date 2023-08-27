package com.example.kisileruygulamasi.data.entity

import java.io.Serializable

data class Kisiler(
    var flight_number:Int,
    var destination_place:String,
    var flight_date:String,
    var flight_company:String,
    var flight_cost: String,
    var aircraft_type:String,
    var flight_time:String,
    var air_quality:String
                   )
    : Serializable