package com.example.maricools_app_designs.ui.favoritefact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.databinding.FavoriteFactsFragmentBinding
import com.example.maricools_app_designs.adapters.FavoriteFactAdapter
import com.example.maricools_app_designs.interfaces_kids.OnPrayerItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFactsFragment : Fragment(R.layout.favorite_facts_fragment), OnPrayerItemClickListener {

    private var _binding: FavoriteFactsFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: FavoriteFactAdapter

    private val viewModel: FavoriteFactsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FavoriteFactsFragmentBinding.bind(view)

        binding.recyclerFav.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recyclerFav.adapter = adapter

        viewModel.getFavFacts().observe(viewLifecycleOwner, Observer {
            adapter.getAllFavPrayers(it)
        })
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