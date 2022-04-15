package com.johnmaricool.mario_designs.hilt

import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.Gson
import com.johnmaricool.mario_designs.androidcomponents.ApplicationConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object ApplicationModule {

    @Singleton
    @Provides
    fun getApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }


    @QuizAdded
    @Singleton
    @Provides
    fun getQuizAddedPrefs(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences("isQuizQuestionsGotten", Context.MODE_PRIVATE)
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
    fun provideAdRequest(): AdRequest {
        return AdRequest.Builder().build()
      //  return AdRequest.Builder().build()
    }

    @Singleton
    @Provides
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson{
        return Gson()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Fav

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FactFav

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GetData

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QuizAdded
