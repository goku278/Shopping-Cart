package com.example.mystore.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mystore.R
import com.example.mystore.db.entity.Cart
import com.example.mystore.ui.home.adapter.CartAdapter
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_cart.custom_toolbar_cart
import kotlinx.android.synthetic.main.activity_cart.tvSubTotalValue
import kotlinx.android.synthetic.main.activity_cart.tvTotalValue
import kotlinx.android.synthetic.main.activity_main.custom_toolbar
import kotlinx.android.synthetic.main.custom_toolbar.view.ivHeartToolBar
import kotlinx.android.synthetic.main.toolbar_cart.ivBack
import kotlinx.android.synthetic.main.toolbar_cart.view.tvTitle

class Cart : AppCompatActivity() {
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var cartList = ArrayList<Cart>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        Paper.init(this)
        setSupportActionBar(findViewById(R.id.custom_toolbar_cart))
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#FF6600")
        val title = custom_toolbar_cart?.tvTitle
        title?.text = "Cart"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
            window.statusBarColor = Color.TRANSPARENT
        }
        getCartList()
        initRecyclerView()
        setClicks()
    }

    private fun getCartList() {
        val carts =
            Paper.book().read("cart", ArrayList<com.example.mystore.ui.home.Cart>())
        if (!carts.isNullOrEmpty()) {
            cartList = carts as ArrayList<Cart>
        }
    }

    private fun setClicks() {
        custom_toolbar?.ivHeartToolBar?.setOnClickListener {
        }
        ivBack?.setOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initRecyclerView() {
        getData()
    }

    private fun getData() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvCart)
        val cartItems = Paper.book().read("cart", ArrayList<Cart>())
        var count:Double = "0".toDouble()
        if (cartItems != null) {
            for (c in cartItems) {
                count += c.price.trim().toDouble() * c.qty.toString().trim().toInt()
            }
        }
        if (!count.toString().trim().isNullOrEmpty() && count.toString().trim().contains(".")) {
            count = count.toString().substring(0,count.toString().indexOf(".")).toDouble()
        }
        tvSubTotalValue?.text = count.toString().trim()
        tvTotalValue?.text = (count.toString().trim().toDouble() - 40).toString()
        val cartAdapter = cartItems?.let { CartAdapter(applicationContext, it) }
        cartAdapter?.tvSubTotal = tvSubTotalValue
        cartAdapter?.tvTotal = tvTotalValue
        cartAdapter?.totalPrice = count.toInt()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter
    }
}