package com.example.app_salesquare_homebridge.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_salesquare_homebridge.NewPropertyActivity
import com.example.app_salesquare_homebridge.R
import com.example.app_salesquare_homebridge.models.Location
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var location: Location
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnCreatePost = findViewById<Button>(R.id.btCreatePost)
        btnCreatePost.setOnClickListener {
            val intent = Intent(this, NewPropertyActivity::class.java)
            startActivity(intent)
        }

        loadLocation()

        val btnUpdateLocation = findViewById<Button>(R.id.btEditLocation)
        btnUpdateLocation.setOnClickListener {
            val intent = Intent(this, EditLocationActivity::class.java)
            val gson = Gson()
            intent.putExtra("location", gson.toJson(location))
            startActivity(intent)
        }
    }

    private fun loadLocation() {
        val gson = Gson()
        val json = intent.getStringExtra("location")
        location = gson.fromJson(json, Location::class.java) ?: Location(null, "",   0.0, 0.0)

        val tvAddress = findViewById<TextView>(R.id.tvAddress)

        tvAddress.text = location.address
    }
}