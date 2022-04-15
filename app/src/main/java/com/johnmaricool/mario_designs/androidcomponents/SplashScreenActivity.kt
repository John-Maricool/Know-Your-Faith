package com.johnmaricool.mario_designs.androidcomponents

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.hilt.GetData
import com.johnmaricool.mario_designs.utils.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    lateinit var p_bar: ProgressBar

    private val model: SplashScreenViewModel by viewModels()

    @Inject
    @GetData
    lateinit var dataGotten: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        p_bar = findViewById(R.id.p_bar)

        if (dataGotten.contains("added")){
            delayTime()
        } else {
            val job = lifecycleScope.launch(Main){
               model.doAllWork()
            }

            lifecycleScope.launch(Main) {
                job.join()
                if (job.isCompleted){
                    p_bar.visibility = View.GONE
                    dataGotten.edit().putBoolean("added", true).apply()
                    goToNextActivity()
                }
                else{
                    p_bar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun goToNextActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun delayTime(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

  }




