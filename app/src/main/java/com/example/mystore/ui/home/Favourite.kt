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
import com.example.mystore.db.entity.FavouriteItem
import com.example.mystore.ui.home.adapter.FavouriteAdapter
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_cart.custom_toolbar_cart
import kotlinx.android.synthetic.main.activity_main.custom_toolbar
import kotlinx.android.synthetic.main.custom_toolbar.view.ivHeartToolBar
import kotlinx.android.synthetic.main.toolbar_cart.ivBack
import kotlinx.android.synthetic.main.toolbar_cart.view.tvTitle

class Favourite : AppCompatActivity() {
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var favouriteList = ArrayList<FavouriteItem>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        Paper.init(this)
        setSupportActionBar(findViewById(R.id.custom_toolbar_cart))
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#FF6600")
        val title = custom_toolbar_cart?.tvTitle
        title?.text = "Favourite"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
            window.statusBarColor = Color.TRANSPARENT
        }
        getFavouriteItemList()
        initRecyclerView()
        setClicks()
    }

    private fun getFavouriteItemList() {
        val favourite =
            Paper.book().read("favourite", ArrayList<FavouriteItem>())
        if (!favourite.isNullOrEmpty()) {
            favouriteList = favourite as ArrayList<FavouriteItem>
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
        val recyclerView = findViewById<RecyclerView>(R.id.rvFavourite)
        val favouriteItems = Paper.book().read("favourite", ArrayList<FavouriteItem>())
        val favouriteAdapter = favouriteItems?.let { FavouriteAdapter(applicationContext, it) }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = favouriteAdapter
    }
}