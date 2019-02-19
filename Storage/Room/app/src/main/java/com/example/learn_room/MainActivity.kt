package com.example.learn_room

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "test-db"
        ).build()

        Thread({
//            db.userDao().insertAll(User("A1", "B1"))
//            db.userDao().insertAll(User("A2", "B2"))
//            db.userDao().insertAll(User("A1", "B1"))
//            db.userDao().insertAll(User("A1", "B1"))
//            db.userDao().insertAll(User("A1", "B1"))

//            db.userDao().delete(User(2, "A2", "B2"))

//            db.userDao().update("A1")
//            var users = db.userDao().getAll()
//            for (user in users) {
//                Log.d("MainActivity", "users = " + user)
//            }

//            db.bookDao().insertAll(Book("Book 1", 1))
//            db.bookDao().insertAll(Book("Book 2", 1))
//            db.bookDao().insertAll(Book("Book 3", 2))

            var books = db.bookDao().loadAllByUserId(IntArray(1){2})
            for (book in books) {
                Log.d("MainActivity", "books = " + book)
            }

        }).start()

    }
}
