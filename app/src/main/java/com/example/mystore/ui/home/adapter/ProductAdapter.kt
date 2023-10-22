package com.example.mystore.ui.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystore.R
import io.paperdb.Paper
import android.view.animation.AnimationUtils
import com.example.mystore.model.DataModel

class ProductAdapter(
    private val context: Context,
    private val itemList: ArrayList<DataModel>,
    private val onItemClick: (DataModel) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: DataModel) {
            // Bind your data to the item views here
            val heart = itemView.findViewById<ImageView>(R.id.ivHeart)
            val product = itemView.findViewById<ImageView>(R.id.ivProduct)
            val name = itemView.findViewById<TextView>(R.id.tvName)
            val price = itemView.findViewById<TextView>(R.id.tvPrice)
            val addToCart = itemView.findViewById<Button>(R.id.btnAddToCart)

            Glide.with(context).load(item.icon).into(product)
            name.text = item.name
            price.text = item.price

            addToCart.setOnClickListener {
                item.addToCart = "1"
                onItemClick(item)
            }

            if (item.isFavourite.equals("true")) {
                val scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_heart)
                heart?.setImageResource(R.drawable.heart_red_1)
                heart.startAnimation(scaleUp)
            }

            heart?.setOnClickListener {
                Log.d("Adapter", "clicked on heart")
                val scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_heart)
                if (item.isFavourite.equals("false")) {
                    item.isFavourite = "true"
                    heart?.setImageResource(R.drawable.heart_red_1)
                    heart.startAnimation(scaleUp)
                } else {
                    item.isFavourite = "false"
                    heart?.setImageResource(R.drawable.heart)
                }
                onItemClick(item)
            }

            val catName = Paper.book().read<String>(
                "category_name",
                ""
            )
            Log.d("Adapter", "catName => $catName")

            var split = catName?.split("\n")

            Log.d("Adapter", "split ${split.toString()}")

            for (i in split!!) {
                Log.d("Adapter", "i is $i")

            }

            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}