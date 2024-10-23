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
import      androidx.appcompat.app.AppCompatActivity
import      androidx.cardview.widget.CardView
import      androidx.core.view.ViewCompat
import      androidx.core.view.WindowInsetsCompat
import com.example.app_salesquare_homebridge.communication.PublicationResponse
import com.example.app_salesquare_homebridge.network.PostApiService
import com.example.app_salesquare_homebridge.shared.user.UserWrapper
import      org.imaginativeworld.whynotimagecarousel.ImageCarousel
import      org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import retrofit2.Retrofit.Builder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory


class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backIcon = findViewById<ImageView>(R.id.inmuebles_icon)
        val menuIcon:ImageView = findViewById(R.id.menu_icon)
        val shareIcon:ImageView = findViewById(R.id.share_icon)

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
                    Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
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
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val price = intent.getDoubleExtra("price",0.0)
        val rooms = intent.getIntExtra("rooms", 0)
        val bathroom = intent.getIntExtra("bathroom", 0)
        val garages = intent.getIntExtra("garages", 0)
        val location = intent.getStringExtra("location")
        val coveredArea = intent.getFloatExtra("coveredArea", 0.0f)
        val totalArea = intent.getFloatExtra("totalArea", 0.0f)
        val type = intent.getStringExtra("type")
        val operation = intent.getStringExtra("operation")
        val delivery = intent.getStringExtra("delivery")
        val antiquity = intent.getIntExtra("antiquity", 0)
        val projectStage = intent.getStringExtra("projectStage")
        val projectStartDate = intent.getStringExtra("projectStartDate")
        val saleState = intent.getStringExtra("saleState")

        findViewById<TextView>(R.id.tvTitle).text = title
        findViewById<TextView>(R.id.tvDescription).text = description
        findViewById<TextView>(R.id.tvPrice).text = price.toString()
        findViewById<TextView>(R.id.tvDormitoriesQuantity).text = rooms.toString()
        findViewById<TextView>(R.id.tvBathroomsQuantity).text = bathroom.toString()
        findViewById<TextView>(R.id.tvParkingLotsQuantity).text = garages.toString()
        findViewById<TextView>(R.id.tvAddress).text = location
        findViewById<TextView>(R.id.tvCoveredArea).text = coveredArea.toString()
        findViewById<TextView>(R.id.tvTotalArea).text = totalArea.toString()
        findViewById<TextView>(R.id.tvType).text = type
        findViewById<TextView>(R.id.tvOperation).text = operation
        findViewById<TextView>(R.id.tvDelivery).text = delivery
        findViewById<TextView>(R.id.tvAntiquity).text = antiquity.toString()
        findViewById<TextView>(R.id.tvProjectStage).text = projectStage
        findViewById<TextView>(R.id.tvProjectStartDate).text = projectStartDate
        findViewById<TextView>(R.id.tvSaleState).text = saleState
        findViewById<TextView>(R.id.tvOperation2).text = operation


        val carousel: ImageCarousel = findViewById(R.id.carousel)
        val list = mutableListOf<CarouselItem>()
        val images = intent.getStringArrayListExtra("imageList") ?: emptyList<String>()

        for (imageUrl in images) {
            addCarouselItem(list, imageUrl)
        }

        carousel.setData(list)
        /*val retrofit = Builder()
            .baseUrl("https://salesquare-aceeh0btd8frgyc2.brazilsouth-01.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PostApiService::class.java)
        val request = service.publications("Bearer ${this.d_UserWrapper.token()}", this.d_UserWrapper.userId())

        request.enqueue(object : Callback<List<PublicationResponse>> {
            override fun onResponse(call: Call<List<PublicationResponse>>, response: Response<List<PublicationResponse>>) {
                if (response.isSuccessful) {
                    val publicationResponse: List<PublicationResponse> = response.body()!!
                    if (publicationResponse.isNotEmpty()) {
                        val publication = publicationResponse[id].toPublication()

                        findViewById<TextView>(R.id.tvTitle).text = publication.title
                        findViewById<TextView>(R.id.tvDescription).text = publication.description
                        findViewById<TextView>(R.id.tvPrice).text = publication.price.toString()
                        findViewById<TextView>(R.id.tvDate).text = publication.createdDate.toString()
                        findViewById<TextView>(R.id.tvAddress).text = publication.location
                        findViewById<TextView>(R.id.tvCoveredArea).text = publication.coveredArea.toString()
                        findViewById<TextView>(R.id.tvTotalArea).text = publication.totalArea.toString()
                        findViewById<TextView>(R.id.tvType).text = publication.type
                        findViewById<TextView>(R.id.tvOperation).text = publication.operation
                        findViewById<TextView>(R.id.tvDelivery).text = publication.delivery
                        findViewById<TextView>(R.id.tvDormitoriesQuantity).text = publication.dormitory.toString()
                        findViewById<TextView>(R.id.tvBathroomsQuantity).text = publication.bathroom.toString()
                        findViewById<TextView>(R.id.tvParkingLotsQuantity).text = publication.parkingLot.toString()
                        findViewById<TextView>(R.id.tvSaleState).text = publication.saleState
                        findViewById<TextView>(R.id.tvProjectStage).text = publication.projectStage
                        findViewById<TextView>(R.id.tvProjectStartDate).text = publication.projectStartDate
                        findViewById<TextView>(R.id.tvAntiquity).text = publication.antiquity.toString()
                        findViewById<TextView>(R.id.tvOperation2).text = publication.operation

                        val carousel: ImageCarousel = findViewById(R.id.carousel)
                        val list = mutableListOf<CarouselItem>()
                        val images = publication.imagesList ?: emptyList()

                        for (imageUrl in images) {
                            addCarouselItem(list, imageUrl)
                        }

                        carousel.setData(list)
                    }
                }
            }

            override fun onFailure(call: Call<List<PublicationResponse>>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })*/
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