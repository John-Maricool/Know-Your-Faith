package com.johnmaricool.mario_designs.ui.mainscreen

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.adapters.MainScreenRecyclerAdapter
import com.johnmaricool.mario_designs.androidcomponents.ApplicationConstants.Companion.NAVIGATEFACTS
import com.johnmaricool.mario_designs.androidcomponents.ApplicationConstants.Companion.NAVIGATEORDEROFMASS
import com.johnmaricool.mario_designs.androidcomponents.ApplicationConstants.Companion.NAVIGATEPRAYERS
import com.johnmaricool.mario_designs.androidcomponents.ApplicationConstants.Companion.NAVIGATEQUIZ
import com.johnmaricool.mario_designs.databinding.FragmentMainScreenBinding
import com.johnmaricool.mario_designs.hilt.QuizAdded
import com.johnmaricool.mario_designs.interfaces_kids.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainScreenFragment : Fragment(R.layout.fragment_main_screen), OnItemClickListener {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    @Inject
    @QuizAdded
    lateinit var quizAdded: SharedPreferences

    @Inject
    lateinit var adapter: MainScreenRecyclerAdapter

    @Inject
    lateinit var request: AdRequest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainScreenBinding.bind(view)
        setBackPressed()
        val gridColumnCount: Int = resources.getInteger(R.integer.grid_column_count)
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(activity, gridColumnCount)
            recyclerView.setHasFixedSize(true)
            ViewCompat.setNestedScrollingEnabled(recyclerView, false)
            recyclerView.adapter = adapter
        }
    }

    override fun onStart() {
        super.onStart()
        binding.adV?.loadAd(request)
        adapter.setOnItemClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setBackPressed() {
        var backPressedTime: Long = 0
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val backToast: Toast = Toast.makeText(context, "Press back again exit.", Toast.LENGTH_LONG)
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    backToast.cancel()
                    activity!!.finish()
                    return
                } else {
                    backToast.show()
                }
                backPressedTime = System.currentTimeMillis()
            }
        })
    }

    override fun onItemClick(position: Int) {
        when (position) {
            NAVIGATEPRAYERS -> findNavController().navigate(R.id.action_mainScreenFragment_to_catholicPrayerFragment3)
            NAVIGATEFACTS -> findNavController().navigate(R.id.action_mainScreenFragment_to_catholicFactsFragment2)
            NAVIGATEQUIZ -> findNavController().navigate(R.id.action_mainScreenFragment_to_catholicQuizFragment)
            NAVIGATEORDEROFMASS -> findNavController().navigate(R.id.action_mainScreenFragment_to_orderOfMassFragment)
        }
    }

}