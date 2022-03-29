package com.johnmaricool.mario_designs.ui.favoriteprayer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

        model.getData()
        binding.adV.loadAd(request)
        binding.recyclerFav.apply {
            setHasFixedSize(true)
        }
        binding.recyclerFav.adapter = adapter

        model.data.observe(viewLifecycleOwner) {
            adapter.getAllFavPrayers(it)
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPrayerItemClick(prayer: String, id: Int) {
        val action = FavouritesFragmentDirections.actionFavouritesFragmentToPrayerFragment(id, prayer)
        findNavController().navigate(action)
    }
}