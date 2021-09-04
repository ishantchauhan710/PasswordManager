package com.ishant.passwordmanager.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_details")
data class EntryDetail (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var entryId: Long,
    val detailType: String,
    var detailContent: String
)