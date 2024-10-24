package com.example.app_salesquare_homebridge

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_salesquare_homebridge.ui.MainActivity

class MyPostsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.my_posts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.changeToEditPost()
        this.changeToMain()
    }

    private fun changeToEditPost(): Unit {
        val btnCreatePost = findViewById<ImageButton>(R.id.edit_button)
        btnCreatePost.setOnClickListener {
            val intent = Intent(this, EditPropertyActivity::class.java)
            startActivity(intent)
        }
    }
    private fun changeToMain(): Unit {
        val btnCreatePost = findViewById<ImageButton>(R.id.backButton)
        btnCreatePost.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}