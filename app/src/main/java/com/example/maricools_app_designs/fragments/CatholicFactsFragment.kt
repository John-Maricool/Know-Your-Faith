package com.example.maricools_app_designs.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.`interface`.onPrayerItemClickListener
import com.example.maricools_app_designs.databinding.FragmentCatholicFactsBinding
import com.example.maricools_app_designs.viewmodels.FactsListViewModel


class CatholicFactsFragment : Fragment(R.layout.fragment_catholic_facts){
    //onPrayerItemClickListener, SearchView.OnQueryTextListener {
/*
    var _binding : FragmentCatholicFactsBinding? = null
    val binding get() = _binding!!
    lateinit var model: FactsListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatholicFactsBinding.bind(view)

        model = ViewModelProvider(this).get(FactsListViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.apply {
            factsRecyclerView.layoutManager = LinearLayoutManager(activity)
            factsRecyclerView.setHasFixedSize(true)
            factsRecyclerView.adapter = model.adapter
        }
        model.adapter.setOnItemClickListener(this)
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        super.onDestroy()
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
        model.adapter.filter.filter(newText)

        return true
    }

    override fun onPrayerItemClick(prayer: String, id: Int) {
        val action = CatholicFactsFragmentDirections.actionCatholicFactsFragmentToFactsFragment(prayer, id)
        findNavController().navigate(action)
    }*/
}