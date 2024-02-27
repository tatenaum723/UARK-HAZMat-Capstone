package com.example.hazmatapp.ViewModel

//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hazmatapp.Data.ConnectionState
import com.example.hazmatapp.Data.MethaneReceiveManager
import com.example.hazmatapp.Util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



//TODO convert this to a conventional ViewModel
@HiltViewModel
class MethaneViewModel  @Inject constructor(
    private val MethaneReceiveManager: MethaneReceiveManager
) : ViewModel(){

     var initializingMessage: String? = ""
         private set
     var errorMessage: String? = ""
         private set
     var lowerExplosiveLimit: Float = 0.0f
         private set
     var sensorHash: ByteArray = byteArrayOf(0)
        private set
     var absoluteMethane: Float = 0.0f
         private set
     var connectionState: ConnectionState = ConnectionState.Uninitialized
         private set




    private fun subscribeToChanges() {
        viewModelScope.launch {
            MethaneReceiveManager.data.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        //if HMACS match then update the values in the ViewModel
                        connectionState = result.data.connectionState
                        lowerExplosiveLimit = result.data.methane
                        sensorHash = result.data.shaHash
                        //absoluteMethane = result.data.absolutePercent
                        Log.d("Bluetooth Connection","LEL Methane: $lowerExplosiveLimit")
                    }

                    is Resource.Loading -> {
                        initializingMessage = result.message
                        connectionState = ConnectionState.CurrentlyInitializing
                    }

                    is Resource.Error -> {
                        errorMessage = result.errorMessage
                        connectionState = ConnectionState.Uninitialized
                    }
                }
            }
        }
    }



    fun disconnect(){
        MethaneReceiveManager.disconnect()
    }

    fun reconnect(){
        MethaneReceiveManager.reconnect()
    }

    fun initializeConnection(){
        errorMessage = null
        subscribeToChanges()
        MethaneReceiveManager.startReceiving()
    }

    override fun onCleared() {
        super.onCleared()
        MethaneReceiveManager.closeConnection()
    }

    fun oneManualRead(){

    }


}
