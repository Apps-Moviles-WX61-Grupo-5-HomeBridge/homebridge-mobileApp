package com.example.app_salesquare_homebridge

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_salesquare_homebridge.shared.user.UserWrapper

class AddPhotosActivity : AppCompatActivity() {
    private lateinit var d_UserWrapper: UserWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_image)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.d_UserWrapper = intent.getParcelableExtra("userWrapper")!!

        this.changeToMyPosts()
    }

    private fun changeToMyPosts(): Unit {
        val btnCreatePost = findViewById<Button>(R.id.btPublish)
        btnCreatePost.setOnClickListener {
            val intent = Intent(this, MyPostsActivity::class.java)
            intent.putExtra("userWrapper", d_UserWrapper)
            startActivity(intent)
            finish()
        }
    }
}