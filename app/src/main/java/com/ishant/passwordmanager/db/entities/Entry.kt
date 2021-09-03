package com.ishant.passwordmanager.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry")
data class Entry (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val category: Int,
    val icon: Int
)