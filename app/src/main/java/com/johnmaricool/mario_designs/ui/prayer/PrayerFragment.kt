package com.johnmaricool.mario_designs.ui.prayer

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.adapters.PrayerViewPagerAdapter
import com.johnmaricool.mario_designs.databinding.FragmentPrayerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PrayerFragment : Fragment(R.layout.fragment_prayer) {

    private var _binding: FragmentPrayerBinding? = null
    private val binding get() = _binding!!
    private val model: PrayerViewModel by viewModels()
    private val args: PrayerFragmentArgs by navArgs()

    @Inject
    lateinit var adRequest: AdRequest

    lateinit var adapter: PrayerViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPrayerBinding.bind(view)
        binding.adV.loadAd(adRequest)
        val data = model.getData()
        binding.viewPager.adapter = data

        binding.viewPager.setCurrentItem(args.id, false)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}