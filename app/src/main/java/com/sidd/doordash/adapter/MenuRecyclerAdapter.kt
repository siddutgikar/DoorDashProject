package com.sidd.doordash.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sidd.doordash.databinding.MenuGridItemBinding

/**
 * Menu adapter. Currently uses static list of images.
 *
 */
class MenuRecyclerAdapter(
    var items: List<String>
) : RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MenuGridItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])


    inner class ViewHolder(private val binding: MenuGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            with(binding) {
                Glide.with(menuItemImageView.context).load((item))
                    .into(menuItemImageView)
            }

        }

    }
}