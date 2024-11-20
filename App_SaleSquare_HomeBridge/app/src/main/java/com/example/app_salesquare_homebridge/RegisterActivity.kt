package com.example.app_salesquare_homebridge

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_salesquare_homebridge.communication.UserRequest
import com.example.app_salesquare_homebridge.network.UserApiService
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var btRegister: Button
    private lateinit var tvLogin : TextView
    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var cbTerms: CheckBox
    private var spannableString = SpannableString("Ya tengo cuenta. Ingresar")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btRegister = findViewById(R.id.btCreate)
        tvLogin = findViewById(R.id.tvRedLogin)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        cbTerms = findViewById(R.id.cbTerms)

        btRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (!cbTerms.isChecked) {
                Toast.makeText(
                    this,
                    "Debe aceptar los términos y condiciones",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Por favor, ingrese su correo y contraseña",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                registerUser(email, password)
            }
        }

        val clickableSpan = object: android.text.style.ClickableSpan() {
            override fun onClick(widget: android.view.View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            override fun updateDrawState(ds: android.text.TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = resources.getColor(R.color.blue, theme)
            }
        }

        spannableString.setSpan(clickableSpan, 16, 24, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvLogin.text = spannableString

        tvLogin.movementMethod = android.text.method.LinkMovementMethod.getInstance()
    }

    fun registerUser(email: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://salesquare-aceeh0btd8frgyc2.brazilsouth-01.azurewebsites.net/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UserApiService::class.java)
        val userRequest = UserRequest("", email, password, "")
        val call = service.registerUser(userRequest)

        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    val a = 0
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
            }
        })


    }
}