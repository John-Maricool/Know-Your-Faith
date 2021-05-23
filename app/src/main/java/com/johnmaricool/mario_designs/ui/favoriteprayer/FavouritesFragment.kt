package com.johnmaricool.mario_designs.ui.favoriteprayer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.adapters.FavouritePrayerAdapter
import com.johnmaricool.mario_designs.databinding.FragmentFavouritesBinding
import com.johnmaricool.mario_designs.interfaces_kids.OnPrayerItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class FavouritesFragment : Fragment(R.layout.fragment_favourites), OnPrayerItemClickListener {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: FavouritePrayerAdapter

    @Inject
    lateinit var request: AdRequest


    private val model: FavPrayerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)

        binding.adView.adView.loadAd(request)
        binding.recyclerFav.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recyclerFav.adapter = adapter

        model.getFavPrayers().observe(viewLifecycleOwner, Observer {
            adapter.getAllFavPrayers(it)
        })

        adapter.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPrayerItemClick(prayer: String, id: Int) {
       val action = FavouritesFragmentDirections.actionFavouritesFragmentToPrayerFragment2(id, prayer)
        findNavController().navigate(action)
    }
}