package com.johnmaricool.mario_designs.androidcomponents

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.database.SbDao
import com.johnmaricool.mario_designs.models.Book
import com.johnmaricool.mario_designs.models.EntityBook
import com.johnmaricool.mario_designs.models.EntityVerse
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject


class DbSetupWorker(context: Context,
                        workerParams: WorkerParameters) : Worker(context, workerParams) {

        @Inject
        lateinit var dao: SbDao


        override fun doWork(): Result {
            return if (populateTableBooks() && populateTableVerses()) Result.success() else Result.failure()
        }

        private fun populateTableBooks(): Boolean {
            Log.d(TAG, "populateTableBooks:")
            val res = applicationContext.resources
            var parts: Array<String>
            var line: String
            var number: Int
            var chapters: Int
            var verses: Int
            val fileName = res.getString(R.string.db_setup_asset_file_table_books)
            val separator = res.getString(R.string.db_setup_asset_file_table_books_separator)
            Log.d(TAG, "populateTableBooks: asset file [" + fileName
                    + "] separated by [" + separator + "]")
            try {
                val read = BufferedReader(
                        InputStreamReader(applicationContext.assets.open(fileName)))
                val newT = res.getString(R.string.new_testament)
                val oldT = res.getString(R.string.old_testament)
                while (read.readLine().also { line = it } != null) {
                    parts = line.split(separator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                    // we are expecting 5 parts in each line [description~number~name~chapters~verses]
                    if (parts.size != 5) {
                        Log.d(TAG, "populateTableBooks: : [$line] does not have 5 parts")
                        continue
                    }

                    // check that we do nt have empty values in description & name
                    if (parts[0].isEmpty() || parts[2].isEmpty()) {
                        Log.e(TAG, "populateTableBooks: empty description or name in [$line]")
                        continue
                    }

                    // validate book number
                    try {
                        number = parts[1].toInt()
                        if (number < 1 || number > Book.MAX_BOOKS) {
                            Log.e(TAG, "populateTableBooks: book number invalid in [$line]")
                            continue
                        }
                    } catch (e: NumberFormatException) {
                        Log.e(TAG, "populateTableBooks: book number invalid in [$line]", e)
                        continue
                    }

                    // validate chapters count
                    try {
                        chapters = parts[3].toInt()
                        if (chapters < 1) {
                            Log.e(TAG, "populateTableBooks: invalid chapters count in [$line]")
                            continue
                        }
                    } catch (e: NumberFormatException) {
                        Log.e(TAG, "populateTableBooks: invalid chapters count in [$line]", e)
                        continue
                    }

                    // validate verses count
                    try {
                        verses = parts[4].toInt()
                        if (verses < 1) {
                            Log.e(TAG, "populateTableBooks: invalid verses count in [$line]")
                            continue
                        }
                    } catch (e: NumberFormatException) {
                        Log.e(TAG, "populateTableBooks: invalid verses count in [$line]", e)
                        continue
                    }
                    dao.createBook(EntityBook(
                            parts[0], number, parts[2], chapters, verses, if (number > 39) newT else oldT))
                }
            } catch (ioe: IOException) {
                Log.e(TAG, "populateTableBooks: failed to open asset file [$fileName]", ioe)
                return false
            }
            return true
        }

        private fun populateTableVerses(): Boolean {
            Log.d(TAG, "populateTableVerses:")
            val res = applicationContext.resources
            var parts: Array<String>
            var line: String
            var number: Int
            var chapters: Int
            var verses: Int
            val fileName = res.getString(R.string.db_setup_asset_file_table_verses)
            val separator = res.getString(R.string.db_setup_asset_file_table_verses_separator)
            Log.d(TAG, "populateTableVerses: asset file [" + fileName
                    + "] separated by [" + separator + "]")
            try {
                val read = BufferedReader(
                        InputStreamReader(applicationContext.assets.open(fileName)))
                while (read.readLine().also { line = it } != null) {
                    parts = line.split(separator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                    // we are expecting 5 parts in each line [translation~number~chapter~verse~text]
                    if (parts.size != 5) {
                        Log.e(TAG, "populateTableVerses: line does not have 5 values [$line]")
                        continue
                    }
                    if (parts[0].isEmpty() || parts[4].isEmpty()) {
                        Log.e(TAG, "populateTableVerses: translation or verse is empty in [$line]")
                        continue
                    }

                    // validate book number
                    try {
                        number = parts[1].toInt()
                        if (number < 1 || number > Book.MAX_BOOKS) {
                            Log.e(TAG, "populateTableVerses: book number invalid in [$line]")
                            continue
                        }
                    } catch (e: NumberFormatException) {
                        Log.e(TAG, "populateTableVerses: book number invalid in [$line]", e)
                        continue
                    }

                    // validate chapter
                    try {
                        chapters = parts[2].toInt()
                        if (chapters < 1) {
                            Log.e(TAG, "populateTableVerses: invalid chapters count in [$line]")
                            continue
                        }
                    } catch (e: NumberFormatException) {
                        Log.e(TAG, "populateTableVerses: invalid chapters count in [$line]", e)
                        continue
                    }

                    // validate verses
                    try {
                        verses = parts[3].toInt()
                        if (verses < 1) {
                            Log.e(TAG, "populateTableVerses: invalid verses count in [$line]")
                            continue
                        }
                    } catch (e: NumberFormatException) {
                        Log.e(TAG, "populateTableVerses: invalid verses count in [$line]", e)
                        continue
                    }
                    dao.createVerse(EntityVerse(parts[0], number, chapters, verses, parts[4]))
                }
                read.close()
            } catch (ioe: IOException) {
                Log.e(TAG, "doWork: failed to open asset file [$fileName]", ioe)
                return false
            }
            return true
        }

        companion object {
            private const val TAG = "DbSetupWorker"
        }

}