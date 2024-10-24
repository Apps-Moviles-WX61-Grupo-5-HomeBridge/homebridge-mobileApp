package com.example.app_salesquare_homebridge

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewPropertyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.new_property)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.new_property)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.changeToPhotos()
    }

    private fun changeToPhotos(): Unit {
        val btnCreatePost = findViewById<Button>(R.id.btnContinue)
        btnCreatePost.setOnClickListener {
            val intent = Intent(this, AddPhotosActivity::class.java)
            startActivity(intent)
        }
    }
}