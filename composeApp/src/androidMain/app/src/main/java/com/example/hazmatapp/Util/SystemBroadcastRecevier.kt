package com.example.hazmatapp.Util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter


/*
fun SystemBroadcastReceiver(
    systemAction:String,
    onSystemEvent:(intent: Intent?)->Unit
) {

    val context = LocalContext.current

    val currentOnSystemEvent by rememberUpdatedState(onSystemEvent)

    DisposableEffect(context, systemAction){
        val intentFilter = IntentFilter(systemAction)
        val broadcast = object : BroadcastReceiver(){
            override fun onReceive(p0: Context?, intent: Intent?) {
                currentOnSystemEvent(intent)
            }
        }

        context.registerReceiver(broadcast, intentFilter)

        onDispose {
            context.unregisterReceiver(broadcast)
        }

    }
}*/