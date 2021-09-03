package com.ishant.passwordmanager.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_details")
data class EntryDetails (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val entryId: Int,
    val detailType: String,
    val detailContent: String
)