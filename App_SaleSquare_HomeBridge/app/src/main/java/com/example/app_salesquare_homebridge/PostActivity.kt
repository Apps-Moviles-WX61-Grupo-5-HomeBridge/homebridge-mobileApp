package     com.example.app_salesquare_homebridge

import      android.os.Bundle
import      android.view.View
import      android.view.ViewGroup
import android.widget.Button
import      android.widget.ImageView
import      android.widget.PopupMenu
import      android.widget.Toast
import      androidx.activity.enableEdgeToEdge
import      androidx.appcompat.app.AppCompatActivity
import      androidx.cardview.widget.CardView
import      androidx.core.view.ViewCompat
import      androidx.core.view.WindowInsetsCompat
import      org.imaginativeworld.whynotimagecarousel.ImageCarousel
import      org.imaginativeworld.whynotimagecarousel.model.CarouselItem



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

        val backIcon = findViewById<ImageView>(R.id.back_icon)
        val menuIcon:ImageView = findViewById(R.id.menu_icon)
        val shareIcon:ImageView = findViewById(R.id.share_icon)
        //  val carousel:ImageCarousel = findViewById(R.id.carousel)

        val list = mutableListOf<CarouselItem>()
        addCarouselItem(list, "https://img10.naventcdn.com/avisos/resize/111/01/44/64/30/87/1200x1200/1487714694.jpg?rapc=bXZhX2ltYWdl?isFirstImage=true")
        addCarouselItem(list, "https://img10.naventcdn.com/avisos/resize/111/01/44/64/30/87/1200x1200/1487714701.jpg?rapc=bXZhX2ltYWdl")
        addCarouselItem(list, "https://img10.naventcdn.com/avisos/resize/111/01/44/64/30/87/1200x1200/1487714695.jpg?rapc=bXZhX2ltYWdl")
        addCarouselItem(list, "https://img10.naventcdn.com/avisos/resize/111/01/44/64/30/87/1200x1200/1487714687.jpg?rapc=bXZhX2ltYWdl")
        addCarouselItem(list, "https://img10.naventcdn.com/avisos/resize/111/01/44/64/30/87/1200x1200/1487714689.jpg?rapc=bXZhX2ltYWdl")
        addCarouselItem(list, "https://img10.naventcdn.com/avisos/resize/111/01/44/64/30/87/1200x1200/1487714685.jpg?rapc=bXZhX2ltYWdl")

        //  carousel.setData(list)

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
        this.goBack()
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
        val editAddress: ImageView = findViewById(R.id.ivEditAddress)
        val editDetails: ImageView = findViewById(R.id.ivEditDetails)
        val editPhotos: ImageView = findViewById(R.id.ivEditPhotos)

        val visibility = if (isVisible) View.VISIBLE else View.GONE

        editAddress.visibility = visibility
        editDetails.visibility = visibility
        editPhotos.visibility = visibility

        adjustPhotosMargin()
        adjustTitleMargin()
    }

    private fun addCarouselItem(list: MutableList<CarouselItem>, imageUrl: String) {
        list.add(CarouselItem(imageUrl = imageUrl))
    }

    private fun adjustPhotosMargin() {
        //  val ivEditPhotos = findViewById<ImageView>(R.id.ivEditPhotos)
        //  val photos = findViewById<View>(R.id.photos_slider)

        //  val params = photos.layoutParams as ViewGroup.MarginLayoutParams
        //  if (ivEditPhotos.visibility == View.VISIBLE) {
        //      params.topMargin = 100
        //  } else {
        //      params.topMargin = 0
        //  }
        //  photos.layoutParams = params
    }

    private fun adjustTitleMargin() {
        val ivEditDetails = findViewById<ImageView>(R.id.ivEditDetails)
        val tvTitle = findViewById<View>(R.id.tvTitle)

        val params = tvTitle.layoutParams as ViewGroup.MarginLayoutParams
        if (ivEditDetails.visibility == View.VISIBLE) {
            params.topMargin = 150
        } else {
            params.topMargin = 21
        }
        tvTitle.layoutParams = params
    }

    private fun showShareIcon() {
        val shareIcon = findViewById<ImageView>(R.id.share_icon)
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)

        shareIcon.visibility = View.VISIBLE
        menuIcon.visibility = View.GONE
    }

    private fun goBack(): Unit {
        val btnCreatePost = findViewById<ImageView>(R.id.back_icon)
        btnCreatePost.setOnClickListener {
            finish()
        }
    }
}