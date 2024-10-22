package     com.example.app_salesquare_homebridge

import      android.content.Intent
import      android.os.Bundle
import      android.widget.Button
import      android.widget.ImageButton
import      android.widget.ImageView
import      android.widget.TextView
import      androidx.activity.enableEdgeToEdge
import      androidx.appcompat.app.AppCompatActivity
import      androidx.core.view.ViewCompat
import      androidx.core.view.WindowInsetsCompat
import      androidx.recyclerview.widget.LinearLayoutManager
import      androidx.recyclerview.widget.RecyclerView
import      com.example.app_salesquare_homebridge.communication.PublicationResponse
import      com.example.app_salesquare_homebridge.models.Publication
import      com.example.app_salesquare_homebridge.network.PostApiService
import      com.example.app_salesquare_homebridge.posts.PostAdapter
import      com.example.app_salesquare_homebridge.shared.user.UserWrapper
import      com.example.app_salesquare_homebridge.ui.MainActivity
import      okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import retrofit2.Call
import retrofit2.Response
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
    private val client = OkHttpClient()
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

        call.enqueue(object : retrofit2.Callback<List<PublicationResponse>> {
            override fun onResponse(call: Call<List<PublicationResponse>>, response: Response<List<PublicationResponse>>) {
                if (response.isSuccessful) {
                    val publicationResponse: List<PublicationResponse>? = response.body()
                    publicationResponse?.let {
                        if (it.isNotEmpty()) {
                            for (publication in it) {
                                m_Posts.add(publication.toPublication())
                            }

                            if (this@PostResultsActivity.m_Posts.isNotEmpty()) {
                                findViewById<ImageView>(R.id.posts_not_found_image).visibility = ImageView.INVISIBLE
                                findViewById<TextView>(R.id.post_not_found_text).visibility = TextView.INVISIBLE
                            }

                            this@PostResultsActivity.initializeView()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<PublicationResponse>>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }
    private fun initializeView(): Unit {
        val rvContact: RecyclerView = findViewById<RecyclerView>(R.id.posts_recycler_view)
        rvContact.adapter       = this.m_PostsAdapter
        rvContact.layoutManager = LinearLayoutManager(this)

        val tvPropertiesShownTextView: TextView = findViewById<TextView>(R.id.properties_shown_tv)
        tvPropertiesShownTextView.text = "Estás viendo ${this.m_Posts.size} propiedad(es)."
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