package com.ishant.passwordmanager.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_details")
data class EntryDetail (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val entryId: Long,
    val detailType: String,
    val detailContent: String
)