package com.sidd.doordash.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidd.doordash.data.Restaurant
import com.sidd.doordash.data.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantListViewModel @Inject internal constructor(
    private val restaurantRepository: RestaurantRepository
) : ViewModel() {

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    private val networkStateLiveData = MutableLiveData<Boolean>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("ListViewModel", "$exception")
    }

    /**
     * Get the restaurant list
     */
    val restaurants = _restaurants


    init {
        getRestaurantList()
    }


    fun setNetworkState(isConnected: Boolean) {
        if (networkStateLiveData.value != isConnected) {
            networkStateLiveData.value = isConnected
            if (isConnected) {
                getRestaurantList()
            }

        }
    }


    private fun getRestaurantList() {
        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            restaurantRepository.getRestaurants().collect {
                _restaurants.postValue(it)
            }
        }
    }

}