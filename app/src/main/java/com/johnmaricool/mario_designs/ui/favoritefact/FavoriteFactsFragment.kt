package com.johnmaricool.mario_designs.ui.favoritefact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.adapters.FavoriteFactAdapter
import com.johnmaricool.mario_designs.databinding.FavoriteFactsFragmentBinding
import com.johnmaricool.mario_designs.interfaces_kids.OnPrayerItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFactsFragment : Fragment(R.layout.favorite_facts_fragment), OnPrayerItemClickListener {

    private var _binding: FavoriteFactsFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: FavoriteFactAdapter

    @Inject
    lateinit var request: AdRequest

    private val viewModel: FavoriteFactsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FavoriteFactsFragmentBinding.bind(view)
        viewModel.getData()
        binding.adV.loadAd(request)
        binding.recyclerFav.apply {
            setHasFixedSize(true)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.recyclerFav.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) {
            adapter.getAllFavPrayers(it)
        }
        adapter.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPrayerItemClick(prayer: String, id: Int) {
        val action = FavoriteFactsFragmentDirections.actionFavoriteFactsFragmentToFactsFragment(id)
        findNavController().navigate(action)
    }

}