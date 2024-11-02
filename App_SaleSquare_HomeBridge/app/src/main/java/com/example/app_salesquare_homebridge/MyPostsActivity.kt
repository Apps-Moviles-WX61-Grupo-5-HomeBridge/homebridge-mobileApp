package com.example.app_salesquare_homebridge

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_salesquare_homebridge.shared.user.UserWrapper
import com.example.app_salesquare_homebridge.ui.MainActivity

class MyPostsActivity : AppCompatActivity() {
    private lateinit var d_UserWrapper: UserWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.my_posts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.d_UserWrapper = intent.getParcelableExtra("userWrapper")!!

        val navbar = layoutInflater.inflate(R.layout.navbar, findViewById<LinearLayout>(R.id.navbar_container3), true)

        navbar.findViewById<ImageView>(R.id.icon_inmuebles).setOnClickListener {
            val intent = Intent(this, PostResultsActivity::class.java)
            intent.putExtra("userWrapper", this.d_UserWrapper)
            startActivity(intent)
        }

        navbar.findViewById<ImageView>(R.id.icon_buscar).setOnClickListener {
            val intent = Intent(this, SearchFilterActivity::class.java)
            startActivity(intent)
        }

        navbar.findViewById<ImageView>(R.id.icon_publicar).setOnClickListener {
            val iconPublicar = it as ImageView
            iconPublicar.setColorFilter(resources.getColor(R.color.light_green, theme))

            val intent = Intent(this, NewPropertyActivity::class.java)
            intent.putExtra("userWrapper", this.d_UserWrapper)
            startActivity(intent)
        }

        navbar.findViewById<ImageView>(R.id.icon_cuenta).setOnClickListener {
            val intent = Intent(this, AccountConfigurationActivity::class.java)
            intent.putExtra("userWrapper", this.d_UserWrapper)
            startActivity(intent)
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