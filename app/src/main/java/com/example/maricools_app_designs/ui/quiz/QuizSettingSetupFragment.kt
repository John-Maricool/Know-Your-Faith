package com.example.maricools_app_designs.ui.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.databinding.FragmentFirstScreenBinding
import com.example.maricools_app_designs.interfaces_kids.StateInterface
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("DEPRECATION")
class QuizSettingSetupFragment : Fragment(R.layout.fragment_first_screen), StateInterface {

    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!
    var item: Int = 10

    private val model: QuizSettingViewModel by viewModels()
    val args: QuizSettingSetupFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirstScreenBinding.bind(view)

         item = binding.spinner.selectedItem.toString().toInt()

        binding.progressBar.visibility = View.GONE

        binding.startQuiz.setOnClickListener {
            model.getAllQuestions(args.selection, item)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model.setStateInterfaceListener(this)
        model.clearAllLists()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showSnackBar(){
        val snack = Snackbar.make(binding.rel, "No Internet Connection", Snackbar.LENGTH_LONG)
        snack.setAction("Retry"){
            model.getAllQuestions(args.selection, item)
        }
        snack.show()
    }

    override fun isLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.loading.visibility = View.VISIBLE
    }

    override fun notLoaded() {
        showSnackBar()
        binding.progressBar.visibility = View.GONE
        binding.loading.visibility = View.GONE
    }

    override fun fullyLoaded() {
        binding.progressBar.visibility = View.GONE
        binding.loading.visibility = View.GONE
        val action = QuizSettingSetupFragmentDirections.actionFirstScreenFragmentToQuizCustomFragment(item)
        findNavController().navigate(action)
    }

}
