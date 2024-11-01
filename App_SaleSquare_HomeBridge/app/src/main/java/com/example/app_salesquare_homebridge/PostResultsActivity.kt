package     com.example.app_salesquare_homebridge

import      android.content.Intent
import      android.os.Bundle
import      android.widget.ImageButton
import      android.widget.ImageView
import      android.widget.LinearLayout
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
import      retrofit2.Call
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
    private val m_Posts: MutableList<Publication>   = mutableListOf()

    //	-------------------------------------------
    //					Dependencies
    //	-------------------------------------------
    private lateinit var m_PostsAdapter: PostAdapter
    private lateinit var d_UserWrapper: UserWrapper



//	|-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-|
//			        Functions and Methods
//	|-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-|

    //	-------------------------------------------
    //			    Loading Functions
    //	-------------------------------------------
    protected override fun onCreate(savedInstanceState: Bundle?): Unit {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.post_results)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.d_UserWrapper = intent.getParcelableExtra("userWrapper")!!
        this.m_PostsAdapter = PostAdapter(this.m_Posts, this.d_UserWrapper)

        val navbar = layoutInflater.inflate(R.layout.navbar, findViewById<LinearLayout>(R.id.navbar_container), true)

        val iconInmuebles = navbar.findViewById<ImageView>(R.id.icon_inmuebles)
        iconInmuebles.setColorFilter(resources.getColor(R.color.light_green, theme))

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
        navbar.findViewById<ImageView>(R.id.icon_cuenta).setOnClickListener {
            val intent = Intent(this, AccountConfigurationActivity::class.java)
            intent.putExtra("userWrapper", this.d_UserWrapper)
            startActivity(intent)
        }

        this.changeToFilter()
        this.changeToMenu()
        this.changeToLogin()
    }

    private fun loadPosts(): Unit {
        //  Usage of [[SearchFilterWrapper]]

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

    protected override fun onResume() {
        super.onResume()

        this.loadPosts()
    }

    //	-------------------------------------------
    //			        Functions
    //	-------------------------------------------
    private fun changeToFilter(): Unit {
        val filterButton: ImageButton = findViewById(R.id.filter_button)
        filterButton.setOnClickListener {
            val intent: Intent = Intent(this, SearchFilterActivity::class.java)
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