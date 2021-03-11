package com.sidd.doordash.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sidd.doordash.ConnectionLiveData
import com.sidd.doordash.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var connectionLiveData: ConnectionLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, {
            if(!it) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.no_internet_connection),
                    Snackbar.LENGTH_LONG
                ).setActionTextColor(Color.RED).show()
            }
        })
    }

}