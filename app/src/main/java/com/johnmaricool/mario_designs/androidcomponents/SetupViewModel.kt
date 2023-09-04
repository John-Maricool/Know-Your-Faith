package com.johnmaricool.mario_designs.androidcomponents

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.database.SbDao
import com.johnmaricool.mario_designs.models.Book
import com.johnmaricool.mario_designs.models.EntityBook
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SetupViewModel
@Inject constructor(@ApplicationContext application: Application, var dao: SbDao) : AndroidViewModel(application) {

    fun validateTableData(): LiveData<Int?>? {
        val app = getApplication<Application>()
        return dao.validateTableData(
                app.getString(R.string.db_setup_validation_book_name_first).trim { it <= ' ' },
                app.getString(R.string.db_setup_validation_book_name_last).trim { it <= ' ' },
                app.getString(R.string.db_setup_validation_book_number_last).trim { it <= ' ' },
                app.getString(R.string.db_setup_validation_verse_number_last).trim { it <= ' ' })
    }

    fun setupDatabase(workManager: WorkManager): UUID {
        if (workerUuid != null) {
            return workerUuid!!
        }
        val workRequest = OneTimeWorkRequest.Builder(DbSetupWorker::class.java).build()
        workManager.enqueue(workRequest)
        return workRequest.id
    }

    fun setWorkerUuid(uuid: UUID) {
        workerUuid = uuid
    }

    val allBooks: LiveData<List<EntityBook>>
        get() = dao.allBookRecords

    fun updateCacheBooks(bookList: List<Book?>) {
        Book.updateCacheBooks(bookList)
    }

    val isCacheUpdated: Boolean
        get() = Book.isCacheUpdated

    companion object {
        var workerUuid: UUID? = null
    }
}