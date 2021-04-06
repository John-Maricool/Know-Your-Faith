package com.example.maricools_app_designs.hilt

import android.content.Context
import android.content.SharedPreferences
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import com.example.maricools_app_designs.WorkerClass
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)

object ApplicationModule {

    @Singleton
    @Provides
    fun getApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    @Reward
    @Singleton
    @Provides
    fun getRewardPreference(@ApplicationContext context: Context): SharedPreferences{
        val prefs =  context.getSharedPreferences(ApplicationConstants.rewardPrefsName, Context.MODE_PRIVATE)
        return prefs
    }

    @GetData
    @Singleton
    @Provides
    fun doWorkPreference(@ApplicationContext context: Context): SharedPreferences{
        val prefs =  context.getSharedPreferences("isDataGotten", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt("points", 0)
        editor.apply()

        return prefs
    }

    @Fav
    @Singleton
    @Provides
    fun getFavPreference(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences(ApplicationConstants.favoritePrefsName, Context.MODE_PRIVATE)
    }

    @FactFav
    @Singleton
    @Provides
    fun getFactFavPreference(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences(ApplicationConstants.favoritefactPrefsName, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideAdRequest(): AdRequest{
        return AdRequest.Builder().build()
    }

    @Singleton
    @Provides
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Singleton
    @Provides
    fun getFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun getFirebaseReference(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideRewardedAd(@ApplicationContext context: Context): RewardedAd{
        return RewardedAd(context, "ca-app-pub-3940256099942544/5224354917")
    }

    @Singleton
    @Provides
    fun provideInterstitialAd(@ApplicationContext context: Context, request: AdRequest): InterstitialAd{
       val ad = InterstitialAd(context)
        ad.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        ad.loadAd(request)
        return ad
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Reward

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Fav

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FactFav

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GetData
