package com.johnmaricool.mario_designs.ui.orderofmass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.androidcomponents.MainActivity
import com.johnmaricool.mario_designs.databinding.OOMFragmentBinding
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

        (activity as MainActivity).supportActionBar?.title = args.title
        binding.oomTitle.text = args.title
        binding.oomBody.text = model.getOOM(args.title)

    }

    override fun onStart() {
        super.onStart()
        binding.adV?.loadAd(request)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}