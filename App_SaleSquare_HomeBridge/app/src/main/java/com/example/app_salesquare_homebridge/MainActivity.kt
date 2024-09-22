package com.example.app_salesquare_homebridge

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backIcon:ImageView = findViewById<ImageView>(R.id.back_icon)
        val menuIcon:ImageView = findViewById<ImageView>(R.id.menu_icon)

        backIcon.setOnClickListener {
            Toast.makeText(this, "You clicked in back icon", Toast.LENGTH_SHORT).show()
        }
        menuIcon.setOnClickListener { view ->
            showMenu(view)
        }
    }

    private fun showMenu(v: View) {
        val popupMenu = PopupMenu(this, v)
        popupMenu.menuInflater.inflate(R.menu.post_toolbar_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.edit -> {
                    Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.delete -> {
                    Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}