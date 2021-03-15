package com.example.maricools_app_designs.androidcomponents

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.WorkerClass
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

     private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var prefs: SharedPreferences
     var prefsName = "MyPrefs"
    var isNMode = "isNightMode"
    lateinit var switchCompat: SwitchCompat

    @Inject
    lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setAlarmManager()
        prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        setSupportActionBar(toolbar)
        nav_view.setupWithNavController(navController)

        val menu = nav_view.menu
        switchCompat = MenuItemCompat.getActionView(menu.findItem(R.id.night_mode)).findViewById(R.id.switch_id) as SwitchCompat

        menu.findItem(R.id.share).setOnMenuItemClickListener {
                shareToPeople()
        }
        menu.findItem(R.id.log_out).setOnMenuItemClickListener {
            logOut()
        }

        checkNightModeActivated()
        switchCompat.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                save(true)
                recreate()
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                save(false)
                recreate()
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)

        MobileAds.initialize(this@MainActivity)
    }

    private fun save(b: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(isNMode, b)
        editor.apply()
    }

    private fun setAlarmManager() {
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(this, BroadReceiver::class.java)
        intent.action = MyApplication.receiverFilter
        val pendingIntent = PendingIntent.getBroadcast(this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager?.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime()+ AlarmManager.INTERVAL_HALF_DAY,
                AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun checkNightModeActivated(){
        if(prefs.getBoolean(isNMode, false)){
            switchCompat.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            switchCompat.isChecked = false
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun shareToPeople(): Boolean {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Select Option"))

        return true
    }

    private fun logOut(): Boolean {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Log Out")
        dialog.setCancelable(false)
        dialog.setMessage("Are you sure you want to exit here?")
        dialog.setPositiveButton("Yes"){_,_ ->
            finish()
        }
        dialog.setNegativeButton("No"){theDialog,_ ->
            theDialog.cancel()
        }
        val alert = dialog.create()
        alert.show()
        return true
    }



}