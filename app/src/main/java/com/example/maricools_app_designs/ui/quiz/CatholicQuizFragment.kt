package com.example.maricools_app_designs.ui.quiz

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.viewModels
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

    private val model: CatholicQuizViewModel by viewModels()
    @Inject
    lateinit var adRequest: AdRequest

    @Inject
    @Reward
    lateinit var reward: SharedPreferences

    var item: Int = 10

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

    private fun setClickListenersForButton() {
        binding.startQuiz.setOnClickListener {
            //check if at least one of the radio buttons are clicked
            if (binding.bibleButton.isChecked || binding.catholicFaithButton.isChecked || binding.advancedButton.isChecked) {
                //get the integer from the spinner
                item = binding.spinnerNumberOfQuestions.selectedItem.toString().toInt()
                //check the checked item id and get the text
                val checkedItem = binding.radioGroup.checkedRadioButtonId
                val radioButton: RadioButton? = activity?.findViewById(checkedItem)

                model.getAnsweredList(item, radioButton?.text.toString())
                val action = CatholicQuizFragmentDirections.actionCatholicQuizFragmentToQuizCustomFragment(item)
                findNavController().navigate(action)
            }   else{
                Toast.makeText(activity, "Select which quiz part you want to answer", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun CheckAdvancedButtonSettings(): Boolean {
        if (reward.getInt("points", points) < 100){
            binding.advancedButton.isEnabled = false
            binding.advancedButton.isClickable = false

            return false
        }
        else{
            showDialog()
            binding.advancedButton.isEnabled = true
            binding.advancedButton.isClickable = true

            return true
        }
    }

    private fun showDialog(){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Congratulations!!!")
        builder.setMessage("You have unlocked the advanced quiz")
        builder.show()
    }

}