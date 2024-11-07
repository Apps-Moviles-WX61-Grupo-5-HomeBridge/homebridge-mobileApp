package     com.example.app_salesquare_homebridge.posts

import android.content.Intent
import      android.view.LayoutInflater
import      android.view.View
import      android.view.ViewGroup
import android.widget.Button
import      android.widget.ImageView
import      android.widget.TextView
import      androidx.recyclerview.widget.RecyclerView
import      androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.app_salesquare_homebridge.PostActivity
import      com.example.app_salesquare_homebridge.R
import      com.example.app_salesquare_homebridge.models.Publication
import com.example.app_salesquare_homebridge.shared.user.UserWrapper


public final class PostAdapter(
    private val m_Posts: MutableList<Publication>,
    private val userWrapper: UserWrapper
) : Adapter<PostPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.post_card_result, parent, false)

        return PostPrototype(view)
    }

    override fun onBindViewHolder(holder: PostPrototype, position: Int): Unit {
        holder.bind(this.m_Posts[position], this.userWrapper)
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
    private val m_PostDetailsButton: Button = itemView.findViewById<Button>(R.id.bt_PostDetails)

    public fun bind(post: Publication, userWrapper: UserWrapper): Unit {
        this.m_PriceText.text = post.price.toString()
        this.m_Location.text = post.location
        this.m_Size.text = post.size.toString()
        this.m_Rooms.text = post.rooms.toString()
        this.m_Bathrooms.text = post.bathrooms.toString()
        this.m_Garages.text = post.garages.toString()
        this.m_Description.text = post.description
        this.m_ImageView.setImageResource(R.drawable.cute_house)

        m_PostDetailsButton.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, PostActivity::class.java)
            intent.putExtra("post_id", post.id)
            intent.putExtra("size", post.size)
            intent.putExtra("rooms", post.rooms)
            intent.putExtra("bathrooms", post.bathrooms)
            intent.putExtra("garages", post.garages)
            intent.putExtra("description", post.description)
            intent.putExtra("price", post.price)
//            intent.putExtra("imageList", ArrayList(post.imagesList))
            intent.putExtra("title", post.title)
            intent.putExtra("location", post.location)
            intent.putExtra("totalArea", post.totalArea)
            intent.putExtra("type", post.type)
            intent.putExtra("operation", post.operation)
            intent.putExtra("delivery", post.delivery)
            intent.putExtra("projectStartDate", post.projectStartDate)
            intent.putExtra("antiquity", post.antiquity)
            intent.putExtra("userWrapper", userWrapper)
            context.startActivity(intent)
        }
    }
}