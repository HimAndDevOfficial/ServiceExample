package com.example.servicesample

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //Bound Service class object
    var boundService: BoundService? = null
    var isBound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startMusicbutton = findViewById<Button>(R.id.startMusic)
        val stopMusicbutton = findViewById<Button>(R.id.stopMusic)
        val foregorundServicebutton = findViewById<Button>(R.id.startForgroundService)

        startMusicbutton.setOnClickListener {
            val intent = Intent(this@MainActivity, MyService::class.java)
            startService(intent)
        }

        stopMusicbutton.setOnClickListener {
            val intent = Intent(this@MainActivity, MyService::class.java)
            stopService(intent)
        }

        foregorundServicebutton.setOnClickListener {
            val intent = Intent(this@MainActivity, ForegroundService::class.java)
            startService(intent)
        }


    }

    override fun onStart() {
        super.onStart()

        val intent = Intent(this, BoundService::class.java)
        // startService(intent)
        bindService(intent, boundServiceConnection, BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()

        val runnable = Runnable {
            Toast.makeText(this@MainActivity,boundService!!.randomGenerator().toString(),Toast.LENGTH_SHORT).show()
        }

        val handler = Handler()
        handler.postDelayed(runnable,3000)

    }

    override fun onStop() {
        super.onStop()
        if(isBound) {
            unbindService(boundServiceConnection)
            isBound= false
        }
    }

    //it is called when a connection to the service has been established, with the ibinder of the communication chanel to the service

    private val boundServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binderBridge = p1 as BoundService.MyBinder
            boundService = binderBridge.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

            isBound = false
            boundService = null
        }

    }

}