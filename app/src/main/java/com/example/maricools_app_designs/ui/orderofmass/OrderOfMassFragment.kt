package com.example.maricools_app_designs.ui.orderofmass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.databinding.FragmentOrderOfMassBinding
import com.example.maricools_app_designs.interfaces_kids.OnPrayerItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderOfMassFragment : Fragment(R.layout.fragment_order_of_mass), OnPrayerItemClickListener {

    var _binding: FragmentOrderOfMassBinding? = null
    val binding get() = _binding!!
    val model: OrderOfMassListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderOfMassBinding.bind(view)

        binding.orderRecyclerView.setHasFixedSize(true)
        binding.orderRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.orderRecyclerView.adapter = model.adapter
    }

    override fun onStart() {
        super.onStart()
        model.adapter.setOnItemClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPrayerItemClick(prayer: String, id: Int) {
        val action = OrderOfMassFragmentDirections.actionOrderOfMassFragmentToOOMFragment(prayer)
        findNavController().navigate(action)
    }
}
