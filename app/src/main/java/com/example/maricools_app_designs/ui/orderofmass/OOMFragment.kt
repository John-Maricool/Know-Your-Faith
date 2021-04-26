package com.example.maricools_app_designs.ui.orderofmass

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.maricools_app_designs.androidcomponents.MainActivity
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.databinding.OOMFragmentBinding
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OOMFragment : Fragment(R.layout.o_o_m_fragment) {

    @Inject
    lateinit var request: AdRequest
    var _binding: OOMFragmentBinding? = null
    val binding get() = _binding!!
    val model: OOMViewModel by viewModels()
    private val args: OOMFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = OOMFragmentBinding.bind(view)
        binding.adView.adView.loadAd(request)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = args.title
        binding.oomTitle.text = args.title
            binding.oomBody.text = model.getOOM(args.title)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}