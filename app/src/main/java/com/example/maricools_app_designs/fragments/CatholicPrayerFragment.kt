package com.example.maricools_app_designs.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.`interface`.onPrayerItemClickListener
import com.example.maricools_app_designs.databinding.FragmentCatholicPrayerBinding
import com.example.maricools_app_designs.viewModels.PrayerListViewModel

class CatholicPrayerFragment : Fragment(R.layout.fragment_catholic_prayer), SearchView.OnQueryTextListener {
    private var _binding: FragmentCatholicPrayerBinding? = null
    private val binding get() = _binding!!
    lateinit var model: PrayerListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatholicPrayerBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProvider(this).get(PrayerListViewModel::class.java)

        binding.apply {
            recyclerPrayers.layoutManager = LinearLayoutManager(activity)
            recyclerPrayers.setHasFixedSize(false)
            recyclerPrayers.adapter = model.concat
        }
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()

        val adapter1Size = model.adapter1.myList.size
        val adapter2Size = model.adapter2.myList.size
        val adapter3Size = model.adapter3.myList.size

        model.adapter1.setOnItemClickListener(object : onPrayerItemClickListener {
            override fun onPrayerItemClick(prayer: String, id: Int) {
                val action =
                    CatholicPrayerFragmentDirections.actionCatholicPrayerFragmentToPrayerFragment(
                        prayer,
                        id
                    )
                findNavController().navigate(action)
            }

        })
        model.adapter2.setOnItemClickListener(object : onPrayerItemClickListener {
            override fun onPrayerItemClick(prayer: String, id: Int) {

                val action =
                    CatholicPrayerFragmentDirections.actionCatholicPrayerFragmentToPrayerFragment(
                        prayer,
                        id + adapter1Size
                    )
                findNavController().navigate(action)
            }
        })

        model.adapter3.setOnItemClickListener(object : onPrayerItemClickListener {
            override fun onPrayerItemClick(prayer: String, id: Int) {
                val action =
                    CatholicPrayerFragmentDirections.actionCatholicPrayerFragmentToPrayerFragment(
                        prayer,
                        id + adapter1Size + adapter2Size
                    )
                findNavController().navigate(action)
            }
        })
        model.adapter4.setOnItemClickListener(object : onPrayerItemClickListener {
            override fun onPrayerItemClick(prayer: String, id: Int) {
                val action =
                    CatholicPrayerFragmentDirections.actionCatholicPrayerFragmentToPrayerFragment(
                        prayer,
                        id + adapter1Size + adapter2Size + adapter3Size
                    )
                findNavController().navigate(action)
            }
        })

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
        model.adapter1.filter.filter(newText)
        model.adapter2.filter.filter(newText)
        model.adapter3.filter.filter(newText)
        model.adapter4.filter.filter(newText)

        return true
    }

}

