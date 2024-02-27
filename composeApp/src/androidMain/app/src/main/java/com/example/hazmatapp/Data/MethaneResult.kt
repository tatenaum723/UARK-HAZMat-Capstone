package com.example.hazmatapp.Data

data class MethaneResult(
    val methane:Float,
    val shaHash: ByteArray,
    val connectionState: ConnectionState
    //Need stuff for hashed values to come through
)
