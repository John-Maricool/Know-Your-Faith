package com.example.maricools_app_designs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.databinding.FragmentPrayerBinding
import com.example.maricools_app_designs.viewModels.PrayerViewModel
import kotlinx.android.synthetic.main.fragment_first_screen.view.*

class PrayerFragment : Fragment(R.layout.fragment_prayer) {

    private var _binding:  FragmentPrayerBinding? = null
    private val binding get() = _binding!!

    private val args: PrayerFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPrayerBinding.bind(view)

        //binding.text.text = args.id.toString()
        val model = ViewModelProvider(this).get(PrayerViewModel::class.java)

        binding.viewPager.adapter = model.adapter
        binding.viewPager.setCurrentItem(args.id, false)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}