package com.example.maricools_app_designs.ui.prayer

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.adapters.PrayerFragmentMainScreenAdapter
import com.example.maricools_app_designs.databinding.FragmentCatholicPrayerBinding
import com.example.maricools_app_designs.interfaces_kids.OnPrayerItemClickListener
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class CatholicPrayerFragment : Fragment(R.layout.fragment_catholic_prayer), SearchView.OnQueryTextListener,
    OnPrayerItemClickListener{
    private var _binding: FragmentCatholicPrayerBinding? = null
    private val binding get() = _binding!!

   private val model: PrayerListViewModel by viewModels()

    @Inject
     lateinit var adapter1: PrayerFragmentMainScreenAdapter
    @Inject
     lateinit var adapter2: PrayerFragmentMainScreenAdapter
    @Inject
     lateinit var adapter3: PrayerFragmentMainScreenAdapter
    @Inject
     lateinit var adapter4: PrayerFragmentMainScreenAdapter
    @Inject
    lateinit var request: AdRequest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatholicPrayerBinding.bind(view)
        setHasOptionsMenu(true)
        binding.adView.loadAd(request)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.apply {
            recyclerPrayers.layoutManager = LinearLayoutManager(activity)
            recyclerPrayers.setHasFixedSize(false)
            ViewCompat.setNestedScrollingEnabled(recyclerPrayers, false)
        }
        adapter1.header = "Basic Prayers"
        adapter2.header = "Jesus Christ"
        adapter3.header = "Rosary"
        adapter4.header = "Prayer to Angels and saints"

        val concat = ConcatAdapter(adapter1, adapter2, adapter3, adapter4)
        binding.recyclerPrayers.adapter = concat

        model.basic.observe(viewLifecycleOwner, Observer {
            adapter1.getPrayerList(it)
        })
        model.jesus.observe(viewLifecycleOwner, Observer {
            adapter2.getPrayerList(it)
        })
        model.rosary.observe(viewLifecycleOwner, Observer {
            adapter3.getPrayerList(it)
        })
        model.saints.observe(viewLifecycleOwner, Observer {
            adapter4.getPrayerList(it)
        })
    }

    override fun onStart() {
        super.onStart()
        adapter1.setOnClickListener(this)
        adapter2.setOnClickListener(this)
        adapter3.setOnClickListener(this)
        adapter4.setOnClickListener(this)
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
        adapter1.filter.filter(newText)
        adapter2.filter.filter(newText)
        adapter3.filter.filter(newText)
        adapter4.filter.filter(newText)
        return true
    }

    override fun onPrayerItemClick(prayer: String, id: Int) {
        val action = CatholicPrayerFragmentDirections.actionCatholicPrayerFragmentToPrayerFragment(
                prayer,
                id
        )
        findNavController().navigate(action)
    }
}

