package com.example.maricools_app_designs.ui.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants.Companion.NAVIGATEFACTS
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants.Companion.NAVIGATEPRAYERS
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants.Companion.NAVIGATEQUIZ
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.interfaces_kids.OnItemClickListener
import com.example.maricools_app_designs.adapters.MainScreenRecyclerAdapter
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants.Companion.NAVIGATEORDEROFMASS
import com.example.maricools_app_designs.databinding.FragmentMainScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainScreenFragment : Fragment(R.layout.fragment_main_screen), OnItemClickListener{

    private var _binding:  FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: MainScreenRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainScreenBinding.bind(view)
        val gridColumnCount: Int = resources.getInteger(R.integer.grid_column_count)
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(activity, gridColumnCount)
            recyclerView.setHasFixedSize(true)
            ViewCompat.setNestedScrollingEnabled(recyclerView, false)
            recyclerView.adapter = adapter
            setBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun setBackPressed(){
        var backPressedTime:Long = 0
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
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


    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        adapter.setOnItemClickListener(this)
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