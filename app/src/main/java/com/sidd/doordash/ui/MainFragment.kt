package com.sidd.doordash.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sidd.doordash.ConnectionLiveData
import com.sidd.doordash.R
import com.sidd.doordash.adapter.RestaurantRecyclerAdapter
import com.sidd.doordash.data.Restaurant
import com.sidd.doordash.databinding.MainFragmentBinding

import com.sidd.doordash.viewmodel.RestaurantListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: RestaurantListViewModel by viewModels()
    private lateinit var viewBinding: MainFragmentBinding
    private lateinit var connectionLiveData: ConnectionLiveData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = MainFragmentBinding.inflate(layoutInflater)
        connectionLiveData = ConnectionLiveData(viewBinding.root.context)
        val restaurantAdapter = RestaurantRecyclerAdapter(emptyList<Restaurant>().toMutableList())
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(false)
            it.setDisplayShowHomeEnabled(false)
            it.setDisplayHomeAsUpEnabled(false)
        }
        with(viewBinding.restaurantRecyclerView) {
            val itemDecoration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(ColorDrawable(resources.getColor(R.color.gray_border)))
            addItemDecoration(itemDecoration)
            adapter = restaurantAdapter
            layoutManager = LinearLayoutManager(context)

        }
        connectionLiveData.observe(viewLifecycleOwner) {
            viewModel.setNetworkState(it)
        }
        viewModel.restaurants.observe(viewLifecycleOwner) { restaurants ->
            restaurantAdapter.items.clear()
            restaurantAdapter.items.addAll(restaurants)
            restaurantAdapter.notifyDataSetChanged()
        }

        return viewBinding.root
    }

}