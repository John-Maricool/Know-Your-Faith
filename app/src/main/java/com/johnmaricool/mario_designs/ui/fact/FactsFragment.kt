package com.johnmaricool.mario_designs.ui.fact

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.androidcomponents.MainActivity
import com.johnmaricool.mario_designs.databinding.FactsFragmentBinding
import com.johnmaricool.mario_designs.utils.models.FactModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FactsFragment : Fragment(R.layout.facts_fragment) {

    private var _binding: FactsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FactsViewModel by viewModels()
    val args: FactsFragmentArgs by navArgs()

    lateinit var fact: FactModel

    @Inject
    lateinit var adRequest: AdRequest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FactsFragmentBinding.bind(view)
        binding.adView.adView.loadAd(adRequest)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fact = viewModel.getParticularFact(args.id)
        binding.apply {
            factName.text = fact.factTitle
            val text =  fact.factContent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                factBody.text = Html.fromHtml(text, 0)
            }
        }
        (activity as MainActivity).supportActionBar?.title = fact.factTitle
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}