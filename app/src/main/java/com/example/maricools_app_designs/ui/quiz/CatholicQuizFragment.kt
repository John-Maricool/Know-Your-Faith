package com.example.maricools_app_designs.ui.quiz

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants.Companion.points
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.hilt.Reward
import com.example.maricools_app_designs.databinding.FragmentCatholicQuizBinding
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_catholic_prayer.*
import javax.inject.Inject

@AndroidEntryPoint
class CatholicQuizFragment : Fragment(R.layout.fragment_catholic_quiz) {

    var _binding: FragmentCatholicQuizBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var adRequest: AdRequest

    @Inject
    @Reward
    lateinit var reward: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatholicQuizBinding.bind(view)
        adView.loadAd(adRequest)
        CheckAdvancedButtonSettings()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListenersForButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setClickListenersForButton(){
        binding.apply {
            bible.setOnClickListener {
                val action = CatholicQuizFragmentDirections.actionCatholicQuizFragmentToFirstScreenFragment(bible.text.toString())
                findNavController().navigate(action)
            }
            catholicQuiz.setOnClickListener {
                val action = CatholicQuizFragmentDirections.actionCatholicQuizFragmentToFirstScreenFragment(catholicQuiz.text.toString())
                findNavController().navigate(action)
            }

            advanced.setOnClickListener {
                if (CheckAdvancedButtonSettings()){
                    val action = CatholicQuizFragmentDirections.actionCatholicQuizFragmentToFirstScreenFragment(advanced.text.toString())
                    findNavController().navigate(action)
                }
                else{
                    Toast.makeText(activity, "You dont have 100 points yet!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun CheckAdvancedButtonSettings(): Boolean {
        if (reward.getInt("points", points) < 200){
            binding.advanced.isEnabled = false
            binding.advanced.isClickable = false
            binding.advanced.setTextColor(resources.getColor(R.color.colorGrey, null))

            return false
        }
        else{
            binding.advanced.isEnabled = true
            binding.advanced.isClickable = true
            binding.advanced.setTextColor(resources.getColor(R.color.colorBlack, null))

            return true
        }
    }

}