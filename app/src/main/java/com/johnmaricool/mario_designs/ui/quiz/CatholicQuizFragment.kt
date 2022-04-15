package com.johnmaricool.mario_designs.ui.quiz

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.androidcomponents.MainActivity
import com.johnmaricool.mario_designs.databinding.FragmentCatholicQuizBinding
import com.johnmaricool.mario_designs.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CatholicQuizFragment : androidx.fragment.app.Fragment(R.layout.fragment_catholic_quiz) {

    var _binding: FragmentCatholicQuizBinding? = null
    val binding get() = _binding!!

    private val model: CatholicQuizViewModel by viewModels()

    lateinit var quizPart: String

    @Inject
    lateinit var adRequest: AdRequest

    var item: Int = 10

    lateinit var anim: Animation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatholicQuizBinding.bind(view)
        binding.progress.visibility = View.GONE
        (activity as MainActivity).toolbar.visibility = View.VISIBLE
        anim = AnimationUtils.loadAnimation(activity, R.anim.bounce)
        setClickListenersForButton()
    }

    override fun onStart() {
        super.onStart()
        binding.adV.loadAd(adRequest)
    }

    private fun setClickListenersForButton() {
        binding.startQuiz.setOnClickListener {
            it.startAnimation(anim)
            if (binding.bibleButton.isChecked || binding.catholicFaithButton.isChecked) {
                item = binding.spinnerNumberOfQuestions.selectedItem.toString().toInt()
                val checkedItem = binding.radioGroup.checkedRadioButtonId
                val radioButton: RadioButton? = activity?.findViewById(checkedItem)
                 quizPart = radioButton?.text.toString()
                model.getAnsweredList(item, quizPart)
                observeData()
            }   else{
                Toast.makeText(activity, "Select which quiz part you want to answer", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun observeData() {
        model.data.observe(viewLifecycleOwner){ result ->
            when (result) {
                is Result.isLoading -> {
                    binding.progress.visibility = View.VISIBLE
                }

                is Result.isLoaded -> {
                    binding.progress.visibility = View.GONE
                    CatholicQuizViewModel.QuizQuestionsToAnswer = result.item
                    val action = CatholicQuizFragmentDirections.actionCatholicQuizFragmentToQuizCustomFragment(quizPart)
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}