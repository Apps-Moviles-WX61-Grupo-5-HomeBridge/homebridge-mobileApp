package com.example.app_salesquare_homebridge

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.app_salesquare_homebridge.models.publications.Publication
import com.example.app_salesquare_homebridge.network.PostApiService
import com.example.app_salesquare_homebridge.posts.PostAdapter
import com.example.app_salesquare_homebridge.shared.user.UserWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyProfileActivity : AppCompatActivity() {
    private lateinit var userWrapper: UserWrapper
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.profile_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userWrapper = intent.getParcelableExtra("userWrapper")!!
        postAdapter = PostAdapter(mutableListOf(),userWrapper,createApiService())

        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvPublicaciones)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = postAdapter

        val userId = intent.getIntExtra("userId", -1)
        if (userId != -1) {
            loadProfile(userId)
            loadUserPosts(userId)
        }
        else
        {
            Log.e("MyProfileActivity", "No user id was passed to the activity ${intent.extras}")
        }
    }

    private fun createApiService(): PostApiService {
        return Retrofit.Builder()
            .baseUrl("https://salesquare-aceeh0btd8frgyc2.brazilsouth-01.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApiService::class.java)
    }

    private fun loadProfile(userId : Int)
    {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://salesquare-aceeh0btd8frgyc2.brazilsouth-01.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PostApiService::class.java)
        val call = service.findUserById("Bearer ${this.userWrapper.token()}", userId)

        call.enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(call : Call<Map<String, Any>>, response : Response<Map<String, Any>>) {
                val user = response.body()

                if (response.isSuccessful) {

                    if (user != null) {

                        val name = user["name"] as String
                        val picture = user["userProfilePhotoUrl"] as String
                        val phoneNumber = user["phoneNumber"] as String
                        val userDescription = user["userDescription"] as String
                        findViewById<TextView>(R.id.tvName).text = name
                        Glide.with(this@MyProfileActivity).load(picture).into(findViewById<ImageView>(R.id.logo_button))
                        findViewById<TextView>(R.id.tvNumber).text = phoneNumber
                        findViewById<TextView>(R.id.tvDescription).text = userDescription
                    }
                } else {
                    Log.e("PostActivity", "Failed to load user information. Response code: ${response.message()}")
                    Toast.makeText(this@MyProfileActivity, "No se pudo cargar la información del usuario", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                Log.e("PostActivity", "Request failed: ${t.message}")
                Toast.makeText(this@MyProfileActivity, "Error al cargar la información del usuario: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadUserPosts(userId : Int)
    {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://salesquare-aceeh0btd8frgyc2.brazilsouth-01.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PostApiService::class.java)
        val call = service.userPublications("Bearer ${this.userWrapper.token()}", userId)

        call.enqueue(object : Callback<List<Publication>> {
            override fun onResponse(
                call: Call<List<Publication>>,
                response: Response<List<Publication>>
            ) {
               if (response.isSuccessful) {
                   val posts = response.body()
                   if (posts != null) {
                       val postList = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvPublicaciones)
                       postAdapter.updatePosts(posts)
                   }
               }
            }

            override fun onFailure(call: Call<List<Publication>>, t: Throwable) {
                Log.e("PostActivity", "Request failed: ${t.message}")
            }
        })
    }
}