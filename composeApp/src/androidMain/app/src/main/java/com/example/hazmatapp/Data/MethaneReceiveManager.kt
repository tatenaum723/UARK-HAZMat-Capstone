package com.example.hazmatapp.Data

import com.example.hazmatapp.Util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow

interface MethaneReceiveManager {
    val data: MutableSharedFlow<Resource<MethaneResult>>

    fun reconnect()

    fun disconnect()

    fun startReceiving()

    fun closeConnection()
}