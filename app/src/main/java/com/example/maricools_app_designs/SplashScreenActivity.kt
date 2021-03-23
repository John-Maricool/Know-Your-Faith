package com.example.maricools_app_designs

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.maricools_app_designs.androidcomponents.MainActivity
import com.example.maricools_app_designs.hilt.GetData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash_screen.*
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    @GetData
    lateinit var dataGotten: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (dataGotten.contains("added")) {
            onLoaded()
            delayTime()
        } else {
            startWork()
        }
    }

    private fun onLoading(){
        p_bar.visibility = View.VISIBLE
    }

    private fun delayTime(){
        val handler = Handler()
        handler.postDelayed({val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        finish()}, 1000)
    }

    private fun onLoaded(){
        p_bar.visibility = View.GONE
    }

    private fun onFailedLoading(){
        p_bar.visibility = View.GONE
        val snack = Snackbar.make(p_bar, "No Internet Connection", Snackbar.LENGTH_LONG)
        snack.show()
    }

    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    private fun startWork(){
        val provideConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val provideWorkRequest: OneTimeWorkRequest =
                OneTimeWorkRequest.Builder(WorkerClass::class.java)
                        .setConstraints(provideConstraints)
                        .build()

        val work = WorkManager.getInstance(applicationContext)
        work.enqueue(provideWorkRequest)
        work.getWorkInfoByIdLiveData(provideWorkRequest.id)
                .observe(this, Observer {
                    Toast.makeText(this, it.state.name, Toast.LENGTH_LONG).show()
                    when (it.state) {
                        WorkInfo.State.RUNNING -> onLoading()
                        WorkInfo.State.ENQUEUED -> onFailedLoading()
                        WorkInfo.State.SUCCEEDED -> {
                            dataGotten.edit().putBoolean("added", true).apply()
                            onLoaded()
                            delayTime()
                        }
                        else -> Toast.makeText(this, it.runAttemptCount, Toast.LENGTH_LONG).show()
                    }
                })
    }

}