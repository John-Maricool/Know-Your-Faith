package com.johnmaricool.mario_designs.androidcomponents

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.hilt.GetData
import com.johnmaricool.mario_designs.models.Book
import com.johnmaricool.mario_designs.utils.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    lateinit var p_bar: ProgressBar

    private val model: SplashScreenViewModel by viewModels()

    private val model2: SetupViewModel by viewModels()

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
                    validateDatabase()
                }
                else{
                    p_bar.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun validateDatabase() {
        model2.validateTableData()?.observe(this) { value ->
            if (value == 4) {
                updateCacheRepository()
                return@observe
            }
            if (SetupViewModel.workerUuid != null) {
                return@observe
            }
            val wManager = WorkManager.getInstance(this)
            val uuid: UUID = model2.setupDatabase(wManager)
            model2.setWorkerUuid(uuid)
            monitorDatabaseSetup()
        }
    }

    private fun updateCacheRepository() {
        if (!model2.isCacheUpdated) {
            model2.allBooks.observe(this) { list ->
                if (list == null || list.isEmpty() || list.size != Book.MAX_BOOKS) {

                } else {
                    val bookList: ArrayList<Book> = ArrayList<Book>(list.size)
                    for (book in list) {
                        bookList.add(Book(book))
                    }
                    model2.updateCacheBooks(bookList)
                    p_bar.visibility = View.GONE
                    dataGotten.edit().putBoolean("added", true).apply()
                    goToNextActivity()
                }
            }
        } else {
            goToNextActivity()
        }
    }

    private fun monitorDatabaseSetup() {
        val uuid: UUID = SetupViewModel.workerUuid!!
        WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(uuid)
                .observe(this) { info: WorkInfo ->
                    when (info.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            updateCacheRepository()
                        }

                        WorkInfo.State.CANCELLED, WorkInfo.State.FAILED -> {

                        }

                        WorkInfo.State.BLOCKED, WorkInfo.State.ENQUEUED ->{

                        }
                        WorkInfo.State.RUNNING -> {
                            p_bar.visibility = View.VISIBLE
                        }
                        else -> {}
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




