package com.example.app_salesquare_homebridge.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_salesquare_homebridge.R

class UrlAdapter(private val urls: List<String>) : RecyclerView.Adapter<UrlAdapter.UrlViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrlViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return UrlViewHolder(view)
    }

    override fun onBindViewHolder(holder: UrlViewHolder, position: Int) {
        val url = urls[position]
        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.urlImageView)
    }

    override fun getItemCount(): Int = urls.size

    inner class UrlViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val urlImageView: ImageView = view.findViewById(R.id.urlImageView)
    }
}