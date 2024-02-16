package com.example.hazmatapp.ViewModel

//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.hazmatapp.Data.ConnectionState
import com.example.hazmatapp.Data.MethaneReceiveManager
import com.example.hazmatapp.Util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.Connection
import androidx.lifecycle.viewModelScope
import javax.inject.Inject


//TODO convert this to a conventional ViewModel
@HiltViewModel
class MethaneViewModel  @Inject constructor(
    private val MethaneReceiveManager: MethaneReceiveManager
) : ViewModel(){

    private var initializingMessage: String? = ""
    private var errorMessage: String? = ""
    private var lowerExplosiveLimit: Float = 0.0f
    private var absoluteMethane: Float = 0.0f
    private var connectionState: ConnectionState = ConnectionState.Uninitialized



    private fun subscribeToChanges() {
        viewModelScope.launch {
            MethaneReceiveManager.data.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        connectionState = result.data.connectionState
                        lowerExplosiveLimit = result.data.lel
                        absoluteMethane = result.data.absolutePercent
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


}
