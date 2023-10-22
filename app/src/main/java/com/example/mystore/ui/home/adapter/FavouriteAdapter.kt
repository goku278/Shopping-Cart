package com.example.mystore.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystore.R
import com.example.mystore.db.entity.FavouriteItem

class FavouriteAdapter(context: Context, private val favouriteItems: List<FavouriteItem>) :
    RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.favourite_list_layout, parent, false)
        return FavouriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val currentItem = favouriteItems[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return favouriteItems.size
    }

    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val favouriteImage = itemView.findViewById<ImageView>(R.id.ivFavourite)
        val favouriteName = itemView.findViewById<TextView>(R.id.tvFavouriteName)
        val favouritePrice = itemView.findViewById<TextView>(R.id.tvFavouritePrice)
        val tvFavouriteQty = itemView.findViewById<TextView>(R.id.tvFavouriteQty)
        val like = itemView.findViewById<ImageView>(R.id.ivHeart2)
        val addToCart2 = itemView.findViewById<Button>(R.id.btnAddToCart2)
        fun bind(item: FavouriteItem) {
            Glide.with(favouriteImage).load(item.icon).into(favouriteImage)
            favouriteName.text = item.name
            favouritePrice.text = item.price
            tvFavouriteQty.text = "1 unit"

            addToCart2?.setOnClickListener {
                var qty = tvFavouriteQty?.text.toString().trim()
                if (qty.contains("unit")) {
                    qty = qty.substring(0,qty.indexOf("unit"))
                }
                if (!qty.contains("unit")) {
                    tvFavouriteQty?.text = (qty.toString().trim().toInt() + 1).toString() + " unit"
                }
                var p = favouritePrice?.text
                if (!p.isNullOrEmpty() && p.contains(".")) {
                    p = p.substring(0,p.indexOf("."))
                    favouritePrice?.text = (p.toString().trim().toFloat() + item.price.toFloat()).toString()
                }
            }
        }
    }
}
