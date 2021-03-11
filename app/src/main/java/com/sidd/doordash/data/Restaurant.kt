package com.sidd.doordash.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey @ColumnInfo(name = "id") @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("average_rating") val averageRating: Float,
    @field:SerializedName("num_ratings") val ratingsCount: Int,
    @field:SerializedName("distance_from_consumer") val distance: Double,
    @field:SerializedName("cover_img_url") val coverImageURL: String,
    @field:SerializedName("header_img_url") val headerImageURL: String,
    @field:SerializedName("price_range") val priceRange: Int,
    @field:SerializedName("status") val status: Status
) {

    fun getDisplayPriceRange(): String {
        val stringBuilder = StringBuilder()
        repeat(priceRange) {
            stringBuilder.append("$")
        }
        return stringBuilder.toString()
    }
}


data class Status(
    @field:SerializedName("asap_minutes_range") val minRange: Array<Int>
) {
    fun getMinDeliveryTime(): String? {
        return if (minRange.size > 1) {
            if (minRange[0] == minRange[1]) minRange[0].toString() + " min"
            else minRange[0].toString() + "-" + minRange[1].toString() + " min"
        } else ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Status

        if (!minRange.contentEquals(other.minRange)) return false

        return true
    }

    override fun hashCode(): Int {
        return minRange.contentHashCode()
    }
}