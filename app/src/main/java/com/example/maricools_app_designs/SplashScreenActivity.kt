package com.example.maricools_app_designs

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.example.maricools_app_designs.androidcomponents.MainActivity
import com.example.maricools_app_designs.database.FactDao
import com.example.maricools_app_designs.database.PrayerDao
import com.example.maricools_app_designs.hilt.GetData
import com.example.maricools_app_designs.utils.models.FactModel
import com.example.maricools_app_designs.utils.models.FactServerModel
import com.example.maricools_app_designs.utils.models.PrayerModel
import com.example.maricools_app_designs.utils.models.PrayerServerModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    @GetData
    lateinit var dataGotten: SharedPreferences

    @Inject
    lateinit var prayerdao: PrayerDao

    @Inject
    lateinit var factdao: FactDao

    lateinit var gson: Gson
    private lateinit var mapper: CacheMapper
    private lateinit var factMapper: FactMapper

    val work by lazy {  WorkManager.getInstance(applicationContext)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        mapper = CacheMapper()
        gson = Gson()
        factMapper = FactMapper()
        if (dataGotten.contains("added")) {
            delayTime()
        } else {
            lifecycleScope.launch(IO){
                doAllWork()
                delay(2000)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun delayTime(){
        val handler = Handler()
        handler.postDelayed( {val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()}, 500)
    }


    override fun onDestroy() {
        super.onDestroy()
        work.cancelAllWork()
    }

    private fun getData(){
        val jsonFile = "MY FILES/PRAYERS.json".getJsonDataFromAsset(this)
        val listPrayer = object: TypeToken<List<PrayerServerModel>>(){}.type
        val prayers: List<PrayerServerModel> = gson.fromJson(jsonFile, listPrayer)
        val list = mapper.convertToCacheList(prayers)
        addToRoom(list)
    }

    private fun String.getJsonDataFromAsset(
            context: Context
    ): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(this).bufferedReader().use {
                it.readText()
            }
        }catch (e: IOException){
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun getFactData(){
        val jsonFile = "MY FILES/FACTS.json".getJsonDataFromAsset(this)
        val listFact = object: TypeToken<List<FactServerModel>>(){}.type
        val facts: List<FactServerModel> = gson.fromJson(jsonFile, listFact)
        val list = factMapper.convertToCacheList(facts)
        addToRoomFact(list)
    }

    private fun doAllWork(){
        getFactData()
        getData()
    }

    private fun addToRoom(prayer: List<PrayerModel>) {
        prayerdao.addPrayer(prayer)
    }

    private fun addToRoomFact(fact: List<FactModel>) {
        factdao.addFact(fact)
    }
}