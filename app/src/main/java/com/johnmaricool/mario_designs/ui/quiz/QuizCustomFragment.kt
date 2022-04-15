package com.johnmaricool.mario_designs.ui.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.androidcomponents.ApplicationConstants
import com.johnmaricool.mario_designs.androidcomponents.MainActivity
import com.johnmaricool.mario_designs.databinding.FragmentQuizCustomBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuizCustomFragment : Fragment(R.layout.fragment_quiz_custom),
        QuizCustomViewModel.onTimeClick {

    var _binding: FragmentQuizCustomBinding? = null
    private val binding get() = _binding!!

    private val model: QuizCustomViewModel by viewModels()

    var mInterestitial: InterstitialAd? = null

    @Inject
    lateinit var adRequest: AdRequest

    private val args: QuizCustomFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuizCustomBinding.bind(view)
        (activity as MainActivity).supportActionBar?.title = args.title
        setBackPressed()
        loadUI()
        loadAd()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        handleButtonClicks()
    }

    private fun loadAd() {
        InterstitialAd.load(
                requireContext(),
                ApplicationConstants.INTERSTITIAL_AD_UNIT_ONE,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(p0: InterstitialAd) {
                        mInterestitial = p0
                    }

                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        mInterestitial = null
                    }
                }
        )
    }

    private fun disableOptionsButtons() {
        binding.apply {
            op1?.isEnabled = false
            op2?.isEnabled = false
            op3?.isEnabled = false
        }
    }

    private fun checkIfCorrect(it: View?) {
        model.countDownTimer.cancel()
        val v = it as Button
        if (v.text.toString() == model.currentQuiz.correctOption) {
            v.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorWhite))
            v.background = ResourcesCompat.getDrawable(resources, R.drawable.quiz_correct, context?.theme)
            binding.showCorrection.visibility = View.VISIBLE
            binding.showCorrection.text = "CORRECT"
            // binding.showCorrection.setTextColor(resources.getColor(R.color.colorWhite, null))
            binding.nxt?.visibility = View.VISIBLE
            model.correctAnswers++
        } else {
            v.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorRed))
            v.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorWhite))
            v.background = ResourcesCompat.getDrawable(resources, R.drawable.quiz_wrong, context?.theme)
            binding.showCorrection.visibility = View.VISIBLE
            binding.showCorrection.text = "WRONG"
            // binding.showCorrection.setTextColor(resources.getColor(R.color.colorRed, null))
            binding.buttonCorrectAnswer.visibility = View.VISIBLE
        }
    }

    private fun loadUI() {
        enableButtons()
        if (model.questionCount == model.totalSize) {
            binding.nxt?.text = "FINISH"
        }
        loadQuestions(model.questionIndex)
    }

    private fun loadQuestions(id: Int) {
        model.isAnswered = false
        model.currentQuiz = model.downloadedQuestions[id]
        binding.apply {
            question.text = model.currentQuiz.question
            op1?.text = model.currentQuiz.firstOption
            op2?.text = model.currentQuiz.secondOption
            op3?.text = model.currentQuiz.thirdOption
            showQuestionDetails.text = "question ${model.questionCount} out of ${model.totalSize}"
            showScore.text = "Score: ${model.correctAnswers}"
            //start timer
        }
        startTimer()
    }

    private fun startTimer() {
        val timeToAnswer = model.currentQuiz.timer
        binding.timerText.text = timeToAnswer.toString()
        model.startTimer(this)
        model.countDownTimer.start()
    }

    private fun enableButtons() {
        binding.apply {
            op1?.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorBlue))
            op1?.background = ResourcesCompat.getDrawable(resources, R.drawable.quiz_options, context?.theme)
            op2?.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorBlue))
            op2?.background   = ResourcesCompat.getDrawable(resources, R.drawable.quiz_options, context?.theme)
            op3?.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorBlue))
            op3?.background = ResourcesCompat.getDrawable(resources, R.drawable.quiz_options, context?.theme)
            binding.timerText.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
            op1?.isEnabled = true
            op2?.isEnabled = true
            op3?.isEnabled = true
            buttonCorrectAnswer.visibility = View.GONE
            nxt?.visibility = View.GONE
            showCorrection.visibility = View.GONE
        }
    }

    override fun onTickSelected(p0: Long, ttA: Long) {
        //update time
        val time = (p0 / 1000)
        if (time < 10) {
            binding.timerText.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorRed))
        }
        binding.timerText.text = (p0 / 1000).toString()
        val percent = p0 / (ttA * 10)
        binding.progress.progress = percent.toInt()
    }

    override fun onFinishSelected() {
        if (!model.isAnswered) {
            disableOptionsButtons()
            binding.showCorrection.visibility = View.VISIBLE
            binding.showCorrection.text = "No option selected"
            binding.buttonCorrectAnswer.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        model.countDownTimer.cancel()
    }

    override fun onResume() {
        super.onResume()
        model.countDownTimer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val dialog = context?.let { AlertDialog.Builder(it) }
                dialog?.setTitle("Exit Quiz")
                dialog?.setCancelable(true)
                dialog?.setMessage("Are you sure you want to exit Quiz?")
                dialog?.setPositiveButton("Yes") { _, _ ->
                    NavHostFragment.findNavController(this@QuizCustomFragment).navigateUp()
                }
                dialog?.setNegativeButton("No") { theDialog, _ ->
                    theDialog.cancel()
                }
                val alert = dialog?.create()
                alert?.show()
            }
        })
    }

    private fun handleButtonClicks() {
        binding.buttonCorrectAnswer.setOnClickListener {
            binding.showCorrection.text = "The correct answer is: ${model.currentQuiz.correctOption}"
            it.visibility = View.GONE
            binding.nxt?.visibility = View.VISIBLE
            model.countDownTimer.cancel()
        }

        binding.nxt?.setOnClickListener {
            if (model.questionCount == model.totalSize) {
                mInterestitial?.show(requireActivity())
                val wrongOnes = model.totalSize - model.correctAnswers
                val action = QuizCustomFragmentDirections.actionQuizCustomFragmentToQuizResultFragment(model.correctAnswers, wrongOnes)
                findNavController().navigate(action)
            } else {
                model.questionCount++
                model.questionIndex++
                loadUI()
            }
        }
        binding.op1?.setOnClickListener {
            model.isAnswered = true
            checkIfCorrect(it)
            disableOptionsButtons()
        }
        binding.op2?.setOnClickListener {
            model.isAnswered = true
            checkIfCorrect(it)
            disableOptionsButtons()
        }
        binding.op3?.setOnClickListener {
            model.isAnswered = true
            checkIfCorrect(it)
            disableOptionsButtons()
        }
    }

}