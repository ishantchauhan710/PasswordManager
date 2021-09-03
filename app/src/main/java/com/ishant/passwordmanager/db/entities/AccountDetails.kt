package com.ishant.passwordmanager.db.entities

data class AccountDetails (
    val id: Int,
    val passwordId: Int,
    val detailType: String,
    val detailContent: String
)