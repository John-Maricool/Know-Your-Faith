package com.johnmaricool.mario_designs.ui.prayer

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.databinding.FragmentPrayerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PrayerFragment : Fragment(R.layout.fragment_prayer) {

    private var _binding:  FragmentPrayerBinding? = null
    private val binding get() = _binding!!
    private val model: PrayerViewModel by viewModels()
    private val args: PrayerFragmentArgs by navArgs()

    @Inject
    lateinit var adRequest: AdRequest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPrayerBinding.bind(view)

        binding.adView.adView.loadAd(adRequest)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewPager.adapter = model.adapter
        binding.viewPager.setCurrentItem(args.id, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}