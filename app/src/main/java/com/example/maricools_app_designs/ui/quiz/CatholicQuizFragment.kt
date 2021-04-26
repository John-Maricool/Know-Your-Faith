package com.example.maricools_app_designs.ui.quiz

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    lateinit var anim: Animation
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatholicQuizBinding.bind(view)
        anim = AnimationUtils.loadAnimation(activity, R.anim.bounce)
        binding.adView.adView.loadAd(adRequest)
        binding.notice.isSelected = true
        CheckAdvancedButtonSettings()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListenersForButton()
    }

    private fun setClickListenersForButton() {
        binding.startQuiz.setOnClickListener {
            it.startAnimation(anim)
            //check if at least one of the radio buttons are clicked
            if (binding.bibleButton.isChecked || binding.catholicFaithButton.isChecked) {
                //get the integer from the spinner
                item = binding.spinnerNumberOfQuestions.selectedItem.toString().toInt()
                //check the checked item id and get the text
                val checkedItem = binding.radioGroup.checkedRadioButtonId
                val radioButton: RadioButton? = activity?.findViewById(checkedItem)
                val quizPart = radioButton?.text.toString()
                model.getAnsweredList(item, quizPart)
                val action = CatholicQuizFragmentDirections.actionCatholicQuizFragmentToQuizCustomFragment(item, quizPart)
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
        return if (reward.getInt("points", 0) < 100){
            binding.catholicFaithButton.isEnabled = false
            binding.catholicFaithButton.isClickable = false

            false
        }
        else{
            binding.catholicFaithButton.isEnabled = true
            binding.catholicFaithButton.isClickable = true
            true
        }
    }
}