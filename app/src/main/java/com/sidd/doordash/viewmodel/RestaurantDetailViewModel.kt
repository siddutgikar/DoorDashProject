package com.sidd.doordash.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.sidd.doordash.data.RestaurantDetail
import com.sidd.doordash.data.RestaurantRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RestaurantDetailViewModel @AssistedInject constructor(
    private val restaurantRepository: RestaurantRepository,
    @Assisted private val restaurantId: Int
) : ViewModel() {

    val restaurant = restaurantRepository.getRestaurantFromList(restaurantId).asLiveData()
    private val _restaurantDetail = MutableLiveData<RestaurantDetail>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("ListViewModel", "$exception")
    }

    val restaurantDetail: LiveData<RestaurantDetail> = _restaurantDetail


    init {
        getRestaurantDetail()
    }

    private fun getRestaurantDetail() {
        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            restaurantRepository.getRestaurantDetail(restaurantId).collect {
                _restaurantDetail.postValue(it)
            }
        }
    }

    companion object {
        fun provideFactory(
            assistedFactory: RestaurantDetailViewModelFactory,
            restaurantId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(restaurantId) as T
            }
        }
    }
}

@AssistedFactory
interface RestaurantDetailViewModelFactory {
    fun create(restaurantId: Int): RestaurantDetailViewModel
}