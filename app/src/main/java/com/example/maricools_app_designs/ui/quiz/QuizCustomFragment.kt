package com.example.maricools_app_designs.ui.quiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.maricools_app_designs.androidcomponents.MainActivity
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.databinding.FragmentQuizCustomBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@Suppress("DEPRECATION")
class QuizCustomFragment : Fragment(R.layout.fragment_quiz_custom),
        QuizCustomViewModel.onTimeClick {

    var _binding: FragmentQuizCustomBinding? = null
    private val binding get() = _binding!!

    private val model: QuizCustomViewModel by viewModels()

    @Inject
    lateinit var mInterestitial: InterstitialAd

    @Inject
    lateinit var adRequest: AdRequest

    private val args: QuizCustomFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuizCustomBinding.bind(view)
        setBackPressed()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
       handleButtonClicks()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadUI()
    }

    private fun disableOptionsButtons() {
        binding.apply {
            option1.isEnabled = false
            option2.isEnabled = false
            option3.isEnabled = false
        }
    }

    private fun checkIfCorrect(it: View?) {
        model.countDownTimer.cancel()
        val v = it as Button
        if (v.text.toString() == model.currentQuiz.correctOption){
            v.setTextColor(resources.getColor(R.color.colorWhite, null))
            v.background = resources.getDrawable(R.drawable.quiz_correct, null)
            binding.showCorrection.visibility = View.VISIBLE
            binding.showCorrection.text = "CORRECT"
            binding.showCorrection.setTextColor(resources.getColor(R.color.colorWhite, null))
            binding.next.visibility = View.VISIBLE
            model.correctAnswers++
        }else{
            v.setBackgroundColor(resources.getColor(R.color.colorRed, null))
            v.setTextColor(resources.getColor(R.color.colorGrey, null))
            v.background = resources.getDrawable(R.drawable.quiz_wrong, null)
            binding.showCorrection.visibility = View.VISIBLE
            binding.showCorrection.text = "WRONG"
            binding.showCorrection.setTextColor(resources.getColor(R.color.colorRed, null))
            binding.buttonCorrectAnswer.visibility = View.VISIBLE
        }
    }

    private fun loadUI() {
        enableButtons()
        if (model.questionCount == args.item) {
            binding.next.text = "FINISH"
        }
        loadQuestions(model.questionIndex)
    }

    private fun loadQuestions(id: Int) {
        model.isAnswered = false
        model.currentQuiz = model.downloadedQuestions[id]
        binding.question.text = model.currentQuiz.question
        binding.option1.text = model.currentQuiz.firstOption
        binding.option2.text = model.currentQuiz.secondOption
        binding.option3.text = model.currentQuiz.thirdOption
        binding.showQuestionDetails.text = "question ${model.questionCount} out of ${args.item}"
        binding.showScore.text = "Score: ${model.correctAnswers}"
        //start timer
        startTimer()
    }

    private fun startTimer() {
        val timeToAnswer = model.currentQuiz.timer
        binding.timerText.text  = timeToAnswer.toString()
        model.startTimer(this)
        model.countDownTimer.start()
    }

    private fun enableButtons(){
        binding.apply{
            option1.setTextColor(resources.getColor(R.color.colorWhite, null))
            option1.background = resources.getDrawable(R.drawable.quiz_options, null)
            option2.setTextColor(resources.getColor(R.color.colorWhite, null))
            option2.background = resources.getDrawable(R.drawable.quiz_options, null)
            option3.setTextColor(resources.getColor(R.color.colorWhite, null))
            option3.background = resources.getDrawable(R.drawable.quiz_options, null)
            option1.isEnabled = true
            option2.isEnabled = true
            option3.isEnabled = true
            buttonCorrectAnswer.visibility = View.GONE
            next.visibility = View.GONE
            showCorrection.visibility = View.GONE
        }
    }

    override fun onTickSelected(p0: Long, ttA: Long) {
        //update time
        binding.timerText.text = (p0/1000).toString()
        val percent = p0/(ttA*10)
        binding.progress.progress = percent.toInt()
    }

    override fun onFinishSelected() {
        if (!model.isAnswered){
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

    private fun setBackPressed(){
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val dialog = context?.let { AlertDialog.Builder(it) }
                dialog?.setTitle("Exit Quiz")
                dialog?.setCancelable(true)
                dialog?.setMessage("Are you sure you want to exit Quiz?")
                dialog?.setPositiveButton("Yes"){_,_ ->
                    NavHostFragment.findNavController(this@QuizCustomFragment).navigateUp()
                }
                dialog?.setNegativeButton("No"){theDialog,_ ->
                    theDialog.cancel()
                }
                val alert = dialog?.create()
                alert?.show()
            }
        })

    }

    private fun handleButtonClicks(){
        binding.buttonCorrectAnswer.setOnClickListener {
            binding.showCorrection.text = "The correct answer is: ${model.currentQuiz.correctOption}"
            it.visibility = View.GONE
            binding.next.visibility = View.VISIBLE
            model.countDownTimer.cancel()
        }

        binding.next.setOnClickListener {
            if (model.questionCount == args.item){
                if (mInterestitial.isLoaded){
                    mInterestitial.show()
                }
                val wrongOnes = args.item - model.correctAnswers
                val action = QuizCustomFragmentDirections.actionQuizCustomFragmentToQuizResultFragment(model.correctAnswers, wrongOnes)
                findNavController().navigate(action)
            }else{
                model.questionCount++
                model.questionIndex++
                loadUI()
            }
            Log.d("BUTTON",  model.questionCount.toString() + ":" + model.questionIndex.toString())
        }
        binding.option1.setOnClickListener {
            model.isAnswered = true
            checkIfCorrect(it)
            disableOptionsButtons()
        }
        binding.option2.setOnClickListener {
            model.isAnswered = true
            checkIfCorrect(it)
            disableOptionsButtons()
        }
        binding.option3.setOnClickListener {
            model.isAnswered = true
            checkIfCorrect(it)
            disableOptionsButtons()
        }
    }

}