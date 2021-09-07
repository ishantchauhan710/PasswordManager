package com.ishant.passwordmanager.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_lock")
data class Lock(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val password: String,
    val key: String,
    val hint: String,
    val antiBruteforceEnabled: Int
)