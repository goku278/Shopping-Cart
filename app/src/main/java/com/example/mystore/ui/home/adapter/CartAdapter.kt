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
import com.example.mystore.db.entity.Cart
import com.google.android.exoplayer2.util.Log

class CartAdapter(context: Context, private val cartItems: List<Cart>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    var count = 100

    var totalPrice = 0

    var tvSubTotal: TextView = TextView(context)
    var tvTotal: TextView = TextView(context)

    var subTotal: Double = "0".toDouble()
    var subTotal2: Double = "0".toDouble()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_list_layout, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartItems[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Cart) {
            val img = itemView.findViewById<ImageView>(R.id.ivCart2)
            val name = itemView.findViewById<TextView>(R.id.tvCartName2)
            val price = itemView.findViewById<TextView>(R.id.tvCartPrice2)
            val btnMinus = itemView.findViewById<Button>(R.id.btnMinusToCart2)
            val btnPlus = itemView.findViewById<Button>(R.id.btnAddToCart2)
            val qty = itemView.findViewById<TextView>(R.id.tvQty2)

            Log.d("CartAdapter", "count = $count")

            Glide.with(img).load(item.icon).into(img)
            name.text = item.name
            price.text = item.price + ""
            qty.text = item.qty
            var p = 0
            btnPlus?.setOnClickListener {
                if (!item.price.toString().trim().isNullOrEmpty() && item.price.toString().trim()
                        .contains(".")
                ) {
                    p = item.price.toString().trim()
                        .substring(0, item.price.toString().trim().indexOf(".")).toString().trim()
                        .toInt()
                }
                totalPrice += p.toInt()
                qty.text = (qty.text.toString().trim().toInt() + 1).toString()
                tvSubTotal.text = totalPrice.toString()
                tvTotal.text = (totalPrice.toString().trim().toInt() - 40).toString()
            }
            btnMinus?.setOnClickListener {
                if (!item.price.toString().trim().isNullOrEmpty() && item.price.toString().trim()
                        .contains(".")
                ) {
                    p = item.price.toString().trim()
                        .substring(0, item.price.toString().trim().indexOf(".")).toString().trim()
                        .toInt()
                }
                totalPrice -= p.toInt()
                qty.text = (qty.text.toString().trim().toInt() - 1).toString()
                tvSubTotal.text = totalPrice.toString()
                tvTotal.text = (totalPrice.toString().trim().toInt() - 40).toString()
            }
        }
    }
}