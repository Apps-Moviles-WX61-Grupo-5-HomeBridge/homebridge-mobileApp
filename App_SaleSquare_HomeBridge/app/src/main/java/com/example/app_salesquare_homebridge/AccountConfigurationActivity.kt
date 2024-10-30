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

    private val titles = arrayOf("Calculadora crediticia", "Perfil", "Crear nueva publicación", "Cerrar sesión")
    private val icons = intArrayOf(
        R.drawable.baseline_calculate_24,
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

        navbar.findViewById<ImageView>(R.id.icon_inmuebles).setOnClickListener {
            val intent = Intent(this, PostResultsActivity::class.java)
            startActivity(intent)
        }

        navbar.findViewById<ImageView>(R.id.icon_buscar).setOnClickListener {
            val intent = Intent(this, SearchFilterActivity::class.java)
            startActivity(intent)
        }

        navbar.findViewById<ImageView>(R.id.icon_notificaciones).setOnClickListener {
            // Manejar la navegación a la sección de Notificaciones
        }

        navbar.findViewById<ImageView>(R.id.icon_planes).setOnClickListener {
            // Manejar la navegación a la sección de Planes
        }
    }
}