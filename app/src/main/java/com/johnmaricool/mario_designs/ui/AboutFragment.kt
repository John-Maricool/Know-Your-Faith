package com.johnmaricool.mario_designs.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AboutFragment : Fragment(R.layout.fragment_about) {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var request: AdRequest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAboutBinding.bind(view)
        binding.adV?.loadAd(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}