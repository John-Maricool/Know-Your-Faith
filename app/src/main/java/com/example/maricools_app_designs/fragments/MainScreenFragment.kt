package com.example.maricools_app_designs.fragments

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.maricools_app_designs.MainActivity
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.`interface`.onItemClickListener
import com.example.maricools_app_designs.adapters.MainScreenRecyclerAdapter
import com.example.maricools_app_designs.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment(R.layout.fragment_main_screen), onItemClickListener {

    private var _binding:  FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private val NAVIGATE_FACTS = 1
    private val NAVIGATE_QUIZ = 3
    private val NAVIGATE_SAINTS = 2
    private val NAVIGATE_PRAYERSS = 0

    private lateinit var adapter:MainScreenRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMainScreenBinding.bind(view)
       adapter = MainScreenRecyclerAdapter(activity)
        val gridColumnCount: Int = resources.getInteger(R.integer.grid_column_count)


        binding.apply {
                recyclerView.layoutManager = GridLayoutManager(activity, gridColumnCount)
                recyclerView.setHasFixedSize(true)
            ViewCompat.setNestedScrollingEnabled(recyclerView, false)
            recyclerView.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(position: Int) {
        val options = NavOptions.Builder()
        options.setEnterAnim(R.anim.slide_in_right)
        options.setExitAnim(R.anim.slide_out_left)
        options.setPopEnterAnim(R.anim.slide_in_left)
        options.setPopExitAnim(R.anim.slide_out_right)
        when(position){
            NAVIGATE_PRAYERSS -> findNavController().navigate(R.id.catholicPrayerFragment, null, options.build())
            NAVIGATE_FACTS -> findNavController().navigate(R.id.catholicFactsFragment, null, options.build())
            NAVIGATE_SAINTS -> findNavController().navigate(R.id.catholicSaintsFragment, null, options.build())
            NAVIGATE_QUIZ -> findNavController().navigate(R.id.catholicQuizFragment, null, options.build())
        }
    }
}