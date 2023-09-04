package com.johnmaricool.mario_designs.models

import android.text.Spanned
import android.util.Log
import androidx.annotation.IntRange
import androidx.core.text.HtmlCompat
import java.util.Objects


class Verse(verse: EntityVerse, book: Book) : Comparable<Any?> {
    val translation: String

    @get:IntRange(from = 1, to = Book.MAX_BOOKS.toLong())
    @IntRange(from = 1, to = Book.MAX_BOOKS.toLong())
    val bookNumber: Int

    @get:IntRange(from = 1)
    @IntRange(from = 1)
    val chapterNumber: Int

    @get:IntRange(from = 1)
    @IntRange(from = 1)
    val verseNumber: Int
    val verseText: String
    val book: Book

    init {
        translation = verse.translation
        bookNumber = verse.book
        chapterNumber = verse.chapter
        verseNumber = verse.verse
        verseText = verse.text
        this.book = book
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val that = o as Verse
        return bookNumber == that.bookNumber && chapterNumber == that.chapterNumber && verseNumber == that.verseNumber && verseText == that.verseText && translation == that.translation && book.equals(that.book)
    }

    override fun hashCode(): Int {
        return Objects.hash(translation, bookNumber, chapterNumber, verseNumber, verseText, book)
    }

    override operator fun compareTo(o: Any?): Int {
        val that = o as Verse
        val thisPosition = (
                bookNumber.toString() + "" + chapterNumber + "" + verseNumber).toInt()
        val thatPosition = (
                that.bookNumber.toString() + "" + that.chapterNumber + "" + that.verseNumber).toInt()
        return thisPosition - thatPosition
    }

    val reference: String
        get() = (bookNumber
                .toString() + REFERENCE_SEPARATOR
                + chapterNumber
                + REFERENCE_SEPARATOR
                + verseNumber)

    fun getFormattedContentForSearchResult(template: String): Spanned {
        if (template.isEmpty()) {
            Log.e(TAG, "getFormattedContentForSearchResult:",
                    IllegalArgumentException("Empty template value passed, returning empty string"))
            return HtmlCompat.fromHtml("", HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
        return HtmlCompat.fromHtml(
                java.lang.String.format(template, book.name, chapterNumber, verseNumber, verseText),
                HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    fun getFormattedContentForBookmark(template: String): Spanned {
        if (template.isEmpty()) {
            Log.e(TAG, "getFormattedContentForSearchResult:",
                    IllegalArgumentException("Empty template value passed, returning empty string"))
            return HtmlCompat.fromHtml("", HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
        return HtmlCompat.fromHtml(
                java.lang.String.format(template, book.name, chapterNumber, verseNumber, verseText),
                HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    fun getFormattedContentForShareChapterVerse(template: String): Spanned {
        if (template.isEmpty()) {
            Log.e(TAG, "getFormattedContentForSearchResult:",
                    IllegalArgumentException("Empty template value passed, returning empty string"))
            return HtmlCompat.fromHtml("", HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
        return HtmlCompat.fromHtml(String.format(template, verseNumber, verseText),
                HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    fun getFormattedContentForChapter(template: String): Spanned {
        if (template.isEmpty()) {
            Log.e(TAG, "getFormattedContentForSearchResult:",
                    IllegalArgumentException("Empty template value passed, returning empty string"))
            return HtmlCompat.fromHtml("", HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
        return HtmlCompat.fromHtml(String.format(template, verseNumber, verseText),
                HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    companion object {
        const val MAX_VERSES = 31098
        const val REFERENCE_SEPARATOR = ":"
        private const val TAG = "Verse"
        fun splitReference(reference: String): IntArray? {
            if (!validateReference(reference)) {
                return null
            }
            val parts = reference.split(REFERENCE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return intArrayOf(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
        }

        fun validateReference(reference: String): Boolean {
            if (reference.isEmpty()) {
                Log.e(TAG, "validateVerseReference:", IllegalArgumentException(
                        "Verse reference [$reference] is empty."
                ))
                return false
            }
            if (!reference.contains(REFERENCE_SEPARATOR)) {
                Log.e(TAG, "validateVerseReference:", IllegalArgumentException(
                        "Verse reference [" + reference + "] has no separators [" + REFERENCE_SEPARATOR + "]"
                ))
                return false
            }
            val part = reference.split(REFERENCE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (part.size != 3) {
                Log.e(TAG, "validateVerseReference:", IllegalArgumentException(
                        "Verse reference [$reference] does not contain 3 parts"
                ))
            }
            @IntRange(from = 1, to = Book.MAX_BOOKS.toLong()) val book: Int
            @IntRange(from = 1) val chapter: Int
            @IntRange(from = 1) val verse: Int
            try {
                book = part[0].toInt()
                chapter = part[1].toInt()
                verse = part[2].toInt()
            } catch (nfe: NumberFormatException) {
                Log.e(TAG, "validateVerseReference: book, chapter or book is NAN", nfe)
                return false
            }
            if (book < 1 || book > Book.MAX_BOOKS) {
                Log.e(TAG, "validateVerseReference:", IllegalArgumentException(
                        "Verse reference [$reference] has an invalid book number [$book]"
                ))
                return false
            }
            if (chapter < 1) {
                Log.e(TAG, "validateVerseReference:", IllegalArgumentException(
                        "Verse reference [$reference] has an invalid chapter number [$chapter]"
                ))
                return false
            }
            if (verse < 1) {
                Log.e(TAG, "validateVerseReference:", IllegalArgumentException(
                        "Verse reference [$reference] has an invalid verse number [$verse]"
                ))
                return false
            }
            return true
        }

        fun createReference(@IntRange(from = 1, to = Book.MAX_BOOKS.toLong()) book: Int,
                            @IntRange(from = 1) chapter: Int,
                            @IntRange(from = 1) verse: Int): String {
            if (book < 1 || chapter < 1 || verse < 1) {

                return ""
            }
            return book.toString() + REFERENCE_SEPARATOR + chapter + REFERENCE_SEPARATOR + verse
        }
    }
}