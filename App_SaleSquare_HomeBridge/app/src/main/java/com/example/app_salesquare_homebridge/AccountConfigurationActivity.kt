package com.example.app_salesquare_homebridge

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_salesquare_homebridge.adapters.AccountConfigurationAdapter
import com.example.app_salesquare_homebridge.models.User
import com.example.app_salesquare_homebridge.shared.user.UserWrapper

class AccountConfigurationActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AccountConfigurationAdapter
    private lateinit var userWrapper: UserWrapper

    private val titles = arrayOf("Perfil", "Crear nueva publicación", "Cerrar sesión")
    private val icons = intArrayOf(
        R.drawable.baseline_person_24,
        R.drawable.baseline_add_box_24,
        R.drawable.baseline_logout_24
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_configuration)

        userWrapper = intent.getParcelableExtra("userWrapper") ?: throw IllegalArgumentException("UserWrapper not found")

        recyclerView = findViewById(R.id.rvMyAccount)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AccountConfigurationAdapter(this, titles, icons, userWrapper)
        recyclerView.adapter = adapter

        val navbar = layoutInflater.inflate(R.layout.navbar, findViewById<LinearLayout>(R.id.navbar_container2), true)

        val iconCuenta = navbar.findViewById<ImageView>(R.id.icon_cuenta)
        iconCuenta.setColorFilter(resources.getColor(R.color.light_green, theme))

        navbar.findViewById<ImageView>(R.id.icon_inmuebles).setOnClickListener {
            val intent = Intent(this, PostResultsActivity::class.java)
            intent.putExtra("userWrapper", this.userWrapper)
            startActivity(intent)
        }

        navbar.findViewById<ImageView>(R.id.icon_publicar).setOnClickListener {
            val iconPublicar = it as ImageView
            iconPublicar.setColorFilter(resources.getColor(R.color.light_green, theme))

            val intent = Intent(this, NewPropertyActivity::class.java)
            intent.putExtra("userWrapper", this.userWrapper)
            startActivity(intent)
        }

        navbar.findViewById<ImageView>(R.id.icon_buscar).setOnClickListener {
            val intent = Intent(this, SearchFilterActivity::class.java)
            startActivity(intent)
        }

    }
}