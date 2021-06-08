package com.johnmaricool.mario_designs.ui.fact

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.androidcomponents.MainActivity
import com.johnmaricool.mario_designs.databinding.FactsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FactsFragment : Fragment(R.layout.facts_fragment) {

    private var _binding: FactsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FactsViewModel by viewModels()
    val args: FactsFragmentArgs by navArgs()

    @Inject
    lateinit var adRequest: AdRequest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FactsFragmentBinding.bind(view)
        viewModel.getFact(args.id)
        binding.adView.adView.loadAd(adRequest)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner, Observer {fact ->
            binding.apply {
                factName.text = fact.factTitle
                val text = fact.factContent
                factBody.text = HtmlCompat.fromHtml(text, 0)
                (activity as MainActivity).supportActionBar?.title = fact.factTitle
            }
            })
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}