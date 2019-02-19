package com.example.learn_room

import androidx.room.*

@Entity(tableName = "users", indices = arrayOf(
    Index(value = ["first_name", "last_name"],
    unique = true))
)
class User(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "first_name") var firstName: String?,
    @ColumnInfo(name = "last_name") var lastName: String?
) {
    @Ignore
    constructor(firstName: String= "", lastName: String = "") : this(0, firstName, lastName)

    override fun toString(): String {
        return "User(uid=$uid, firstName=$firstName, lastName=$lastName)"
    }

}