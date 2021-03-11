package com.sidd.doordash.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for Restaurants
 */
@Dao
interface RestaurantDao {
    @Query("Select * FROM restaurants ORDER BY distance")
    fun getRestaurants(): Flow<List<Restaurant>>

    @Query("SELECT * FROM restaurants WHERE id = :restaurantID")
    fun getRestaurant(restaurantID: Int): Flow<Restaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(restaurants: List<Restaurant>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurantDetail(restaurantDetail: RestaurantDetail)

    @Query("SELECT * FROM restaurantdetail WHERE id = :restaurantID")
    fun getRestaurantDetail(restaurantID: Int): Flow<RestaurantDetail>

}
