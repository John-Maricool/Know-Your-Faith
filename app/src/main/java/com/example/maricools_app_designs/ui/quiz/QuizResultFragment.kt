package com.example.maricools_app_designs.ui.quiz

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants.Companion.points
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.hilt.Reward
import com.example.maricools_app_designs.databinding.FragmentQuizResultBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuizResultFragment : Fragment(R.layout.fragment_quiz_result) {

    var _binding: FragmentQuizResultBinding? = null

    val binding get() = _binding!!

    @Inject
    lateinit var adRequest: AdRequest

    @Inject
     lateinit var mInterestitial: InterstitialAd

    @Inject
    @Reward
     lateinit var rewardPrefs: SharedPreferences

    @Inject
     lateinit var mRevardVideoAd: RewardedAd

    private val args: QuizResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuizResultBinding.bind(view)

        if(rewardPrefs.contains("points")){
            points = checkIfSaved()
        }
        arrangeViewsResult()
        backQuizListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRewardVideoAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun saveToSP(p: Int){
        val editor = rewardPrefs.edit()
        editor.putInt("points", points.plus(p))
        editor.apply()
    }

    private fun checkIfSaved(): Int{
        return rewardPrefs.getInt("points", points)
    }

    private fun setRewardVideoAd(){
        binding.watchVideo.setOnClickListener {
            mRevardVideoAd.loadAd(adRequest, RewardedAdLoadCallback())
            if (mRevardVideoAd.isLoaded){
                mRevardVideoAd.show(activity, object: RewardedAdCallback(){
                    override fun onUserEarnedReward(p0: RewardItem) {
                        val amount = p0.amount
                        saveToSP(amount)
                    }
                })
            }else{
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_LONG).show()
            }
            mRevardVideoAd.setOnPaidEventListener {
                binding.noOfPoints.text = "You have $points points"
            }
        }
    }

    private fun arrangeViewsResult(){
        binding.noOfPoints.text = "You have $points points"
        val totalQuestions = args.score + args.wrong
        val percentage= (args.score.times(100)).div(totalQuestions)
        if (percentage >= 90){
            saveToSP(10)
        }

        binding.progress.progress = percentage
        binding.percentage.text = "$percentage%"
        binding.correctOnes.text = "Correct: ${args.score}"
        binding.wrongOnes.text = "Wrong: ${args.wrong}"

    }

    private fun backQuizListener(){
        binding.backToQuiz.setOnClickListener {
            if (mInterestitial.isLoaded){
                mInterestitial.show()
            }
            val action = QuizResultFragmentDirections.actionQuizResultFragmentToCatholicQuizFragment2()
            findNavController().navigate(action)
        }
    }

}