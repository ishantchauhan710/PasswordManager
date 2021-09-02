package com.ishant.passwordmanager.db.entities

data class AccountDetails (
    val id: Int,
    val passwordId: Int,
    val detailType: Int,
    val detailContent: String
)