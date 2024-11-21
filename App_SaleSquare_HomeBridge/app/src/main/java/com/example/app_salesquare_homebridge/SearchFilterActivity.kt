package     com.example.app_salesquare_homebridge

import      android.os.Bundle
import      android.widget.Button
import      android.widget.SearchView
import      android.widget.TextView
import      androidx.activity.enableEdgeToEdge
import      androidx.appcompat.app.AppCompatActivity
import      androidx.core.view.ViewCompat
import      androidx.core.view.WindowInsetsCompat
import      com.example.app_salesquare_homebridge.shared.publication.SearchFilterWrapper
import      com.example.app_salesquare_homebridge.shared.publication.types.PublicationOperationType
import      com.example.app_salesquare_homebridge.shared.publication.types.PublicationPlaceType



public final class SearchFilterActivity : AppCompatActivity()
{
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

        this.assignDefaultValue()
        this.backToResults()
        this.clearInputs()
    }

    private fun assignDefaultValue(): Unit {
        val search: SearchView = findViewById(R.id.searchView)
        val buyOperationTypeButton: Button = findViewById(R.id.buyOperationTypeButton)
        val rentOperationTypeButton: Button = findViewById(R.id.rentOperationTypeButton)
        val housePlaceTypeButton: Button = findViewById(R.id.housePlaceTypeButton)
        val apartmentPlaceTypeButton: Button = findViewById(R.id.apartmentPlaceTypeButton)
        val terrainPlaceTypeButton: Button = findViewById(R.id.terrainPlaceTypeButton)
        val priceFrom: TextView = findViewById(R.id.priceFromView)
        val priceTo: TextView = findViewById(R.id.priceToView)
        val noRoomButton: Button = findViewById(R.id.noRoomButton)
        val oneRoomButton: Button = findViewById(R.id.oneRoomButton)
        val twoRoomsButton: Button = findViewById(R.id.twoRoomsButton)
        val threeRoomsButton: Button = findViewById(R.id.threeRoomsButton)
        val fourRoomsButton: Button = findViewById(R.id.fourRoomsButton)
        val fiveRoomsButton: Button = findViewById(R.id.manyRoomsButton)
        val noBathroomButton: Button = findViewById(R.id.noBathroomButton)
        val oneBathroomButton: Button = findViewById(R.id.oneBathroomButton)
        val twoBathroomsButton: Button = findViewById(R.id.twoBathroomsButton)
        val threeBathroomsButton: Button = findViewById(R.id.threeBathroomsButton)
        val fourBathroomsButton: Button = findViewById(R.id.fourBathroomsButton)
        val fiveBathroomsButton: Button = findViewById(R.id.manyBathroomsButton)
        val noGarageButton: Button = findViewById(R.id.noGarageButton)
        val oneGarageButton: Button = findViewById(R.id.oneGarageButton)
        val twoGaragesButton: Button = findViewById(R.id.twoGaragesButton)
        val threeGaragesButton: Button = findViewById(R.id.threeGaragesButton)
        val fourGaragesButton: Button = findViewById(R.id.fourGaragesButton)
        val fiveGaragesButton: Button = findViewById(R.id.manyGaragesButton)
        val areaFrom: TextView = findViewById(R.id.areaFromView)
        val areaTo: TextView = findViewById(R.id.areaToView)

        search.setQuery(SearchFilterWrapper.location, false)
        buyOperationTypeButton.isSelected = SearchFilterWrapper.operationType == PublicationOperationType.Buy
        rentOperationTypeButton.isSelected = SearchFilterWrapper.operationType == PublicationOperationType.Rent
        housePlaceTypeButton.isSelected = SearchFilterWrapper.placeType == PublicationPlaceType.House
        apartmentPlaceTypeButton.isSelected = SearchFilterWrapper.placeType == PublicationPlaceType.Apartment
        terrainPlaceTypeButton.isSelected = SearchFilterWrapper.placeType == PublicationPlaceType.Terrain
        priceFrom.text = if (SearchFilterWrapper.priceFrom <= 0) "" else SearchFilterWrapper.priceFrom.toString()
        priceTo.text = if (SearchFilterWrapper.priceTo <= 0) "" else SearchFilterWrapper.priceTo.toString()
        noRoomButton.isSelected = SearchFilterWrapper.rooms == 0
        oneRoomButton.isSelected = SearchFilterWrapper.rooms == 1
        twoRoomsButton.isSelected = SearchFilterWrapper.rooms == 2
        threeRoomsButton.isSelected = SearchFilterWrapper.rooms == 3
        fourRoomsButton.isSelected = SearchFilterWrapper.rooms == 4
        fiveRoomsButton.isSelected = SearchFilterWrapper.rooms == 5
        noBathroomButton.isSelected = SearchFilterWrapper.bathrooms == 0
        oneBathroomButton.isSelected = SearchFilterWrapper.bathrooms == 1
        twoBathroomsButton.isSelected = SearchFilterWrapper.bathrooms == 2
        threeBathroomsButton.isSelected = SearchFilterWrapper.bathrooms == 3
        fourBathroomsButton.isSelected = SearchFilterWrapper.bathrooms == 4
        fiveBathroomsButton.isSelected = SearchFilterWrapper.bathrooms == 5
        noGarageButton.isSelected = SearchFilterWrapper.garages == 0
        oneGarageButton.isSelected = SearchFilterWrapper.garages == 1
        twoGaragesButton.isSelected = SearchFilterWrapper.garages == 2
        threeGaragesButton.isSelected = SearchFilterWrapper.garages == 3
        fourGaragesButton.isSelected = SearchFilterWrapper.garages == 4
        fiveGaragesButton.isSelected = SearchFilterWrapper.garages == 5
        areaFrom.text = if (SearchFilterWrapper.areaFrom <= 0) "" else SearchFilterWrapper.areaFrom.toString()
        areaTo.text = if (SearchFilterWrapper.areaTo <= 0) "" else SearchFilterWrapper.areaTo.toString()
    }
    private fun backToResults(): Unit {
        val btnCreatePost: Button = findViewById(R.id.search_button)
        btnCreatePost.setOnClickListener {
            val search: SearchView = findViewById(R.id.searchView)
            val buyOperationTypeButton: Button = findViewById(R.id.buyOperationTypeButton)
            val rentOperationTypeButton: Button = findViewById(R.id.rentOperationTypeButton)
            val housePlaceTypeButton: Button = findViewById(R.id.housePlaceTypeButton)
            val apartmentPlaceTypeButton: Button = findViewById(R.id.apartmentPlaceTypeButton)
            val terrainPlaceTypeButton: Button = findViewById(R.id.terrainPlaceTypeButton)
            val priceFrom: TextView = findViewById(R.id.priceFromView)
            val priceTo: TextView = findViewById(R.id.priceToView)
            val noRoomButton: Button = findViewById(R.id.noRoomButton)
            val oneRoomButton: Button = findViewById(R.id.oneRoomButton)
            val twoRoomsButton: Button = findViewById(R.id.twoRoomsButton)
            val threeRoomsButton: Button = findViewById(R.id.threeRoomsButton)
            val fourRoomsButton: Button = findViewById(R.id.fourRoomsButton)
            val fiveRoomsButton: Button = findViewById(R.id.manyRoomsButton)
            val noBathroomButton: Button = findViewById(R.id.noBathroomButton)
            val oneBathroomButton: Button = findViewById(R.id.oneBathroomButton)
            val twoBathroomsButton: Button = findViewById(R.id.twoBathroomsButton)
            val threeBathroomsButton: Button = findViewById(R.id.threeBathroomsButton)
            val fourBathroomsButton: Button = findViewById(R.id.fourBathroomsButton)
            val fiveBathroomsButton: Button = findViewById(R.id.manyBathroomsButton)
            val noGarageButton: Button = findViewById(R.id.noGarageButton)
            val oneGarageButton: Button = findViewById(R.id.oneGarageButton)
            val twoGaragesButton: Button = findViewById(R.id.twoGaragesButton)
            val threeGaragesButton: Button = findViewById(R.id.threeGaragesButton)
            val fourGaragesButton: Button = findViewById(R.id.fourGaragesButton)
            val fiveGaragesButton: Button = findViewById(R.id.manyGaragesButton)
            val areaFrom: TextView = findViewById(R.id.areaFromView)
            val areaTo: TextView = findViewById(R.id.areaToView)

            SearchFilterWrapper.location = search.query.toString()
            SearchFilterWrapper.operationType = if (buyOperationTypeButton.isSelected) PublicationOperationType.Buy else if (rentOperationTypeButton.isSelected) PublicationOperationType.Rent else PublicationOperationType.NotDefined
            SearchFilterWrapper.placeType = if (housePlaceTypeButton.isSelected) PublicationPlaceType.House else if (apartmentPlaceTypeButton.isSelected) PublicationPlaceType.Apartment else if (terrainPlaceTypeButton.isSelected) PublicationPlaceType.Terrain else PublicationPlaceType.NotDefined
            SearchFilterWrapper.priceFrom = if (priceFrom.text.toString().isEmpty()) -1f else priceFrom.text.toString().toFloat()
            SearchFilterWrapper.priceTo = if (priceTo.text.toString().isEmpty()) -1f else priceTo.text.toString().toFloat()
            SearchFilterWrapper.rooms = when {
                noRoomButton.isSelected -> 0
                oneRoomButton.isSelected -> 1
                twoRoomsButton.isSelected -> 2
                threeRoomsButton.isSelected -> 3
                fourRoomsButton.isSelected -> 4
                fiveRoomsButton.isSelected -> 5
                else -> -1
            }
            SearchFilterWrapper.bathrooms = when {
                noBathroomButton.isSelected -> 0
                oneBathroomButton.isSelected -> 1
                twoBathroomsButton.isSelected -> 2
                threeBathroomsButton.isSelected -> 3
                fourBathroomsButton.isSelected -> 4
                fiveBathroomsButton.isSelected -> 5
                else -> -1
            }
            SearchFilterWrapper.garages = when {
                noGarageButton.isSelected -> 0
                oneGarageButton.isSelected -> 1
                twoGaragesButton.isSelected -> 2
                threeGaragesButton.isSelected -> 3
                fourGaragesButton.isSelected -> 4
                fiveGaragesButton.isSelected -> 5
                else -> -1
            }
            SearchFilterWrapper.areaFrom = if (areaFrom.text.toString().isEmpty()) -1f else areaFrom.text.toString().toFloat()
            SearchFilterWrapper.areaTo = if (areaTo.text.toString().isEmpty()) -1f else areaTo.text.toString().toFloat()

            finish()
        }
    }
    private fun clearInputs(): Unit {
        val clear: Button = findViewById(R.id.clear_filter_inputs)
        clear.setOnClickListener {
            val search: SearchView = findViewById(R.id.searchView)
            val buyOperationTypeButton: Button = findViewById(R.id.buyOperationTypeButton)
            val rentOperationTypeButton: Button = findViewById(R.id.rentOperationTypeButton)
            val housePlaceTypeButton: Button = findViewById(R.id.housePlaceTypeButton)
            val apartmentPlaceTypeButton: Button = findViewById(R.id.apartmentPlaceTypeButton)
            val terrainPlaceTypeButton: Button = findViewById(R.id.terrainPlaceTypeButton)
            val priceFrom: TextView = findViewById(R.id.priceFromView)
            val priceTo: TextView = findViewById(R.id.priceToView)
            val noRoomButton: Button = findViewById(R.id.noRoomButton)
            val oneRoomButton: Button = findViewById(R.id.oneRoomButton)
            val twoRoomsButton: Button = findViewById(R.id.twoRoomsButton)
            val threeRoomsButton: Button = findViewById(R.id.threeRoomsButton)
            val fourRoomsButton: Button = findViewById(R.id.fourRoomsButton)
            val fiveRoomsButton: Button = findViewById(R.id.manyRoomsButton)
            val noBathroomButton: Button = findViewById(R.id.noBathroomButton)
            val oneBathroomButton: Button = findViewById(R.id.oneBathroomButton)
            val twoBathroomsButton: Button = findViewById(R.id.twoBathroomsButton)
            val threeBathroomsButton: Button = findViewById(R.id.threeBathroomsButton)
            val fourBathroomsButton: Button = findViewById(R.id.fourBathroomsButton)
            val fiveBathroomsButton: Button = findViewById(R.id.manyBathroomsButton)
            val noGarageButton: Button = findViewById(R.id.noGarageButton)
            val oneGarageButton: Button = findViewById(R.id.oneGarageButton)
            val twoGaragesButton: Button = findViewById(R.id.twoGaragesButton)
            val threeGaragesButton: Button = findViewById(R.id.threeGaragesButton)
            val fourGaragesButton: Button = findViewById(R.id.fourGaragesButton)
            val fiveGaragesButton: Button = findViewById(R.id.manyGaragesButton)
            val areaFrom: TextView = findViewById(R.id.areaFromView)
            val areaTo: TextView = findViewById(R.id.areaToView)

            search.setQuery("", false)
            buyOperationTypeButton.isSelected = false
            rentOperationTypeButton.isSelected = false
            housePlaceTypeButton.isSelected = false
            apartmentPlaceTypeButton.isSelected = false
            terrainPlaceTypeButton.isSelected = false
            priceFrom.text = ""
            priceTo.text = ""
            noRoomButton.isSelected = false
            oneRoomButton.isSelected = false
            twoRoomsButton.isSelected = false
            threeRoomsButton.isSelected = false
            fourRoomsButton.isSelected = false
            fiveRoomsButton.isSelected = false
            noBathroomButton.isSelected = false
            oneBathroomButton.isSelected = false
            twoBathroomsButton.isSelected = false
            threeBathroomsButton.isSelected = false
            fourBathroomsButton.isSelected = false
            fiveBathroomsButton.isSelected = false
            noGarageButton.isSelected = false
            oneGarageButton.isSelected = false
            twoGaragesButton.isSelected = false
            threeGaragesButton.isSelected = false
            fourGaragesButton.isSelected = false
            fiveGaragesButton.isSelected = false
            areaFrom.text = ""
            areaTo.text = ""
        }
    }
}