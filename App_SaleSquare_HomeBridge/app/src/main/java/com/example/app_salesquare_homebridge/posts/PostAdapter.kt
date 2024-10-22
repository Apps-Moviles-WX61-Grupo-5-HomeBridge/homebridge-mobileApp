package     com.example.app_salesquare_homebridge.posts

import      android.view.LayoutInflater
import      android.view.View
import      android.view.ViewGroup
import      android.widget.ImageView
import      android.widget.TextView
import      androidx.recyclerview.widget.RecyclerView
import      androidx.recyclerview.widget.RecyclerView.Adapter
import      com.example.app_salesquare_homebridge.R
import      com.example.app_salesquare_homebridge.models.Publication



public final class PostAdapter(
    private val m_Posts: MutableList<Publication>
) : Adapter<PostPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.post_card_result, parent, false)

        return PostPrototype(view)
    }

    override fun onBindViewHolder(holder: PostPrototype, position: Int): Unit {
        holder.bind(this.m_Posts[position])
    }

    override fun getItemCount(): Int {
        return this.m_Posts.size
    }
}

public class PostPrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val m_PriceText: TextView = itemView.findViewById<TextView>(R.id.price_text)
    private val m_Location: TextView = itemView.findViewById<TextView>(R.id.location_text)
    private val m_Size: TextView = itemView.findViewById<TextView>(R.id.size_text)
    private val m_Rooms: TextView = itemView.findViewById<TextView>(R.id.room_text)
    private val m_Bathrooms: TextView = itemView.findViewById<TextView>(R.id.bathrooms_text)
    private val m_Garages: TextView = itemView.findViewById<TextView>(R.id.garages_text)
    private val m_Description: TextView = itemView.findViewById<TextView>(R.id.description_text)
    private val m_ImageView: ImageView = itemView.findViewById<ImageView>(R.id.house_image_reference)

    public fun bind(post: Publication): Unit {
        this.m_PriceText.text = post.price.toString()
        this.m_Location.text = post.location
        this.m_Size.text = ""
        this.m_Rooms.text = ""
        this.m_Bathrooms.text = post.bathroom.toString()
        this.m_Garages.text = ""
        this.m_Description.text = post.description
        this.m_ImageView.setImageResource(R.drawable.cute_house)
    }
}