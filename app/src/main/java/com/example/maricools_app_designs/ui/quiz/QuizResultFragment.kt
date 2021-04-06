package com.example.maricools_app_designs.ui.quiz

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.hilt.Reward
import com.example.maricools_app_designs.databinding.FragmentQuizResultBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import java.lang.System.load
import javax.inject.Inject

@AndroidEntryPoint
class QuizResultFragment : Fragment(R.layout.fragment_quiz_result) {

    var _binding: FragmentQuizResultBinding? = null

    @Inject
    lateinit var adRequest: AdRequest

    @Inject
    lateinit var mRewardVideoAd: RewardedAd

    @Inject
    @Reward
    lateinit var rewardPrefs: SharedPreferences

    val binding get() = _binding!!

    private val model: QuizResultViewModel by viewModels()

    private val args: QuizResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuizResultBinding.bind(view)
        arrangeResults()
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

    private fun arrangeResults() {
        val percent = model.arrangeViewsResult(args.score, args.wrong)
        when {
            percent < 50 -> {
                binding.percentage.setTextColor(context?.resources!!.getColor(R.color.colorRed, null))
                binding.progress.progressDrawable = context?.resources!!.getDrawable(R.drawable.circular_progress_big_low, null)
            }
            percent > 50 -> {
                binding.percentage.setTextColor(context?.resources!!.getColor(R.color.colorGreen, null))
                binding.progress.progressDrawable = context?.resources!!.getDrawable(R.drawable.circular_progress_big, null)
            }
            else -> {
                binding.percentage.setTextColor(context?.resources!!.getColor(R.color.colorWhite, null))
                binding.progress.progressDrawable = context?.resources!!.getDrawable(R.drawable.circular_progress_big_center, null)
            }
        }
        binding.progress.progress = percent
        binding.percentage.text = "$percent%"
        binding.correctOnes.text = "Correct Answers: ${args.score}"
        binding.wrongOnes.text = "Wrong Answers: ${args.wrong}"
        val point = model.updateNumberOfPoints()
        if (point < 100) {
            binding.noOfPoints.text = "You have $point points"
        } else {
            binding.noOfPoints.text = getString(R.string.show_that_advanced_quiz_is_unlocked)
        }
    }

    private fun setRewardVideoAd() {
        val callback = object: RewardedAdLoadCallback(){
            override fun onRewardedAdLoaded() {
            }
            override fun onRewardedAdFailedToLoad(p0: LoadAdError?) {
                Log.d("ad", "Failed to Load")
            }
        }
        mRewardVideoAd.loadAd(adRequest, callback)
    }

    private fun loadAd(){
        if (mRewardVideoAd.isLoaded) {
            val addCallback = object : RewardedAdCallback() {
                override fun onUserEarnedReward(p0: RewardItem) {
                    model.saveToSP(p0.amount)
                }
                override fun onRewardedAdClosed() {
                    super.onRewardedAdClosed()
                    arrangeResults()
                }
            }
            mRewardVideoAd.show(activity, addCallback)
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            Log.d("ad", "Failed to Load")
        }
    }
}