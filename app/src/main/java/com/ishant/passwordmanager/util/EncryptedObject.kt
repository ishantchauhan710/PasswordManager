package com.ishant.passwordmanager.util

data class EncryptedObject(
    val emdSalt: String,
    val eedSalt: String,
    val encryptedData: String
)