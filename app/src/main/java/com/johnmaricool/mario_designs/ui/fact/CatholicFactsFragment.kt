package com.johnmaricool.mario_designs.ui.fact

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.adapters.FactAdapter
import com.johnmaricool.mario_designs.databinding.FragmentCatholicFactsBinding
import com.johnmaricool.mario_designs.interfaces_kids.OnPrayerItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CatholicFactsFragment: Fragment(R.layout.fragment_catholic_facts), OnPrayerItemClickListener, SearchView.OnQueryTextListener {

    private var _binding: FragmentCatholicFactsBinding? = null
    private val binding get() = _binding!!
    private val model: FactsListViewModel by viewModels()

    @Inject
    lateinit var adRequest: AdRequest

    @Inject
    lateinit var adapter: FactAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatholicFactsBinding.bind(view)
        model.getData()
        binding.expandableRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
        }
        setHasOptionsMenu(true)

        binding.expandableRecyclerView.adapter = adapter
        model.data.observe(viewLifecycleOwner) {
            adapter.getFactList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        val searchItem: MenuItem = menu.findItem(R.id.search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return true
    }

    override fun onStart() {
        super.onStart()
        binding.adV.loadAd(adRequest)
        adapter.setOnClickListener(this)
    }

    override fun onPrayerItemClick(prayer: String, id: Int) {
        val action = CatholicFactsFragmentDirections.actionCatholicFactsFragmentToFactsFragment()
        findNavController().navigate(action)
    }
}