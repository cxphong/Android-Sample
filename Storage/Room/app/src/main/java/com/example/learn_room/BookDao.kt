package com.example.learn_room

import androidx.room.*

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Query("SELECT * FROM books WHERE user_id IN (:userIds)")
    fun loadAllByUserId(userIds: IntArray): List<Book>

    @Insert
    fun insertAll(book: Book)

    @Delete
    fun delete(book: Book)
}
