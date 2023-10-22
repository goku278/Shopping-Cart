package com.example.mystore.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.BoringLayout
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mystore.R
import com.example.mystore.db.entity.Cart
import com.example.mystore.db.entity.FavouriteItem
import com.example.mystore.db.entity.Item
import com.example.mystore.model.DataModel
import com.example.mystore.ui.home.adapter.ProductAdapter
import com.example.mystore.utils.AppUtils
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.view.ivCart
import kotlinx.android.synthetic.main.custom_toolbar.view.ivHeartToolBar
import kotlinx.android.synthetic.main.product_list_layout.*
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), ShoppingCart.Listener {
    // Initalized the view model
    private lateinit var mainActivityViewModel: MainActivityViewModel

    // This is for animation
    private var isDialogOpen = false

    private lateinit var cartNo: TextView

    private lateinit var ivHeart: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Paper.init(this)
        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        setSupportActionBar(findViewById(R.id.custom_toolbar))

        // Setting up the listener

        ShoppingCart.setListenerToFetchAndStoreData(this)
        mainActivityViewModel.initListener(this)

        // Set the status bar color to be transparent (or a matching gradient color)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#FF6600")

        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        cartNo = toolbar.findViewById<TextView>(R.id.redDotImageView)

        // Creating a custom floating action button

        setUpFloatingActionButton()

        // A co-routine call to get all the cat items list length from the room db

        lifecycleScope.launch {
            countCartItems()
        }

        // Inserting data in the room db

        lifecycleScope.launch {
            mainActivityViewModel.checkIfTableRecordExists()
        }

        // Initializing recycler view

        initRecyclerView()

        // set on click listeners call back methods....

        setClicks()
    }

    override fun onStart() {
        Paper.book().write("on_start","true")
        lifecycleScope.launch {
            mainActivityViewModel.checkIfTableRecordExists()
        }
        super.onStart()
    }

    private fun countCartItems() {
        lifecycleScope.launch {
            // getting all the size of the available carts from the room db
            mainActivityViewModel?.getCart(true)
        }
    }


    // This is a listener which is called from the AppUtils class,
    // once the cart items are all fetched from the room db
    override fun onCartDataFetched(data: ArrayList<Cart>) {
        Log.d("MainActivity", "data is =====> $data")
        Paper.book().write("cart", data)
        val intent =
            Intent(applicationContext, com.example.mystore.ui.home.Cart::class.java)
        startActivity(intent)
    }

    // This is a listener which is called from the AppUtils class,
    // once the products items are all fetched from the room db
    override fun onDataFetched(data: ArrayList<ArrayList<Item>>) {
        Log.d("MainActivity", "OnDataFetched =====> $data")
        processData(data)
    }

    // This is a listener which is called from the AppUtils class,
    // once the cart items are all fetched from the room db
    override fun onCountCartItems(total: Int?) {
        cartNo?.text = total.toString()
    }

    // This is a listener which is called from the AppUtils class,
    // once the favourite marked items are all fetched from the room db
    override fun onFavouriteDataFetched(data: ArrayList<FavouriteItem>) {
        Log.d("MainActivity", "data is =====> $data")
        Paper.book().write("favourite", data)
        val ifOnStart = Paper.book().read("on_start","")
        if (!ifOnStart.isNullOrEmpty() && ifOnStart.equals("false")) {
            val intent = Intent(applicationContext, Favourite::class.java)
            startActivity(intent)
        }
        else {

        }
    }

    override fun onTableRecordsFound(isFound: Boolean?) {
        if (isFound == true) {
            lifecycleScope.launch {
                getData()
            }
        }
        else {
            lifecycleScope.launch {
                insertData(mainActivityViewModel)
            }
        }
    }

    // Processing all the data, then display accordingly in the recycler view

    private fun processData(data: ArrayList<ArrayList<Item>>) {
        Paper.book().write("cat-1", data[0])
        Log.d("ViewModel", "onDataFetched in MainActivity")
        var catName = ""
        if (data != null) {
            if (data != null && !data[0].isNullOrEmpty()) {
                val dataModel = AppUtils.mapEntityToModel(data[0], null)
                val adapter = ProductAdapter(this, dataModel) { item ->
                    // Handle item click here
                    if (item.isFavourite.equals("true")) {
//                                ivHeart?.setImageResource(R.drawable.heart_red)
                        insertFavouriteData(mainActivityViewModel, item)
                        Log.d(
                            "MainActivity",
                            "item id is when isFavourite = true ${item.id}"
                        )
                    } else {
                        if (!item.addToCart.equals("-1")) {
                            deleteFavouriteData(mainActivityViewModel, item)
                            Log.d(
                                "MainActivity",
                                "item id is when isFavourite = false ${item.id}"
                            )
                        }
                    }
                    Log.d("MainActivity", "Clicked on the ${item.name}")
                    if (item.addToCart.equals("1")) {
                        addToCart(mainActivityViewModel, item)
                    }
                }
                catName = Paper.book().read<String>(
                    "category_name",
                    ""
                ).toString()
                tvCat1.text = catName.split("\n")[0]
                recyclerView.layoutManager =
                    LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.adapter = adapter

            }

            if (data.size > 1 && data[1] != null) {
                val dataModel2 = AppUtils.mapEntityToModel(data[1], null)
                val adapter2 = ProductAdapter(this, dataModel2) { item ->
                    // Handle item click here
                    if (item.isFavourite.equals("true")) {
//                                ivHeart?.setImageResource(R.drawable.heart_red)
                        insertFavouriteData(mainActivityViewModel, item)
                        Log.d(
                            "MainActivity",
                            "item id is when isFavourite = true ${item.id}"
                        )
                    } else {
                        if (!item.addToCart.equals("1")) {
                            deleteFavouriteData(mainActivityViewModel, item)
                            Log.d(
                                "MainActivity",
                                "item id is when isFavourite = false ${item.id}"
                            )
                        }
                    }
                    Log.d("MainActivity", "Clicked on the ${item.name}")
                    if (item.addToCart.equals("1")) {
                        addToCart(mainActivityViewModel, item)
                    }
                }
                tvCat2.text = catName.split("\n")[1]
                recyclerView2.layoutManager =
                    LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false)
                recyclerView2.adapter = adapter2
            }

            if (data.size >= 3) {
                val dataModel3 = AppUtils.mapEntityToModel(data[2], null)
                val adapter3 = ProductAdapter(this, dataModel3) { item ->
                    // Handle item click here
                    if (item.isFavourite.equals("true")) {
//                                ivHeart?.setImageResource(R.drawable.heart_red)
                        insertFavouriteData(mainActivityViewModel, item)
                        Log.d(
                            "MainActivity",
                            "item id is when isFavourite = true ${item.id}"
                        )
                    } else {
                        if (!item.addToCart.equals("1")) {
                            deleteFavouriteData(mainActivityViewModel, item)
                            Log.d(
                                "MainActivity",
                                "item id is when isFavourite = false ${item.id}"
                            )
                        }
                    }
                    Log.d("MainActivity", "Clicked on the ${item.name}")
                    if (item.addToCart.equals("1")) {
                        addToCart(mainActivityViewModel, item)
                    }
                }

                tvCat3.text = catName!!.split("\n")[2]
                recyclerView3.layoutManager =
                    LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false)
                recyclerView3.adapter = adapter3

            }

            if (data.size >= 4) {

                val dataModel4 = AppUtils.mapEntityToModel(data[3], null)
                val adapter4 = ProductAdapter(this, dataModel4) { item ->
                    // Handle item click here
                    if (item.isFavourite.equals("true")) {
//                                ivHeart?.setImageResource(R.drawable.heart_red)
                        insertFavouriteData(mainActivityViewModel, item)
                        Log.d(
                            "MainActivity",
                            "item id is when isFavourite = true ${item.id}"
                        )
                    } else {
                        if (!item.addToCart.equals("1")) {
                            deleteFavouriteData(mainActivityViewModel, item)
                            Log.d(
                                "MainActivity",
                                "item id is when isFavourite = false ${item.id}"
                            )
                        }
                    }
                    Log.d("MainActivity", "Clicked on the ${item.name}")
                    if (item.addToCart.equals("1")) {
                        addToCart(mainActivityViewModel, item)
                    }
                }

                tvCat4.text = catName!!.split("\n")[3]
                recyclerView4.layoutManager =
                    LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false)
                recyclerView4.adapter = adapter4

            }

            if (data.size >= 5) {
                val dataModel5 = AppUtils.mapEntityToModel(data[4], null)
                val adapter5 = ProductAdapter(this, dataModel5) { item ->
                    // Handle item click here
                    if (item.isFavourite.equals("true")) {
//                                ivHeart?.setImageResource(R.drawable.heart_red)
                        insertFavouriteData(mainActivityViewModel, item)
                        Log.d(
                            "MainActivity",
                            "item id is when isFavourite = true ${item.id}"
                        )
                    } else {
                        if (!item.addToCart.equals("1")) {
                            deleteFavouriteData(mainActivityViewModel, item)
                            Log.d(
                                "MainActivity",
                                "item id is when isFavourite = false ${item.id}"
                            )
                        }
                    }
                    Log.d("MainActivity", "Clicked on the ${item.name}")
                    if (item.addToCart.equals("1")) {
                        addToCart(mainActivityViewModel, item)
                    }
                }

                tvCat5.text = catName!!.split("\n")[4]
                recyclerView5.layoutManager =
                    LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false)
                recyclerView5.adapter = adapter5
            }
        }
    }

    // Floating action button, animated dialog appears on the top of the custom floating action button

    private fun setUpFloatingActionButton() {
        val fab = findViewById<LinearLayout>(R.id.fab)
        val dialogLayout = findViewById<CardView>(R.id.dialogLayout)

        fab.setOnClickListener {
            if (!isDialogOpen) {
                // Show the dialog with animation
                fab?.setBackgroundResource(R.drawable.cross_rounded)
                ivMenuAction?.visibility = View.GONE
                tvCategories?.visibility = View.GONE
                val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
                dialogLayout.startAnimation(slideUpAnimation)
                dialogLayout.visibility = View.VISIBLE
            } else {
                // Close the dialog with animation
                fab?.setBackgroundResource(R.drawable.rectangular_fab_background)
                ivMenuAction?.visibility = View.GONE
                ivMenuAction?.visibility = View.VISIBLE
                tvCategories?.visibility = View.VISIBLE
                val slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
                dialogLayout.startAnimation(slideDownAnimation)
                dialogLayout.visibility = View.GONE
            }
            isDialogOpen = !isDialogOpen
        }
    }

    private fun setClicks() {
        custom_toolbar?.ivHeartToolBar?.setOnClickListener {
            Paper.book().write("on_start","false")
            lifecycleScope.launch {
                getFavourites()
            }
        }
        custom_toolbar?.ivCart?.setOnClickListener {
            lifecycleScope.launch {
                getCart()
            }
        }
    }

    private suspend fun getFavourites() {
        lifecycleScope.launch {
            mainActivityViewModel?.getFavourites()
        }
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        lifecycleScope.launch {
            getData()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView2.layoutManager = LinearLayoutManager(this)
    }

    private suspend fun getData() {
        lifecycleScope.launch {
            mainActivityViewModel?.getData()
        }
    }

    private fun addToCart(mainActivityViewModel: MainActivityViewModel, data: DataModel) {
        lifecycleScope.launch {
            mainActivityViewModel.addToCart(data)
        }
    }

    private suspend fun getCart() {
        lifecycleScope.launch {
            mainActivityViewModel?.getCart(false)
        }
    }

    private fun insertFavouriteData(mainActivityViewModel: MainActivityViewModel, data: DataModel) {
        lifecycleScope.launch {
            mainActivityViewModel.insertFavouriteItem(data)
        }
    }

    private fun deleteFavouriteData(mainActivityViewModel: MainActivityViewModel, data: DataModel) {
        lifecycleScope.launch {
            mainActivityViewModel.deleteFavouriteItem(data)
        }
    }

    private fun insertData(mainActivityViewModel: MainActivityViewModel) {
        lifecycleScope.launch {
            mainActivityViewModel.insertData()
        }
    }
}