package com.example.app_salesquare_homebridge

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.credit_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navbar = layoutInflater.inflate(R.layout.navbar, findViewById<LinearLayout>(R.id.navbar_container4), true)

        navbar.findViewById<ImageView>(R.id.icon_inmuebles).setOnClickListener {
            val intent = Intent(this, PostResultsActivity::class.java)
            startActivity(intent)
        }

        navbar.findViewById<ImageView>(R.id.icon_buscar).setOnClickListener {
            val intent = Intent(this, SearchFilterActivity::class.java)
            startActivity(intent)
        }

        navbar.findViewById<ImageView>(R.id.icon_cuenta).setOnClickListener {
            val intent = Intent(this, AccountConfigurationActivity::class.java)
            startActivity(intent)
        }
    }
}