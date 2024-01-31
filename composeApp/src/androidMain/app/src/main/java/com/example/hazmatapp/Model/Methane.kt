package com.example.hazmatapp.Model

// Contains attributes of the readings that will be stored in the firebase realtime database
data class Methane(
    var name: String? = null,
    var description: String? = null,
    var date: String? = null,
    var time: String? = null,
    var methanePercentage: String? = null,
    var methaneVolume: String? = null,
    var id: String? = null
)