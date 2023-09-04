package com.johnmaricool.mario_designs.models

import androidx.annotation.IntRange
import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "sb_books", primaryKeys = ["number", "name", "chapters", "verses"])
class EntityBook(@field:ColumnInfo(name = "description") val description: String,
                 @field:ColumnInfo(name = "number") @field:IntRange(from = 1, to = Book.MAX_BOOKS.toLong()) @get:IntRange(from = 1, to = Book.MAX_BOOKS.toLong())
                 @param:IntRange(from = 1, to = Book.MAX_BOOKS.toLong()) val number: Int,
                 @field:ColumnInfo(name = "name") val name: String,
                 @field:ColumnInfo(name = "chapters") @field:IntRange(from = 1) @get:IntRange(from = 1)
                 @param:IntRange(from = 1) val chapters: Int,
                 @field:ColumnInfo(name = "verses") @field:IntRange(from = 1) @get:IntRange(from = 1)
                 @param:IntRange(from = 1) val verses: Int,
                 @field:ColumnInfo(name = "testament") val testament: String) {

    override fun toString(): String {
        return (number
                .toString() + "-" + name
                + "-" + description
                + "-" + chapters
                + "-" + verses
                + "-" + testament)
    }
}