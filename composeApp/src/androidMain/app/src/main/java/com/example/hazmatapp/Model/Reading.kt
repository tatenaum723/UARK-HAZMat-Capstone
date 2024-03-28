package com.example.hazmatapp.Model

// Contains attributes of the readings that will be stored in the firebase realtime database
data class Reading(
    var name: String? = null,
    var location: String? = null,
    var notes: String? = null,
    var date: String? = null,
    var time: String? = null,
    var methaneLelPercentage: String? = null,
    var methaneVolumePercentage: String? = null,
    var id: String? = null
)