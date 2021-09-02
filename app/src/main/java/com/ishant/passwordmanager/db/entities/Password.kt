package com.ishant.passwordmanager.db.entities

data class Password (
    val id: Int,
    val title: String,
    val username: String,
    val email: String,
    val password: String,
    val category: Int,
    val icon: Int
)