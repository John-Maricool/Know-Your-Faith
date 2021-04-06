package com.example.maricools_app_designs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maricools_app_designs.databinding.FragmentAboutBinding
import com.example.maricools_app_designs.databinding.FragmentCatholicFactsBinding
import com.google.android.gms.ads.AdRequest
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
        binding.adView.loadAd(request)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}