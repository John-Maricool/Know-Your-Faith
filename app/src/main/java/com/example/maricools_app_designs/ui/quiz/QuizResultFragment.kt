package com.example.maricools_app_designs.ui.quiz

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants.Companion.points
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.hilt.Reward
import com.example.maricools_app_designs.databinding.FragmentQuizResultBinding
import com.example.maricools_app_designs.ui.prayer.PrayerListViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd.load
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd.load
import dagger.hilt.android.AndroidEntryPoint
import java.lang.System.load
import javax.inject.Inject

@AndroidEntryPoint
class QuizResultFragment : Fragment(R.layout.fragment_quiz_result) {

    var _binding: FragmentQuizResultBinding? = null
  @Inject lateinit var adRequest: AdRequest
    @Inject lateinit var mRewardVideoAd: RewardedAd
    val binding get() = _binding!!

    private val model: QuizResultViewModel by viewModels()

    private val args: QuizResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuizResultBinding.bind(view)
        arrangeResults()
        backQuizListener()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.watchVideoText.setOnClickListener {
            setRewardVideoAd()
            loadAd()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        arrangeResults()
    }

    private fun backQuizListener(){
        binding.backToQuiz.setOnClickListener {
            val action = QuizResultFragmentDirections.actionQuizResultFragmentToCatholicQuizFragment2()
            findNavController().navigate(action)
        }
    }

    private fun arrangeResults(){
        val percent = model.arrangeViewsResult(args.score, args.wrong)
        binding.progress.progress = percent
        binding.percentage.text = "$percent%"
        binding.correctOnes.text = "Correct: ${args.score}"
        binding.wrongOnes.text = "Wrong: ${args.wrong}"
        val point = model.updateNumberOfPoints()
            binding.noOfPoints.text = "You have $point points"
    }

    fun setRewardVideoAd() {
        val callback = object: RewardedAdLoadCallback(){
            override fun onRewardedAdLoaded() {
            }
            override fun onRewardedAdFailedToLoad(p0: LoadAdError?) {
                Log.d("ad", "Failed to Load")
            }
        }
        mRewardVideoAd.loadAd(adRequest, callback)
    }

    fun loadAd(){
        if (mRewardVideoAd.isLoaded) {
            val addCallback = object : RewardedAdCallback() {
                override fun onUserEarnedReward(p0: RewardItem) {
                    model.saveToSP(p0.amount)
                }
            }
            mRewardVideoAd.show(activity, addCallback)
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            Log.d("ad", "Failed to Load")
        }
    }
}