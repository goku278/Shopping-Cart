package com.example.mystore.utils

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.room.Room
import com.example.mystore.db.dao.AppDatabase
import com.example.mystore.db.entity.Cart
import com.example.mystore.db.entity.Category
import com.example.mystore.db.entity.FavouriteItem
import com.example.mystore.db.entity.Item
import com.example.mystore.model.DataModel
import com.example.mystore.model.DataResponse
import com.example.mystore.ui.home.ShoppingCart
import com.google.gson.Gson
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AppUtils {

    // This AppUtils object file has insert - delete - retrieve the data from the room database.

    var cart: Cart? = null

    suspend fun insertData(context: Context) {
        // Only inserting the category and Items data are done here
        val gson = Gson()
        val json =
            "{\r\n  \"status\": true,\r\n  \"message\": \"Product Categories\",\r\n  \"error\": null,\r\n  \"categories\": [\r\n    {\r\n      \"id\": 55,\r\n      \"name\": \"Food\",\r\n      \"items\": [\r\n        {\r\n          \"id\": 5501,\r\n          \"name\": \"Potato Chips\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2553/2553691.png\",\r\n          \"price\": 40.00\r\n        },\r\n        {\r\n          \"id\": 5502,\r\n          \"name\": \"Penne Pasta\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2553/2553691.png\",\r\n          \"price\": 110.40\r\n        },\r\n        {\r\n          \"id\": 5503,\r\n          \"name\": \"Tomato Ketchup\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2553/2553691.png\",\r\n          \"price\": 80.00\r\n        },\r\n        {\r\n          \"id\": 5504,\r\n          \"name\": \"Nutella Spread\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2553/2553691.png\",\r\n          \"price\": 120.00\r\n        },\r\n        {\r\n          \"id\": 5505,\r\n          \"name\": \"Everyday Granola\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2553/2553691.png\",\r\n          \"price\": 450.00\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"id\": 56,\r\n      \"name\": \"Beverages\",\r\n      \"items\": [\r\n        {\r\n          \"id\": 5601,\r\n          \"name\": \"Orange Fanta 1 Litre\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2405/2405479.png\",\r\n          \"price\": 100.00\r\n        },\r\n        {\r\n          \"id\": 5602,\r\n          \"name\": \"Keventers Thick Shake 60 ml\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2405/2405479.png\",\r\n          \"price\": 79.99\r\n        },\r\n        {\r\n          \"id\": 5603,\r\n          \"name\": \"Fresh Jaljeera\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2405/2405479.png\",\r\n          \"price\": 50.00\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"id\": 57,\r\n      \"name\": \"Hygiene Essentials\",\r\n      \"items\": [\r\n        {\r\n          \"id\": 5701,\r\n          \"name\": \"Clear Baby Shampoo\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2553/2553642.png\",\r\n          \"price\": 300.00\r\n        },\r\n        {\r\n          \"id\": 5702,\r\n          \"name\": \"Walnut Scrub Daily Glow\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2553/2553642.png\",\r\n          \"price\": 165.00\r\n        },\r\n        {\r\n          \"id\": 5703,\r\n          \"name\": \"Shine Detergent Powder 1 kg\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2553/2553642.png\",\r\n          \"price\": 300.00\r\n        },\r\n        {\r\n          \"id\": 5704,\r\n          \"name\": \"All-in-one Cleaner\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2553/2553642.png\",\r\n          \"price\": 90.00\r\n        },\r\n        {\r\n          \"id\": 5705,\r\n          \"name\": \"Soft Tissue Box\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2553/2553642.png\",\r\n          \"price\": 40.00\r\n        },\r\n        {\r\n          \"id\": 5706,\r\n          \"name\": \"Aroma Essence Balls 10 Pieces\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/2553/2553642.png\",\r\n          \"price\": 200.00\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"id\": 58,\r\n      \"name\": \"Pooja Daily Needs\",\r\n      \"items\": [\r\n        {\r\n          \"id\": 5801,\r\n          \"name\": \"Camphor Large\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/7096/7096435.png\",\r\n          \"price\": 40.00\r\n        },\r\n        {\r\n          \"id\": 5802,\r\n          \"name\": \"Mix Fresh Flowers\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/7096/7096435.png\",\r\n          \"price\": 80.00\r\n        },\r\n        {\r\n          \"id\": 5803,\r\n          \"name\": \"Sandalwood Incense Sticks\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/7096/7096435.png\",\r\n          \"price\": 90.00\r\n        },\r\n        {\r\n          \"id\": 5804,\r\n          \"name\": \"Premium Candle Pack of 10\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/7096/7096435.png\",\r\n          \"price\": 400.00\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"id\": 59,\r\n      \"name\": \"Electronic Items\",\r\n      \"items\": [\r\n        {\r\n          \"id\": 5901,\r\n          \"name\": \"USB Cable Type C\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/3659/3659899.png\",\r\n          \"price\": 200.00\r\n        },\r\n        {\r\n          \"id\": 5902,\r\n          \"name\": \"HearSense Bluetooth Speaker\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/3659/3659899.png\",\r\n          \"price\": 3500.00\r\n        },\r\n        {\r\n          \"id\": 5903,\r\n          \"name\": \"Smartwatch Black NewGen\",\r\n          \"icon\": \"https://cdn-icons-png.flaticon.com/128/3659/3659899.png\",\r\n          \"price\": 6500.00\r\n        }\r\n      ]\r\n    }\r\n  ]\r\n}"

        val data = gson.fromJson(json, DataResponse::class.java)
        val database =
            Room.databaseBuilder(context, AppDatabase::class.java, "shopping_cart_db").build()
        val categoryDao = database.categoryDao()
        val itemDao = database.itemDao()
        for (category in data.categories) {
            Category(category.id, category.name).let { categoryDao.insertCategory(it) }
            for (item in category.items) {
                Item(
                    item.id,
                    item.name,
                    item.icon,
                    item.price,
                    category.id,
                    "false"
                ).let { itemDao.insertItem(it) }
            }
        }
    }

    suspend fun checkIfTableRecordExists(context: Context, listener: ShoppingCart.Listener?) {
        // In this method we are checking if the tables are already created with minimum 1 row(s)
        // If atleat a single row in the tables are found then we are returning true, else false
        val database =
            Room.databaseBuilder(context, AppDatabase::class.java, "shopping_cart_db").build()
        val categoryDao = database.categoryDao()
        val itemDao = database.itemDao()

        if (!categoryDao.getCategories().isNullOrEmpty() && categoryDao.getCategories().size > 1 &&
            !itemDao.getItems().isNullOrEmpty() && itemDao.getItems().size > 1) {
            listener?.onTableRecordsFound(true)
        }
        else {
            listener?.onTableRecordsFound(false)
        }
    }

    suspend fun insertFavouriteItem(context: Context, data: DataModel) {
        // Inserting the favourite items, which ever user clicks on the heart icon.
        val gson = Gson()
        if (data != null && data.name != null && data.icon != null && data.price != null && data.isFavourite != null) {
            val json = gson.toJson(data)
            val data = gson.fromJson(json, FavouriteItem::class.java)
            val database =
                Room.databaseBuilder(context, AppDatabase::class.java, "shopping_cart_db").build()
            val favouriteDao = database.favouriteItemDao()
            val itemDao = database.itemDao()
            favouriteDao.insertFavouriteItem(data)
            var itemsList = itemDao.getItems()
            var isFound = 0

            var item = itemDao.findRecordByNameIcon(data.name,data.icon)
            item?.isFavourite = "true"
            if (item != null) {
                itemDao.update(item)
            }
            item = itemDao.findAllItemsWithIsFavourite("true")
            Log.d("AppUtils","item ----> $item")
            var favouriteItemsList = favouriteDao.getFavouriteItems()
            Log.d("AppUtil", "favouriteItemsList => $favouriteItemsList")
        }
    }

    suspend fun deleteFavouriteItem(context: Context, data: DataModel) {
        // Once user de-select the heart icon,
        // So, it functions as removing the particular item from the favourite items list
        val gson = Gson()
        if (data != null && data.name != null && data.icon != null && data.price != null && data.isFavourite != null) {
            val json = gson.toJson(data)
            val data = gson.fromJson(json, FavouriteItem::class.java)
            val catList = ArrayList<Category>()
            val database =
                Room.databaseBuilder(context, AppDatabase::class.java, "shopping_cart_db").build()
            val categoryDao = database.categoryDao()
            val itemDao = database.itemDao()
            var isFound = false
            for (i in categoryDao.getCategories()) {
                var item = itemDao.getItemsForCategory(i.id)
                for (it in item) {
                    if (it.name.equals(data.name) && it.icon.equals(data.icon) && it.price.equals(
                            data.price
                        )
                    ) {
                        data.id = it.id
                        isFound = true
                        break
                    }
                    if (isFound) break
                }
            }
            val favouriteDao = database.favouriteItemDao()
            favouriteDao.deleteFavouriteItem(data)
            var item = itemDao.findRecordByNameIcon(data.name,data.icon)
            item?.isFavourite = "false"
            if (item != null) {
                itemDao.update(item)
            }
            var favouriteItemsList = favouriteDao.getFavouriteItems()
            Log.d("AppUtil", "favouriteItemsList after delete => $favouriteItemsList")
        }
    }

    suspend fun getData(context: Context, listener: ShoppingCart.Listener?) {
        // Here, we are fetching the data and parsing the data, then displaying the data
        // on the console.
        val database =
            Room.databaseBuilder(context, AppDatabase::class.java, "shopping_cart_db").build()
        val categoryDao = database.categoryDao()
        val itemDao = database.itemDao()

        val itemsList = ArrayList<ArrayList<Item>>()

        Log.d("AppUtils", "categories => ${categoryDao.getCategories()}")

        var categoryName = HashMap<Int, String>()

        var strBuilder = StringBuilder()

        var count1 = 0

        for ((count, i) in categoryDao.getCategories().withIndex()) {
            Log.d("AppUtils", "itemsList => ${itemDao.getItemsForCategory(i.id)}")
            val item = itemDao.getItemsForCategory(i.id)
            itemsList.add(item as ArrayList<Item>)
            if (!strBuilder.contains(i.name)) {
                strBuilder.append("${i.name}\n")
            }
            if (!categoryName.containsValue(i.name)) {
                categoryName[count] = i.name
            }
            count1++
        }
        Log.d("AppUtils", "count1 == $count1")
        Paper.book().write("category_name", strBuilder.toString())
        val item = itemDao.findAllItemsWithIsFavourite("true")
        Log.d("AppUtils","item ----> $item")
        listener?.onDataFetched(itemsList)
    }

    fun mapEntityToModel(list: ArrayList<Item>, ivHeart: ImageView?): ArrayList<DataModel> {
        // Mapping the room database table to the kotlin model class.
        var list2 = ArrayList<DataModel>()
        for (i in list) {
            var dataModel = DataModel(
                i.id.toString(),
                i.name,
                i.price.toString(),
                i.icon,
                "false",
                i.isFavourite,
                ""
            )
            list2.add(dataModel)
        }
        return list2
    }

    suspend fun addToCart(context: Context, data: DataModel, listener: ShoppingCart.Listener?) {
        // Adding the selected items to the cart section.
        val gson = Gson()
        if (data != null && data.name != null && data.icon != null && data.price != null && data.isFavourite != null) {
            val json = gson.toJson(data)
            val data2 = gson.fromJson(json, Cart::class.java)
            // Use a coroutine to perform database operations off the main thread
            withContext(Dispatchers.IO) {
                val database =
                    Room.databaseBuilder(context, AppDatabase::class.java, "shopping_cart_db")
                        .build()
                val cartDao = database.cartDao()
                var selectedCart = Cart(-100, "", "", "", "", 100)
                var carts = cartDao.getCart()
                var isFound = 0
                if (carts != null) {
                    for (c in carts) {
                        if (c.name.equals(data.name) && c.price.equals(data.price) && c.icon.equals(data.icon)) {
                            selectedCart = c
                            isFound = 1
                            break
                        }
                        if (isFound == 1) break
                    }
                    if (isFound == 1) {
                        if (!selectedCart.qty.isNullOrEmpty()) {
                            selectedCart.qty = (selectedCart.qty.toInt() + 1).toString()
                            cartDao.update(selectedCart)
                        }
                    } else {
                        data2.qty = "1"
                        cartDao.insertCart(data2)
                    }
                    val cartts = cartDao.getCart()
                    var count =0
                    for (c in cartts) {
                        count += c.qty.toString().trim().toInt()
                    }
                    listener?.onCountCartItems(count)
                    Log.d("AppUtils", "cartts --> $cartts")
                }
            }
        }
    }

    suspend fun getCart(context: Context, isOnStart: Boolean, listener: ShoppingCart.Listener?) {
        // Fetching all the Add To Cart items, from the room database.
        try {
            withContext(Dispatchers.IO) {
                val database =
                    Room.databaseBuilder(context, AppDatabase::class.java, "shopping_cart_db")
                        .build()
                val cartDao = database.cartDao()
                val cartts = cartDao.getCart()
                Log.d("AppUtils", "cartts --> $cartts")
                if (!isOnStart) {
                    listener?.onCartDataFetched(cartts as ArrayList<Cart>)
                }
                var count =0
                for (c in cartts) {
                    count += c.qty.toString().trim().toInt()
                }
                listener?.onCountCartItems(count)
                database.close() // Close the database when you're done
            }
        } catch (e: Exception) {
            Log.e("AppUtils", "Error fetching cart data: ${e.message}")
        }
    }

    suspend fun getFavourites(context: Context, listener: ShoppingCart.Listener?) {
        // Fetching all the favourite items, as selected by the user on the screen.
        try {
            withContext(Dispatchers.IO) {
                val database =
                    Room.databaseBuilder(context, AppDatabase::class.java, "shopping_cart_db")
                        .build()
                val favouriteDao = database.favouriteItemDao()
                val favouriteItems = favouriteDao.getFavouriteItems()
                Log.d("AppUtils", "favouriteItems --> $favouriteItems")
                listener?.onFavouriteDataFetched(favouriteItems as ArrayList<FavouriteItem>)
                database.close() // Close the database when you're done
            }
        } catch (e: Exception) {
            Log.e("AppUtils", "Error fetching cart data: ${e.message}")
        }
    }
}