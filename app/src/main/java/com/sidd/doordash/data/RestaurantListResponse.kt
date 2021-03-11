package com.sidd.doordash.data

import com.google.gson.annotations.SerializedName

/**
 * Response of Restaurant List
 */
data class RestaurantListResponse(
    @field:SerializedName("stores") val restaurants: List<Restaurant>,
    @field:SerializedName("next_offset") val nextOffset: Int
)