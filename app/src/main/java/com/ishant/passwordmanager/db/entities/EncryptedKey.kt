package com.ishant.passwordmanager.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys")
data class EncryptedKey (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val entryDetailId: Long,
    val emdKey: String,
)