package com.example.app_salesquare_homebridge

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_salesquare_homebridge.adapters.UrlAdapter
import com.example.app_salesquare_homebridge.models.PropertyImages
import com.example.app_salesquare_homebridge.network.PostApiService
import com.example.app_salesquare_homebridge.shared.user.UserWrapper
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddPhotosActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    private lateinit var urlInput: EditText
    private lateinit var addUrlButton: Button
    private lateinit var saveUrlsButton: Button
    private lateinit var urlRecyclerView: RecyclerView
    private val urls: ArrayList<String> = ArrayList()
    private lateinit var urlAdapter: UrlAdapter
    private lateinit var apiService: PostApiService
    private lateinit var d_UserWrapper: UserWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        urlInput = findViewById(R.id.urlInput)
        addUrlButton = findViewById(R.id.addUrlButton)
        saveUrlsButton = findViewById(R.id.btPublish)
        urlRecyclerView = findViewById(R.id.urlRecyclerView)

        urlAdapter = UrlAdapter(urls)
        urlRecyclerView.layoutManager = LinearLayoutManager(this)
        urlRecyclerView.adapter = urlAdapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://salesquare-aceeh0btd8frgyc2.brazilsouth-01.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(PostApiService::class.java)

        addUrlButton.setOnClickListener {
            val url = urlInput.text.toString()
            if (url.isNotBlank()) {
                urls.add(url)
                urlAdapter.notifyItemInserted(urls.size - 1)
                urlInput.text.clear()
            }
        }

        this.d_UserWrapper = intent.getParcelableExtra("userWrapper")!!

        this.changeToMyPosts()
    }

    private fun changeToMyPosts(): Unit {
        saveUrlsButton.setOnClickListener {
            sendUrlsToApi()
            val intent = Intent(this, MyPostsActivity::class.java)
            intent.putExtra("userWrapper", d_UserWrapper)
            startActivity(intent)
            finish()
        }
    }

    private fun sendUrlsToApi() {
        val publicationId = 1
        val urlList = PropertyImages(publicationId, urls)

        val call = apiService.sendUrls("Bearer ${this.d_UserWrapper.token()}", urlList)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddPhotosActivity, "URLs enviadas correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Error desconocido"
                    println("Error en la respuesta: $errorBody")
                    Toast.makeText(this@AddPhotosActivity, "Error al enviar URLs: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@AddPhotosActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun convertUrlsToJson(urls: ArrayList<String>): String {
        val gson = Gson()
        val urlList = UrlList(urls)
        return gson.toJson(urlList)
    }

    data class UrlList(
        @SerializedName("urls") val urls: List<String>
    )
}