package com.sidd.doordash.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "restaurantdetail")
data class RestaurantDetail(
    @PrimaryKey @ColumnInfo(name = "id") @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("phone_number") val phone: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("average_rating") val averageRating: Float,
    @field:SerializedName("number_of_ratings") val ratingsCount: Int,
    @field:SerializedName("distance_from_consumer") val distance: Double,
    @field:SerializedName("cover_img_url") val coverImageURL: String,
    @field:SerializedName("header_image_url") val headerImageURL: String?,
    @field:SerializedName("price_range") val priceRange: Int,
    @field:SerializedName("status") val status: String,
    @field:SerializedName("status_type") val statusType: String
) {


    fun getRatingDetails(): String {

        val stringBuilder = StringBuilder()
        stringBuilder.append(ratingsCount)
            .append(" ratings")
            .append(" . ")
        repeat(priceRange) {
            stringBuilder.append("$")
        }
        return stringBuilder.toString()
    }
}