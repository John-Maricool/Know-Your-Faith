package com.example.maricools_app_designs.ui.orderofmass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.databinding.FragmentOrderOfMassBinding
import com.example.maricools_app_designs.interfaces_kids.OnPrayerItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderOfMassFragment : Fragment(R.layout.fragment_order_of_mass), View.OnClickListener {

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(p0: View?) {
        val btn = p0 as Button
        val action = OrderOfMassFragmentDirections.actionOrderOfMassFragmentToOOMFragment(btn.text.toString())
        findNavController().navigate(action)    }
}
