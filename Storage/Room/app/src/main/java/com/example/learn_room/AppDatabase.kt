package com.example.learn_room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class, Book::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao
}