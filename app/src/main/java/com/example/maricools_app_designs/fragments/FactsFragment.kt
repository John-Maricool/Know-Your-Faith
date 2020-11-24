package com.example.maricools_app_designs.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.databinding.FactsFragmentBinding
import com.example.maricools_app_designs.viewmodels.FactsViewModel

class FactsFragment : Fragment(R.layout.facts_fragment) {

    private lateinit var viewModel: FactsViewModel
    private var _binding: FactsFragmentBinding? = null
    private val binding get() = _binding!!

    val args: FactsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FactsFragmentBinding.bind(view)

        binding.factText.text = args.id.toString()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FactsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}