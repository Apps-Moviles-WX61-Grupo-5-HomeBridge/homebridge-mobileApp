package     com.example.app_salesquare_homebridge

import android.content.Intent
import      android.os.Bundle
import      android.view.View
import      android.view.ViewGroup
import android.widget.Button
import      android.widget.ImageView
import      android.widget.PopupMenu
import android.widget.TextView
import      android.widget.Toast
import      androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import      androidx.appcompat.app.AppCompatActivity
import      androidx.cardview.widget.CardView
import      androidx.core.view.ViewCompat
import      androidx.core.view.WindowInsetsCompat
import com.example.app_salesquare_homebridge.communication.PropertyImagesResponse
import com.example.app_salesquare_homebridge.communication.PublicationResponse
import com.example.app_salesquare_homebridge.network.PostApiService
import com.example.app_salesquare_homebridge.posts.Post
import com.example.app_salesquare_homebridge.posts.PostAdapter
import com.example.app_salesquare_homebridge.shared.user.UserWrapper
import      org.imaginativeworld.whynotimagecarousel.ImageCarousel
import      org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import retrofit2.Retrofit.Builder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory


class PostActivity : AppCompatActivity() {
    private lateinit var userWrapper: UserWrapper
    private lateinit var m_Posts: MutableList<Post>
    private lateinit var PostsAdapter: PostAdapter

    private var postId: Int = -1
    private lateinit var images: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        m_Posts = mutableListOf()

        userWrapper = intent.getParcelableExtra("userWrapper")!!

        val backIcon = findViewById<ImageView>(R.id.inmuebles_icon)
        val menuIcon:ImageView = findViewById(R.id.menu_icon)
        val shareIcon:ImageView = findViewById(R.id.share_icon)
        val editIcon:Button = findViewById(R.id.tvShowMap)
        val tvAddress = findViewById<TextView>(R.id.tvAddress)

        editIcon.setOnClickListener {
            val address = tvAddress.text.toString()
            val intent = Intent(this, MapActivity::class.java).apply {
                putExtra("address", address)
            }
            startActivity(intent)
        }
        backIcon.setOnClickListener {
            Toast.makeText(this, "You clicked in back icon", Toast.LENGTH_SHORT).show()
            showShareIcon()
            showEditIcons(false)
        }
        menuIcon.setOnClickListener { view ->
            showMenu(view)
        }
        shareIcon.setOnClickListener {
            Toast.makeText(this, "You clicked in share icon", Toast.LENGTH_SHORT).show()
        }
        loadPublication()
        this.goBack()
        this.changeToLandlordProfile()
    }


    private fun showMenu(v: View) {
        val popupMenu = PopupMenu(this, v)
        popupMenu.menuInflater.inflate(R.menu.post_toolbar_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.edit -> {
                    coverCardView()
                    showEditIcons(true)
                    true
                }
                R.id.delete -> {
                    //Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
                    val dialogView = layoutInflater.inflate(R.layout.delete_post_dialog_box, null)
                    val dialog = AlertDialog.Builder(this).setView(dialogView).create()
                    val confirmButton = dialogView.findViewById<Button>(R.id.btConfirmDelete)
                    val cancelButton = dialogView.findViewById<Button>(R.id.btDenyDelete)

                    confirmButton.setOnClickListener {
                        deletePost(postId)
                        dialog.dismiss()
                        goBack()
                    }

                    cancelButton.setOnClickListener {
                        dialog.dismiss()
                    }

                    dialog.show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun coverCardView() {
        val cardView: CardView = findViewById(R.id.cvOperationPrice)

        cardView.visibility = View.GONE
    }

    private fun showEditIcons(isVisible: Boolean) {
        val editIcon: ImageView = findViewById(R.id.ivEditIcon)

        val visibility = if (isVisible) View.VISIBLE else View.GONE

        editIcon.visibility = visibility

        adjustEditMargin()
    }

    private fun addCarouselItem(list: MutableList<CarouselItem>, imageUrl: String) {
        list.add(CarouselItem(imageUrl = imageUrl))
    }

    private fun adjustEditMargin() {
        val ivEditIcon = findViewById<ImageView>(R.id.ivEditIcon)
        val photos = findViewById<View>(R.id.photos_slider)

        val params = photos.layoutParams as ViewGroup.MarginLayoutParams
        if (ivEditIcon.visibility == View.VISIBLE) {
            params.topMargin = 100
        } else {
            params.topMargin = 0
        }
        photos.layoutParams = params
    }

    private fun showShareIcon() {
        val shareIcon = findViewById<ImageView>(R.id.share_icon)
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)

        shareIcon.visibility = View.VISIBLE
        menuIcon.visibility = View.GONE
    }

    private fun loadPublication() {

        postId = intent.getIntExtra("post_id", -1)
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val price = intent.getDoubleExtra("price",0.0)
        val rooms = intent.getIntExtra("rooms", 0)
        val bathroom = intent.getIntExtra("bathroom", 0)
        val garages = intent.getIntExtra("garages", 0)
        val location = intent.getStringExtra("location")
        val totalArea = intent.getFloatExtra("totalArea", 0.0f)
        val typeInt = intent.getIntExtra("type", 0)
        val operationInt = intent.getIntExtra("operation", 0)
        val antiquity = intent.getIntExtra("antiquity", 0)
        val projectStartDate = intent.getStringExtra("projectStartDate")

        val type = when (typeInt) {
            0 -> "Casa"
            1 -> "Departamento"
            2 -> "Terreno"
            else -> "Desconocido"
        }

        val operation = when (operationInt) {
            0 -> "Venta"
            1 -> "Alquiler"
            else -> "Desconocido"
        }

        findViewById<TextView>(R.id.tvTitle).text = title
        findViewById<TextView>(R.id.tvDescription).text = description
        findViewById<TextView>(R.id.tvPrice).text = price.toString()
        findViewById<TextView>(R.id.tvDormitoriesQuantity).text = rooms.toString()
        findViewById<TextView>(R.id.tvBathroomsQuantity).text = bathroom.toString()
        findViewById<TextView>(R.id.tvParkingLotsQuantity).text = garages.toString()
        findViewById<TextView>(R.id.tvAddress).text = location
        findViewById<TextView>(R.id.tvTotalArea).text = totalArea.toString()
        findViewById<TextView>(R.id.tvType).text = type
        findViewById<TextView>(R.id.tvOperation).text = operation
        findViewById<TextView>(R.id.tvAntiquity).text = antiquity.toString()
        findViewById<TextView>(R.id.tvProjectStartDate).text = projectStartDate
        findViewById<TextView>(R.id.tvOperation2).text = operation


        val carousel: ImageCarousel = findViewById(R.id.carousel)
        val list = mutableListOf<CarouselItem>()

        val retrofit = Builder()
            .baseUrl("http://10.0.2.2:5011")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PostApiService::class.java)
        val call = service.imageList("Bearer ${this.userWrapper.token()}", 1)

        call.enqueue(object : Callback<PropertyImagesResponse> {
            override fun onResponse(call: Call<PropertyImagesResponse>, response: Response<PropertyImagesResponse>) {
                if (response.isSuccessful) {
                    val imagesResponse = response.body()
                    if (imagesResponse != null) {
                        images = imagesResponse.toPropertyImages().imageList!!
                        for (imageUrl in images) {
                            addCarouselItem(list, imageUrl)
                        }
                        carousel.setData(list)
                    }
                } else {
                    Toast.makeText(this@PostActivity, "No se pudo cargar la lista de imágenes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PropertyImagesResponse>, t: Throwable) {
                println("Fallo en la conexión: ${t.message}")
                Toast.makeText(this@PostActivity, "Error al cargar la lista de imágenes: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deletePost(postId: Int) {

        val retrofit = Builder()
            .baseUrl("https://salesquare-aceeh0btd8frgyc2.brazilsouth-01.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PostApiService::class.java)
        val call = service.deletePost("Bearer ${this.userWrapper.token()}", postId)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Aquí puedes realizar cualquier acción adicional después de la eliminación.
                    Toast.makeText(this@PostActivity, "Publicación eliminada", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@PostActivity, "No se pudo eliminar la publicación", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@PostActivity, "Error al eliminar la publicación: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun goBack(): Unit {
        val btnCreatePost = findViewById<ImageView>(R.id.inmuebles_icon)
        btnCreatePost.setOnClickListener {
            finish()
        }
    }
    private fun changeToLandlordProfile(): Unit {
        val btnCreatePost = findViewById<Button>(R.id.btContact)
        btnCreatePost.setOnClickListener {
            val intent = Intent(this, MyProfileActivity::class.java)
            startActivity(intent)
        }
    }
}