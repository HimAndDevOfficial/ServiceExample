package com.example.servicesample

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlin.random.Random

class BoundService : Service() {
    
    //Instance of inner clas created that to provide access to public method in the class
    private val localBinder:IBinder = MyBinder()

    override fun onBind(intent: Intent): IBinder {
        return localBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }
    
    fun randomGenerator():Int {
        val randomNumber :Random = Random

        var luckyNumber :Int = randomNumber.nextInt()

        return luckyNumber
    }
    
    inner class MyBinder : Binder() {
        
        //Return the instance of your bounded service so client can call public methods
        fun getService() : BoundService {
            return this@BoundService
        }
    }
}