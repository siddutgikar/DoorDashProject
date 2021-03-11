package com.sidd.doordash.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.sidd.doordash.R
import com.sidd.doordash.adapter.MenuRecyclerAdapter
import com.sidd.doordash.data.RestaurantDetail
import com.sidd.doordash.databinding.RestaurantDetailFragmentBinding
import com.sidd.doordash.menuListImages
import com.sidd.doordash.viewmodel.RestaurantDetailViewModel
import com.sidd.doordash.viewmodel.RestaurantDetailViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RestaurantDetailFragment : Fragment() {

    private val args: RestaurantDetailFragmentArgs by navArgs()


    @Inject
    lateinit var restaurantDetailViewModelFactory: RestaurantDetailViewModelFactory

    private val viewModel: RestaurantDetailViewModel by viewModels {
        RestaurantDetailViewModel.provideFactory(
            restaurantDetailViewModelFactory,
            args.restaurantId
        )
    }
    private lateinit var viewBinding: RestaurantDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = RestaurantDetailFragmentBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        val imageAdapter = MenuRecyclerAdapter(menuListImages)
        with(viewBinding.menuRecyclerView) {
            adapter = imageAdapter
            val itemDecoration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(ColorDrawable(resources.getColor(R.color.gray_border)))
            addItemDecoration(itemDecoration)
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
        }
        imageAdapter.notifyDataSetChanged()

        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
        viewModel.restaurantDetail.observe(viewLifecycleOwner) {
            if (it != null) {
                updateData(it)
            }
        }

        viewModel.restaurant.observe(viewLifecycleOwner) {
            if (it != null) {
                Glide.with(viewBinding.coverImageView.context).load((it.headerImageURL))
                    .centerInside()
                    .into(viewBinding.coverImageView)
            }
        }




        return viewBinding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun updateData(restaurantDetail: RestaurantDetail) {
        with(viewBinding) {
            Glide.with(logoImageView.context).load((restaurantDetail.coverImageURL))
                .into(logoImageView)
            nameTextView.text = restaurantDetail.name
            ratingsTextView.text = restaurantDetail.averageRating.toString()
            descTextView.text = restaurantDetail.description
            ratingDetails.text = restaurantDetail.getRatingDetails()
            deliveryFeeTextView.text = "$0.0"
            restaurantDeliveryTime.text = restaurantDetail.status
            callRestaurantFloatingButton.setOnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + restaurantDetail.phone)
                startActivity(dialIntent)
            }
        }
    }
}