package com.example.maricools_app_designs.ui.initialscreen

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.WorkerClass
import com.example.maricools_app_designs.databinding.FragmentInitialScreenBinding
import com.example.maricools_app_designs.hilt.GetData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InitialScreen : Fragment(R.layout.fragment_initial_screen) {

    private var _binding: FragmentInitialScreenBinding? = null
    private val binding get() = _binding!!

    @Inject
    @GetData
    lateinit var dataGotten: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentInitialScreenBinding.bind(view)

        if (dataGotten.getBoolean("added", true)) {
            onLoaded()
        } else {
          startWork()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onLoading(){
        binding.pBar.visibility = View.VISIBLE
    }

    private fun onLoaded(){
        binding.pBar.visibility = View.GONE
        dataGotten.edit().putBoolean("added", true).apply()
        val action = InitialScreenDirections.actionInitialScreenToMainScreenFragment()
        findNavController().navigate(action)
    }

    private fun onFailedLoading(){
        binding.pBar.visibility = View.GONE
        val snack = Snackbar.make(binding.parent, "No Internet Connection", Snackbar.LENGTH_LONG)
        snack.show()
    }

    private fun startWork(){
        val provideConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val provideWorkRequest: OneTimeWorkRequest =
                OneTimeWorkRequest.Builder(WorkerClass::class.java)
                        .setConstraints(provideConstraints)
                        .build()

        val work = WorkManager.getInstance(requireContext())
        work.enqueue(provideWorkRequest)
        work.getWorkInfoByIdLiveData(provideWorkRequest.id)
                .observe(viewLifecycleOwner, Observer {
                    Toast.makeText(activity, it.state.name, Toast.LENGTH_LONG).show()
                    when (it.state) {
                        WorkInfo.State.RUNNING -> onLoading()
                        WorkInfo.State.ENQUEUED -> onFailedLoading()
                        WorkInfo.State.SUCCEEDED -> onLoaded()
                        else -> Toast.makeText(activity, it.runAttemptCount, Toast.LENGTH_LONG).show()
                    }
                })
    }
}

