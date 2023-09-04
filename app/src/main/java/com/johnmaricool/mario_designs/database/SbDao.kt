package com.johnmaricool.mario_designs.database

import androidx.annotation.IntRange
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johnmaricool.mario_designs.models.Book
import com.johnmaricool.mario_designs.models.EntityBook
import com.johnmaricool.mario_designs.models.EntityVerse


@Dao
interface SbDao {
    @Query("select sum(record_count) from ("
            + " select count(number) as record_count from sb_books "
            + " where lower(name) = lower(:bookNameFirst) and number = 1 "
            + " union all "
            + " select count(number) as record_count from sb_books "
            + " where lower(name) = lower(:bookNameLast) and number = 66 "
            + " union all "
            + " select count(distinct number) as record_count from sb_books "
            + " where number = :lastBookNumber "
            + " union all "
            + " select count(distinct book||'~'||chapter||'~'||verse) as record_count from sb_verses "
            + " where book||'~'||chapter||'~'||verse = :lastVerseReference"
            + " );")
    fun validateTableData(bookNameFirst: String,
                          bookNameLast: String,
                          lastBookNumber: String,
                          lastVerseReference: String): LiveData<Int?>?



    @Insert(entity = EntityBook::class, onConflict = OnConflictStrategy.REPLACE)
    fun createBook(entityBook: EntityBook)

    @Insert(entity = EntityVerse::class, onConflict = OnConflictStrategy.REPLACE)
    fun createVerse(entityVerse: EntityVerse)

    @get:Query("select * from sb_books order by number")
    val allBookRecords: LiveData<List<EntityBook>>

    @Query("select * from sb_verses "
            + "where book=:bookNum and chapter=:chapterNum and verse=:verseNumb")
    fun getVerse(@IntRange(from = 1, to = Book.MAX_BOOKS.toLong()) bookNum: Int,
                 @IntRange(from = 1) chapterNum: Int,
                 @IntRange(from = 1) verseNumb: Int): LiveData<EntityVerse?>?

    @Query("select * from sb_verses "
            + "where book=:bookNum and chapter=:chapterNum order by verse asc")
    fun getChapter(
            @IntRange(from = 1, to = Book.MAX_BOOKS.toLong()) bookNum: Int,
            @IntRange(from = 1) chapterNum: Int): LiveData<List<EntityVerse?>?>?

    @Query("select * from sb_verses where lower(text) like lower(:text)")
    fun findVersesContainingText(text: String): LiveData<List<EntityVerse?>?>?

    @Query("select * from sb_verses"
            + " where book in (:bookNumbers)"
            + " and chapter in (:chapterNumbers)"
            + " and verse in (:verseNumbers)"
            + " order by book, chapter, verse")
    fun getVerses(bookNumbers: List<Int?>,
                  chapterNumbers: List<Int?>,
                  verseNumbers: List<Int?>): LiveData<List<EntityVerse?>?>?

}