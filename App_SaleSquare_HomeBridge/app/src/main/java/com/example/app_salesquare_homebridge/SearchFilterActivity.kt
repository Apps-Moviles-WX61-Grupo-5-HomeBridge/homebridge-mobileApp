package     com.example.app_salesquare_homebridge

import      android.os.Bundle
import      android.widget.Button
import android.widget.TextView
import      androidx.activity.enableEdgeToEdge
import      androidx.appcompat.app.AppCompatActivity
import      androidx.core.view.ViewCompat
import      androidx.core.view.WindowInsetsCompat
import com.example.app_salesquare_homebridge.shared.publication.SearchFilterWrapper


public final class SearchFilterActivity : AppCompatActivity()
{
//	|-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-|
//				    Members and Fields
//	|-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-|

    //	-------------------------------------------
    //					Dependencies
    //	-------------------------------------------
    private lateinit var d_SearchFilterWrapper: SearchFilterWrapper



//	|-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-|
//			        Functions and Methods
//	|-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-|

    //	-------------------------------------------
    //			    Loading Functions
    //	-------------------------------------------
    protected override fun onCreate(savedInstanceState: Bundle?): Unit {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.search_filter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.d_SearchFilterWrapper = intent.getParcelableExtra("searchFilterWrapper")!!

        this.backToResults()
    }

    private fun backToResults(): Unit {
        val btnCreatePost: Button = findViewById(R.id.search_button)
        btnCreatePost.setOnClickListener {
            val search: TextView = findViewById(R.id.searchView)
            val buyOperationTypeButton: Button = findViewById(R.id.buyOperationTypeButton)
            val rentOperationTypeButton: Button = findViewById(R.id.rentOperationTypeButton)
            val housePlaceTypeButton: Button = findViewById(R.id.housePlaceTypeButton)
            val apartmentPlaceTypeButton: Button = findViewById(R.id.apartmentPlaceTypeButton)
            val terrainPlaceTypeButton: Button = findViewById(R.id.terrainPlaceTypeButton)
            val priceFrom: TextView = findViewById(R.id.priceFromView)
            val priceTo: TextView = findViewById(R.id.priceToView)


            finish()
        }
    }
}