package com.johnmaricool.mario_designs.ui.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.androidcomponents.MainActivity
import com.johnmaricool.mario_designs.databinding.FragmentQuizResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizResultFragment : Fragment(R.layout.fragment_quiz_result) {

    var _binding: FragmentQuizResultBinding? = null

    val binding get() = _binding!!

    private val model: QuizResultViewModel by viewModels()

    private val args: QuizResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuizResultBinding.bind(view)
        (activity as MainActivity).toolbar.visibility = View.GONE
        arrangeResults()
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun arrangeResults() {
        val percent = model.arrangeViewsResult(args.score, args.wrong)
        when {
           args.score < args.wrong -> {
               binding.apply {
                   state.text = getString(R.string.unlucky)
                   progress.progressDrawable = ResourcesCompat.getDrawable(resources, R.drawable.circular_progress_big_low, context?.theme)
               }
           }
            args.score > args.wrong -> {
                binding.apply {
                    state.text = getString(R.string.congrats)
                    progress.progressDrawable = ResourcesCompat.getDrawable(resources, R.drawable.circular_progress_big, context?.theme)
                }
            }
            else -> {
                binding.apply {
                    state.text = getString(R.string.average)
                    binding.progress.progressDrawable = ResourcesCompat.getDrawable(resources, R.drawable.circular_progress_big_center, context?.theme)
                }
            }
        }
        binding.progress.progress = percent
        binding.percentage.text = "$percent%"
        val total = args.score + args.wrong
        binding.wrongOnes.text = "You attempted $total questions and from that ${args.score} answers is correct."
    }
}