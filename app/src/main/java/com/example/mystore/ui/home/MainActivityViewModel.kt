package com.example.mystore.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mystore.model.DataModel
import com.example.mystore.utils.AppUtils
import io.paperdb.Paper
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

open class MainActivityViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    private var listener: ShoppingCart.Listener? = null

    init {
        Paper.init(getApplication())
    }

    // Initializing the listener
    // This method initListener() is called from the MainActivity

    fun initListener(listener: ShoppingCart.Listener) {
        this.listener = listener
    }

    suspend fun insertData() {
        coroutineScope {
            AppUtils.insertData(getApplication())
        }
    }

    suspend fun checkIfTableRecordExists() {
        coroutineScope {
            AppUtils.checkIfTableRecordExists(getApplication(),listener)
        }
    }

    suspend fun insertFavouriteItem(data: DataModel) {
        coroutineScope {
            AppUtils.insertFavouriteItem(getApplication(), data)
        }
    }

    suspend fun deleteFavouriteItem(data: DataModel) {
        coroutineScope {
            AppUtils.deleteFavouriteItem(getApplication(), data)
        }
    }

    suspend fun addToCart(data: DataModel) {
        coroutineScope {
            AppUtils.addToCart(getApplication(), data, listener)
        }
    }

    suspend fun getFavourites() {
        AppUtils.getFavourites(getApplication(), listener)
    }

    suspend fun getData() {
        AppUtils.getData(getApplication(),listener)
    }

    suspend fun getCart(isOnStart: Boolean) {
        AppUtils.getCart(getApplication(),isOnStart,listener)
    }
}