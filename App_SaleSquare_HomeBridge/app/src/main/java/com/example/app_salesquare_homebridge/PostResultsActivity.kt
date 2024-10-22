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
import      com.example.app_salesquare_homebridge.network.PostApiService
import      com.example.app_salesquare_homebridge.posts.Post
import      com.example.app_salesquare_homebridge.posts.PostAdapter
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
    private val m_Posts: MutableList<Post>  = mutableListOf()
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

        this.initializeView()
        this.loadPosts()
        this.changeToFilter()
        this.changeToPostDescription()
        this.changeToMenu()
        this.changeToLogin()
    }

    private fun loadPosts(): Unit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://salesquare-aceeh0btd8frgyc2.brazilsouth-01.azurewebsites.net/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PostApiService::class.java)
        val call = service.getPublication(userRequest)

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

        findViewById<ImageView>(R.id.posts_not_found_image).visibility = ImageView.INVISIBLE
        findViewById<TextView>(R.id.post_not_found_text).visibility = TextView.INVISIBLE

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