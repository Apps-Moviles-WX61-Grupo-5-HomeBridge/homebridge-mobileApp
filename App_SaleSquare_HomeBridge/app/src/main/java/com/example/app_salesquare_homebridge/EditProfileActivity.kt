package com.example.app_salesquare_homebridge

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app_salesquare_homebridge.network.UserApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditProfileActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etDescription: EditText
    private lateinit var etWeb: EditText
    private lateinit var etImageUrl: EditText
    private lateinit var btnSave: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)

        // Referencia a los elementos del layout
        etUsername = findViewById(R.id.etUsername)
        etDescription = findViewById(R.id.etDescription)
        etWeb = findViewById(R.id.etWeb)
        etImageUrl = findViewById(R.id.etUrlImage)
        btnSave = findViewById(R.id.btSave)

        // Configura el listener
        btnSave.setOnClickListener {
            val username = etUsername.text.toString()
            val description = etDescription.text.toString()
            val web = etWeb.text.toString()
            val imageUrl = etImageUrl.text.toString()

            updateUser(username, description, web, imageUrl)
        }
    }

    private fun updateUser(username: String, description: String, web: String, imageUrl: String) {
        // Configura Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://salesquare-aceeh0btd8frgyc2.brazilsouth-01.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(UserApiService::class.java)

        // PUT request
        val userUpdate = mapOf(
            "userId" to 1,  // Cambiar por el ID del usuario que estés editando
            "companyName" to username,
            "email" to description,
            "ruc" to web
        )

        val call = api.updateUser(userUpdate)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditProfileActivity, "Usuario actualizado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@EditProfileActivity, "Error al actualizar usuario", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
