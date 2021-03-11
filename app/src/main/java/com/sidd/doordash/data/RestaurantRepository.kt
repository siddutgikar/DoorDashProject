package com.sidd.doordash.data

import com.sidd.doordash.api.DoorDashService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantRepository @Inject constructor(
    private val restaurantDao: RestaurantDao,
    private val doorDashService: DoorDashService
) {

    /**
     * Get the list of restaurants
     */
    suspend fun getRestaurants(): Flow<List<Restaurant>> {
        val response = doorDashService.getRestaurants()
        restaurantDao.insertAll(response.restaurants)
        return restaurantDao.getRestaurants()
    }

    /**
     * Get the restaurant details
     */
    suspend fun getRestaurantDetail(restaurantID: Int): Flow<RestaurantDetail> {
        val response = doorDashService.getRestaurant(restaurantID)
        restaurantDao.insertRestaurantDetail(response)
        return restaurantDao.getRestaurantDetail(restaurantID)
    }

    /**
     * Get the details of the restaurant from the list
     */
    fun getRestaurantFromList(restaurantID: Int): Flow<Restaurant> {
        return restaurantDao.getRestaurant(restaurantID)
    }


}