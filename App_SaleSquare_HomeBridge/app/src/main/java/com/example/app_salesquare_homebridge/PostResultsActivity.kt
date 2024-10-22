package     com.example.app_salesquare_homebridge

import      android.content.Intent
import      android.os.Bundle
import android.util.Log
import      android.widget.Button
import      android.widget.ImageButton
import      android.widget.ImageView
import      android.widget.TextView
import android.widget.Toast
import      androidx.activity.enableEdgeToEdge
import      androidx.appcompat.app.AppCompatActivity
import      androidx.core.view.ViewCompat
import      androidx.core.view.WindowInsetsCompat
import      androidx.recyclerview.widget.LinearLayoutManager
import      androidx.recyclerview.widget.RecyclerView
import      com.example.app_salesquare_homebridge.communication.PublicationResponse
import com.example.app_salesquare_homebridge.models.Publication
import      com.example.app_salesquare_homebridge.network.PostApiService
import      com.example.app_salesquare_homebridge.posts.Post
import      com.example.app_salesquare_homebridge.posts.PostAdapter
import      com.example.app_salesquare_homebridge.shared.user.UserWrapper
import      com.example.app_salesquare_homebridge.ui.MainActivity
import okhttp3.internal.wait
import org.json.JSONObject
import      retrofit2.Call
import      retrofit2.Callback
import      retrofit2.Response
import      retrofit2.Retrofit
import      retrofit2.converter.gson.GsonConverterFactory



public final class PostResultsActivity : AppCompatActivity()
{
//	|-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-|
//				    Members and Fields
//	|-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-|

    //	-------------------------------------------
    //					Variables
    //	-------------------------------------------
    private lateinit var d_UserWrapper: UserWrapper
    private val m_Posts: MutableList<Publication>  = mutableListOf()
    private val m_PostsAdapter: PostAdapter = PostAdapter(this.m_Posts)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.post_results)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.d_UserWrapper = intent.getParcelableExtra("userWrapper")!!

        this.loadPosts()
        this.initializeView()
        this.changeToFilter()
        this.changeToPostDescription()
        this.changeToMenu()
        this.changeToLogin()
    }

    private fun loadPosts(): Unit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://salesquare-aceeh0btd8frgyc2.brazilsouth-01.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PostApiService::class.java)

        val call = service.publications("Bearer ${this.d_UserWrapper.token()}", this.d_UserWrapper.userId())

        client.newCall(request).enqueue(object: okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("LoginActivity", "Error de conexión ${e.message}")
                runOnUiThread {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error de conexión ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.body?.let {
                    val responseBody = it.string()

                    runOnUiThread{
                        try {
                            if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                                val jsonObject = JSONObject(responseBody)
                                val result = jsonObject.optBoolean("result", false)
                                if (result) {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Inicio de sesión exitoso",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    this@LoginActivity.m_UserWrapper.token(jsonObject.getString("token"))
                                    this@LoginActivity.m_UserWrapper.userId(jsonObject.getInt("userId"))
                                    this@LoginActivity.m_UserWrapper.refreshToken(jsonObject.getString("refreshToken"))

                                    val intent =
                                        Intent(this@LoginActivity, PostResultsActivity::class.java)
                                    intent.putExtra("userWrapper", this@LoginActivity.m_UserWrapper)
                                    startActivity(intent)
                                }
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Email o contraseña incorrectos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            val a: Int = 0
                        }
                    }
                }
            }
        })

        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val publicationResponse: List<PublicationResponse>? = response.body()
                publicationResponse?.let {
                    if (it.isNotEmpty()) {
                        for (publication in it) {
                            m_Posts.add(publication.toPublication())
                        }
                    }
                }
            } else {
                println("Failed with code: ${response.code()}")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }

        if (this.m_Posts.isNotEmpty()) {
            findViewById<ImageView>(R.id.posts_not_found_image).visibility = ImageView.INVISIBLE
            findViewById<TextView>(R.id.post_not_found_text).visibility = TextView.INVISIBLE
        }
    }
    private fun initializeView(): Unit {
        val rvContact: RecyclerView = findViewById<RecyclerView>(R.id.posts_recycler_view)
        rvContact.adapter       = this.m_PostsAdapter
        rvContact.layoutManager = LinearLayoutManager(this)
    }
    private fun changeToFilter(): Unit {
        val btnFilter = findViewById<ImageButton>(R.id.filter_button)
        btnFilter.setOnClickListener {
            val intent = Intent(this, SearchFilterActivity::class.java)
            startActivity(intent)
        }
    }
    private fun changeToPostDescription(): Unit {
        val crdView = findViewById<Button>(R.id.see_post_button)
        crdView.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }
    }
    private fun changeToMenu(): Unit {
        val btnMenu = findViewById<ImageButton>(R.id.logo_button)
        btnMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changeToLogin(): Unit {
        val btnLogin = findViewById<ImageButton>(R.id.imageButton4)
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}