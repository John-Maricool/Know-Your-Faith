package com.johnmaricool.mario_designs.ui.orderofmass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.databinding.FragmentOrderOfMassBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderOfMassFragment : Fragment(R.layout.fragment_order_of_mass), View.OnClickListener {

    @Inject
    lateinit var request: AdRequest

    var _binding: FragmentOrderOfMassBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderOfMassBinding.bind(view)

        binding.communionRite.setOnClickListener(this)
        binding.concludingRite.setOnClickListener(this)
        binding.introductoryRite.setOnClickListener(this)
        binding.liturgyEucharist.setOnClickListener(this)
        binding.liturgyWord.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        binding.adV.loadAd(request)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        val btn = p0 as Button
        val action = OrderOfMassFragmentDirections.actionOrderOfMassFragmentToOOMFragment(btn.text.toString())
        findNavController().navigate(action)
    }
}
