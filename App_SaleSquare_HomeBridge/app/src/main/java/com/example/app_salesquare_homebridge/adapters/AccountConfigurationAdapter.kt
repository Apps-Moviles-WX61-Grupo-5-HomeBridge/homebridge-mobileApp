package com.example.app_salesquare_homebridge.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_salesquare_homebridge.CalculatorActivity
import com.example.app_salesquare_homebridge.LoginActivity
import com.example.app_salesquare_homebridge.NewPropertyActivity
import com.example.app_salesquare_homebridge.R
import com.example.app_salesquare_homebridge.UserProfileActivity

class AccountConfigurationAdapter(
    private val context: Context,
    private val titles: Array<String>,
    private val icons: IntArray
) : RecyclerView.Adapter<AccountConfigurationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = titles[position]
        holder.icon.setImageResource(icons[position])

        holder.itemView.setOnClickListener {
            val intent = when (position) {
                0 -> Intent(context, CalculatorActivity::class.java)
                1 -> Intent(context, UserProfileActivity::class.java)
                2 -> Intent(context, NewPropertyActivity::class.java)
                3 -> Intent(context, LoginActivity::class.java)
                else -> Intent(context, LoginActivity::class.java)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = titles.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.item_title)
        val icon: ImageView = itemView.findViewById(R.id.item_icon)
    }
}