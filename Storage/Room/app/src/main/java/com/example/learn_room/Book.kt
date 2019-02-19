package com.example.learn_room

import androidx.room.*

@Entity(tableName = "books",
    foreignKeys = arrayOf(
        ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("user_id"))
    ))
class Book(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    @ColumnInfo(name = "user_id") var userId: Int
) {
    @Ignore
    constructor(name: String, userId: Int) : this(0, name, userId)

    override fun toString(): String {
        return "Book(id=$id, name='$name', userId=$userId)"
    }
}