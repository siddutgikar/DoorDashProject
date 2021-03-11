package com.sidd.doordash.api

import com.sidd.doordash.DEFAULT_OFFSET
import com.sidd.doordash.DEFAULT_PAGE_LIMIT
import com.sidd.doordash.DOORDASH_LAT
import com.sidd.doordash.DOORDASH_LNG
import com.sidd.doordash.data.RestaurantListResponse
import com.sidd.doordash.data.RestaurantDetail
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  API Service to connect to DoorDash Server
 *  Test URL https://api.doordash.com/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=50
 */
interface DoorDashService {

    @GET("/v1/store_feed/")
    suspend fun getRestaurants(
        @Query("lat") latitude: Double = DOORDASH_LAT,
        @Query("lng") longitude: Double = DOORDASH_LNG,
        @Query("offset") pageOffset: Int = DEFAULT_OFFSET,
        @Query("limit") pageLimit: Int = DEFAULT_PAGE_LIMIT
    ): RestaurantListResponse

    @GET("/v2/restaurant/{id}")
    suspend fun getRestaurant(
        @Path("id") id: Int
    ): RestaurantDetail

    companion object {
        private const val BASE_URL = "https://api.doordash.com"
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        fun create(): DoorDashService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(DoorDashService::class.java)
        }
    }

}