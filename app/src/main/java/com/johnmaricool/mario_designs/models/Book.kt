package com.johnmaricool.mario_designs.models


import androidx.annotation.IntRange
import java.util.Objects
import java.util.TreeMap


class Book(book: EntityBook) : Comparable<Any?> {
    val description: String

    @get:IntRange(from = 1, to = MAX_BOOKS.toLong())
    @IntRange(from = 1, to = MAX_BOOKS.toLong())
    val number: Int
    val name: String

    @get:IntRange(from = 1)
    @IntRange(from = 1)
    val chapters: Int

    @get:IntRange(from = 1)
    @IntRange(from = 1)
    val verses: Int
    val testament: String

    init {
        description = book.description
        number = book.number
        name = book.name
        chapters = book.chapters
        verses = book.verses
        testament = book.testament
    }

    override operator fun compareTo(that: Any?): Int {
        return number - (that as Book).number
    }

    override fun equals(newObj: Any?): Boolean {
        if (this === newObj) {
            return true
        }
        if (newObj == null || this.javaClass != newObj.javaClass) {
            return false
        }
        val that = newObj as Book
        return number == that.number && chapters == that.chapters && verses == that.verses && name == that.name && testament == that.testament && description == that.description
    }

    override fun hashCode(): Int {
        return Objects.hash(number, name, chapters, verses, testament, description)
    }

    override fun toString(): String {
        return (number
                .toString() + "-" + name
                + "-" + description
                + "-" + chapters
                + "-" + verses
                + "-" + testament)
    }

    companion object {
        const val MAX_BOOKS= 66
        private const val TAG = "Book"
        private val BOOK_CACHE = TreeMap<Int, Book>()
        fun updateCacheBooks(bookList: List<Book?>) {
            val maxCount = MAX_BOOKS
            if (isCacheUpdated) {
                return
            }
            BOOK_CACHE.clear()
            for (book in bookList) {
                BOOK_CACHE[book.number] = book
            }
        }

        val isCacheUpdated: Boolean
            get() = BOOK_CACHE.size == MAX_BOOKS

        fun getCachedBook(@IntRange(from = 1, to = MAX_BOOKS.toLong()) bookNumber: Int): Book? {
            return BOOK_CACHE[bookNumber]
        }

        val cachedBookList: Set<Int>
            get() = BOOK_CACHE.keys
    }
}
