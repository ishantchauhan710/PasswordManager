package com.ishant.passwordmanager.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "salts")
data class EncryptedSalt (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val entryDetailId: Long,
    val emdSalt: String,
    val eedSalt: String
)