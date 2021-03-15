package com.example.maricools_app_designs.ui.fact

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.databinding.FactsFragmentBinding
import com.example.maricools_app_designs.utils.models.FactModel
import com.example.maricools_app_designs.utils.models.FactsFavModel
import com.google.android.gms.ads.AdRequest
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
        binding.adView.loadAd(adRequest)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fact = viewModel.getParticularFact(args.id)

        binding.apply {
            factName.text = fact.factTitle
            factBody.text = fact.factContent
        }
     }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fav, menu)
        val imageItem: MenuItem = menu.findItem(R.id.fav)
        val imageView: ImageView = imageItem.actionView as ImageView

        if (viewModel.checkIfAddedToSharedPrefs(fact.uid!!)) {
            imageView.setBackgroundResource(R.drawable.ic_red_fav)
        } else if (!viewModel.checkIfAddedToSharedPrefs(fact.uid!!)) {
            imageView.setBackgroundResource(R.drawable.ic_grey_fav)
        }

        imageView.setOnClickListener{
            val id = fact.uid
            val title = fact.factTitle
            val content = fact.factContent
            val favFact = FactsFavModel(id!!, title, content)
            if (viewModel.checkIfAddedToSharedPrefs(id)) {
                viewModel.removeFromSharedPrefs(id, favFact)
                it.setBackgroundResource(R.drawable.ic_grey_fav)
            } else if (!viewModel.checkIfAddedToSharedPrefs(id)) {
                viewModel.addToSharedPrefs(id, favFact)
                it.setBackgroundResource(R.drawable.ic_red_fav)
            }
        }
    }
}