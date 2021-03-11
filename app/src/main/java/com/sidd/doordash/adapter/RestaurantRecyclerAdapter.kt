package com.sidd.doordash.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sidd.doordash.Utils
import com.sidd.doordash.data.Restaurant
import com.sidd.doordash.databinding.RestaurantListItemBinding
import com.sidd.doordash.ui.MainFragmentDirections

/**
 * Adapter for restaurant list
 */
class RestaurantRecyclerAdapter(
    var items: MutableList<Restaurant>
) : RecyclerView.Adapter<RestaurantRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RestaurantListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])


    inner class ViewHolder(private val binding: RestaurantListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener() {
                val restaurant = it.tag as Restaurant
                if (Utils.isNetworkAvailable(binding.root.context)) {
                    binding.root.findNavController()
                        .navigate(
                            MainFragmentDirections.actionMainFragmentToRestaurantDetailFragment(
                                restaurant.id
                            )
                        )
                }
            }
        }

        fun bind(item: Restaurant) {
            with(binding) {
                Glide.with(restaurantCoverImageView.context).load((item.coverImageURL))
                    .into(restaurantCoverImageView)
                restaurantNameTextView.text = item.name
                restaurantDeliveryTime.text = item.status.getMinDeliveryTime()
                restaurantPriceRange.text = item.getDisplayPriceRange()
                restaurantDescription.text = item.description
                binding.root.tag = item

            }

        }

    }
}