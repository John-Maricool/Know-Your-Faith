package com.example.maricools_app_designs.ui.fact

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.databinding.FragmentCatholicFactsBinding
import com.example.maricools_app_designs.interfaces_kids.OnItemClickListener
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CatholicFactsFragment: Fragment(R.layout.fragment_catholic_facts), OnItemClickListener {

    private var _binding: FragmentCatholicFactsBinding? = null
    private val binding get() = _binding!!
    private val model: FactsListViewModel by viewModels()

    @Inject
    lateinit var adRequest: AdRequest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatholicFactsBinding.bind(view)
        binding.expandableRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
        }
        binding.adView.loadAd(adRequest)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.expandableRecyclerView.adapter = model.adapter
        model.adapter.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        binding.str.setOnRefreshListener {
            binding.expandableRecyclerView.adapter = model.adapter
            binding.str.isRefreshing = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        val action = CatholicFactsFragmentDirections.actionCatholicFactsFragmentToFactsFragment(position)
        findNavController().navigate(action)
    }
}