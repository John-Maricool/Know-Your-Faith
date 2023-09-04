package com.johnmaricool.mario_designs.models

import androidx.annotation.IntRange
import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "sb_verses", primaryKeys = ["book", "chapter", "verse"])
class EntityVerse(@field:ColumnInfo(name = "translation") val translation: String,
                  @field:IntRange(from = 1, to = Book.MAX_BOOKS.toLong()) @field:ColumnInfo(name = "book") @get:IntRange(from = 1, to = Book.MAX_BOOKS.toLong())
                  @param:IntRange(from = 1, to = Book.MAX_BOOKS.toLong()) val book: Int,
                  @field:IntRange(from = 1) @field:ColumnInfo(name = "chapter") @get:IntRange(from = 1)
                  @param:IntRange(from = 1) val chapter: Int,
                  @field:IntRange(from = 1) @field:ColumnInfo(name = "verse") @get:IntRange(from = 1)
                  @param:IntRange(from = 1) val verse: Int,
                  @field:ColumnInfo(name = "text") val text: String) {

    override fun toString(): String {
        return (translation
                + Verse.REFERENCE_SEPARATOR
                + book
                + Verse.REFERENCE_SEPARATOR
                + chapter
                + Verse.REFERENCE_SEPARATOR
                + verse
                + Verse.REFERENCE_SEPARATOR
                ) + text
    }
}