package com.example.maricools_app_designs

import android.content.Context
import android.os.Build.VERSION_CODES.N
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.maricools_app_designs.database.CacheDatabase
import com.example.maricools_app_designs.utils.models.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class WorkerClass
@Inject
constructor(@ApplicationContext var context: Context,
            params: WorkerParameters): Worker(context, params) {

     private var mapper: CacheMapper = CacheMapper()
    private var factMapper: FactMapper = FactMapper()
    private var quizMapper: QuizMapper = QuizMapper()
    private var oomMapper: OOMMapper = OOMMapper()

     private var database = CacheDatabase.getDatabase(context)
    private val cloud = FirebaseFirestore.getInstance()
   private val prayerdao = database.prayerDao()
    private val factdao = database.factDao()
    private val oomDao = database.oomDao()
    private val quizDow = database.quiDao()
    private val latch = CountDownLatch(N)

    override fun doWork(): Result {
        return try {
               doAllWork()
            latch.await()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun addToRoom(prayer: List<PrayerModel>) {
        prayerdao.addPrayer(prayer)
    }

    private fun addOomToRoom(oom: List<OOMModel>) {
        oomDao.addOOM(oom)
    }

    private fun addToRoomFact(fact: List<FactModel>) {
        factdao.addFact(fact)
    }

    private fun addToRoomQuiz(quiz: List<QuizEntityModel>) {
        quizDow.insertQuiz(quiz)
    }


    private fun getData(){
                cloud.collection("Prayer ").orderBy("id")
                .get(Source.SERVER)
                .addOnCompleteListener { taskQuerySnapshot ->
                    if (taskQuerySnapshot.isSuccessful) {
                        for (query in taskQuerySnapshot.result!!) {
                            val index =  taskQuerySnapshot.result!!.toObjects(PrayerServerModel::class.java)
                            val cacheIndex = mapper.convertToCacheList(index)
                           addToRoom(cacheIndex)
                            latch.countDown()
                            Result.success()
                        }
                    }
                    else{
                        Result.retry()
                    }
               }.addOnFailureListener {
                    Log.d("error", it.toString())
                            latch.countDown()
                    Result.retry()
                }
    }

    private fun getOrderOfMass(){
        cloud.collection("Order of mass")
                .get(Source.SERVER)
                .addOnCompleteListener { taskQuerySnapshot ->
                    if (taskQuerySnapshot.isSuccessful) {
                        for (query in taskQuerySnapshot.result!!) {
                            val index =  taskQuerySnapshot.result!!.toObjects(OOMServerModel::class.java)
                            val cacheIndex = oomMapper.convertToCacheList(index)
                            addOomToRoom(cacheIndex)
                            latch.countDown()
                            Result.success()
                        }
                    }
                    else{
                        Result.retry()
                    }
                }.addOnFailureListener {
                    Log.d("error", it.toString())
                    latch.countDown()
                    Result.retry()
                }
    }

    private fun getFactData(){
        cloud.collection("Facts")
                .get(Source.SERVER)
                .addOnCompleteListener { taskQuerySnapshot ->
                    if (taskQuerySnapshot.isSuccessful) {
                        for (query in taskQuerySnapshot.result!!) {
                            val index =  taskQuerySnapshot.result!!.toObjects(FactServerModel::class.java)
                            val cacheIndex = factMapper.convertToCacheList(index)
                            addToRoomFact(cacheIndex)
                            latch.countDown()
                            Result.success()
                        }
                    }
                    else{
                        Result.retry()
                    }
                }.addOnFailureListener {
                    Log.d("error", it.toString())
                    latch.countDown()
                    Result.retry()
                }
    }

    private fun getBibleQuiz(){
        cloud.collection("Bible")
                .get(Source.SERVER)
                .addOnCompleteListener { taskQuerySnapshot ->
                    if (taskQuerySnapshot.isSuccessful) {
                        for (query in taskQuerySnapshot.result!!) {
                            val index = taskQuerySnapshot.result!!.toObjects(QuizModel::class.java)
                            val cacheIndex = quizMapper.convertToCacheList(index)
                            addToRoomQuiz(cacheIndex)
                            latch.countDown()
                            Result.success()
                        }
                        }
                    else{
                        Result.retry()
                    }
                }.addOnFailureListener {
                    Log.d("error", it.toString())
                    latch.countDown()
                    Result.retry()
                }
    }

    private fun getCatholicFaithQuiz(){
        cloud.collection("Catholic Faith")
                .get(Source.SERVER)
                .addOnCompleteListener { taskQuerySnapshot ->
                    if (taskQuerySnapshot.isSuccessful) {
                        latch.countDown()
                        Result.success()
                    }
                    else{
                        Result.retry()
                    }
                }.addOnFailureListener {
                    Log.d("error", it.toString())
                    latch.countDown()
                    Result.retry()
                }
    }

    private fun doAllWork(){
        getData()
        getFactData()
        getBibleQuiz()
        getOrderOfMass()
        getCatholicFaithQuiz()
    }
}