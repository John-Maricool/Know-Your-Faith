package com.example.maricools_app_designs.ui.mainscreen

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.*
import com.example.maricools_app_designs.QuizMapper
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants.Companion.NAVIGATEFACTS
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants.Companion.NAVIGATEPRAYERS
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants.Companion.NAVIGATEQUIZ
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.WorkerClass
import com.example.maricools_app_designs.interfaces_kids.OnItemClickListener
import com.example.maricools_app_designs.adapters.MainScreenRecyclerAdapter
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants.Companion.NAVIGATEORDEROFMASS
import com.example.maricools_app_designs.androidcomponents.MainActivity
import com.example.maricools_app_designs.database.QuizDao
import com.example.maricools_app_designs.databinding.FragmentMainScreenBinding
import com.example.maricools_app_designs.hilt.GetData
import com.example.maricools_app_designs.hilt.QuizAdded
import com.example.maricools_app_designs.utils.models.QuizEntityModel
import com.example.maricools_app_designs.utils.models.QuizModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.nio.file.FileAlreadyExistsException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainScreenFragment : Fragment(R.layout.fragment_main_screen), OnItemClickListener{

    private var _binding:  FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var work: WorkManager

    @Inject
    @QuizAdded
    lateinit var quizAdded: SharedPreferences

    @Inject
    lateinit var quizDow: QuizDao

    @Inject
    lateinit var cloud: FirebaseFirestore

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var adapter: MainScreenRecyclerAdapter

    lateinit var progressDialog: ProgressDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainScreenBinding.bind(view)
        val gridColumnCount: Int = resources.getInteger(R.integer.grid_column_count)
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(activity, gridColumnCount)
            recyclerView.setHasFixedSize(true)
            ViewCompat.setNestedScrollingEnabled(recyclerView, false)
            recyclerView.adapter = adapter
            setBackPressed()
        }
       // getProgressDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun setBackPressed(){
        var backPressedTime:Long = 0
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val backToast: Toast = Toast.makeText(context, "Press back again exit.", Toast.LENGTH_LONG)
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    backToast.cancel()
                    activity!!.finish()
                    return
                } else {
                    backToast.show()
                }
                backPressedTime = System.currentTimeMillis()
            }
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(position: Int) {
            when (position) {
                NAVIGATEPRAYERS -> findNavController().navigate(R.id.action_mainScreenFragment_to_catholicPrayerFragment3)
                NAVIGATEFACTS -> findNavController().navigate(R.id.action_mainScreenFragment_to_catholicFactsFragment2)
                NAVIGATEQUIZ ->
                   // if (quizAdded.contains("dataGotten")){
                        findNavController().navigate(R.id.action_mainScreenFragment_to_catholicQuizFragment)
                    /*}else{
                        onLoading()
                        startWork()
                    }*/
               NAVIGATEORDEROFMASS -> findNavController().navigate(R.id.action_mainScreenFragment_to_orderOfMassFragment)
            }
    }


/*
    private fun getProgressDialog(){
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Fetching Quiz questions...") // Setting Message
        progressDialog.setTitle("Quiz") // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(true)// Progress Dialog Style Spinner
        // / Display Progress Dialog\
    }

    private fun startWork() {
        val provideConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val provideWorkRequest: OneTimeWorkRequest =
                OneTimeWorkRequest.Builder(WorkerClass::class.java)
                        .setConstraints(provideConstraints)
                        .setBackoffCriteria(
                                BackoffPolicy.LINEAR,
                                40,
                                TimeUnit.SECONDS
                        )
                        .build()

        work.enqueueUniqueWork("TAG", ExistingWorkPolicy.KEEP, provideWorkRequest)

        work.getWorkInfoByIdLiveData(provideWorkRequest.id)
                .observe(this, Observer {
                    Toast.makeText(activity, it.state.name, Toast.LENGTH_LONG).show()
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            quizAdded.edit().putBoolean("dataGotten", true).apply()
                            onLoaded()
                            ListenableWorker.Result.success()
                        }
                        WorkInfo.State.RUNNING -> {
                            onLoading()
                        }
                        WorkInfo.State.ENQUEUED -> {
                            onFailedLoading()
                            ListenableWorker.Result.retry()
                        }
                        WorkInfo.State.BLOCKED, WorkInfo.State.FAILED -> {
                            onFailedLoading()
                            ListenableWorker.Result.failure()
                        }
                    }
                })
        }

    private fun onLoading(){
        progressDialog.show()
    }

    private fun onLoaded(){
        progressDialog.cancel()
        progressDialog.dismiss()
    }

    private fun onFailedLoading(){
        val snack = Snackbar.make(binding.recyclerView, "No Internet Connection", Snackbar.LENGTH_SHORT)
        snack.show()
        progressDialog.cancel()
    }
*/


}