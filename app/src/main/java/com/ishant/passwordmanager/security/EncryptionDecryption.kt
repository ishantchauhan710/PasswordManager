package com.ishant.passwordmanager.security

import com.ishant.passwordmanager.util.EncryptedObject
import com.tozny.crypto.android.AesCbcWithIntegrity.*

class EncryptionDecryption {

    init {
        System.loadLibrary("key-jni")
    }

    external fun getKey(): String


    companion object {

        fun encrypt(data: String, emdPasswordArg: String, eedPasswordArg: String): EncryptedObject {
            val emdSalt = saltString(generateSalt())
            val emdKey = generateKeyFromPassword(emdPasswordArg,emdSalt)
            val emdEncryptedString = encrypt(data, emdKey).toString()
            val eedSalt = saltString(generateSalt())
            val eedKey = generateKeyFromPassword(eedPasswordArg,eedSalt)
            val eedEncryptedString = encrypt(emdEncryptedString, eedKey).toString()
            return EncryptedObject(keyString(emdKey), keyString(eedKey), eedEncryptedString)
        }

        fun decrypt(encryptedData: String, emdKeyArg: String, eedKeyArg: String): String {
            val eedDecryptedString = decryptString(CipherTextIvMac(encryptedData), keys(eedKeyArg))
            val emdDecryptedString = decryptString(CipherTextIvMac(eedDecryptedString),keys(emdKeyArg))
            return emdDecryptedString
        }


    }

}


/*

        // Variable Names
        // emd: Encrypt Main Data
        // eed: Encrypt Encrypted Data
        // This is the main data of user that will be encrypted. It will include emails, passwords, usernames etc
        val dataToEncrypt = "This is data to Encrypt"
        // This is a password to generate key
        val emdPassword = "ishant"
        // A random salt is generated which will be used in creating key
        val emdSalt = saltString(generateSalt())
        // A key is generated using the password and salt
        val emdKey = generateKeyFromPassword(emdPassword,emdSalt)
        // Our main data will get encrypted using the key we generated
        val emdEncryptedString = encrypt(dataToEncrypt, emdKey).toString()
        // Now we will again encrypt this encrypted string
        // This is a new password to create a new key to encrypt the encrypted string
        val eedPassword = "chauhan"
        // Another salt generated to generate new key to encrypt the encrypted string
        val eedSalt = saltString(generateSalt())
        // New key is generated using new password and new salt
        val eedKey = generateKeyFromPassword(eedPassword,eedSalt)
        // Now here we will encrypt the encrypted string
        val eedEncryptedString = encrypt(emdEncryptedString, eedKey).toString()
        // Now we need to decrypt the data
        // This will return the first level encrypted string of our main data
        val eedDecryptedString = decryptString(CipherTextIvMac(eedEncryptedString),eedKey)
        val emdDecryptedString = decryptString(CipherTextIvMac(emdEncryptedString),emdKey)
        val finalDecryptedString = emdDecryptedString
        Log.e("IshantEncryption","Password 1 (EMD): $emdPassword\n")
        Log.e("IshantEncryption","Password 2 (EED): $eedPassword\n")
        Log.e("IshantEncryption","Data to Encrypt: $dataToEncrypt\n")
        Log.e("IshantEncryption","Key1 (EMD): $emdKey\n")
        Log.e("IshantEncryption","Key2 (EED): $eedKey\n")
        Log.e("IshantEncryption","Encrypted Data String1 (EMD): $emdEncryptedString\n")
        Log.e("IshantEncryption","Encrypted Data String2 (EED): $eedEncryptedString\n")
        Log.e("IshantEncryption","Main Data: $dataToEncrypt\n")
        Log.e("IshantEncryption","Final Decrypted String: $finalDecryptedString\n")


*/

/*
        val dataToEncrypt = "This is a text to be encrypted"
        val password = "ishant"
        val salt = saltString(generateSalt())
        val key1 = generateKeyFromPassword(password,salt)
        val encryptedString = encrypt(dataToEncrypt, key1).toString()
        val key2 = generateKeyFromPassword(password,salt)
        val decryptedString = decryptString(CipherTextIvMac(encryptedString),key2)
        Log.e("IshantEncryption","Data: $dataToEncrypt\n")
        Log.e("IshantEncryption","Key1: $key1\n")
        Log.e("IshantEncryption","Key2: $key2\n")
        Log.e("IshantEncryption","Encrypted Data: $encryptedString\n")
        Log.e("IshantEncryption","Decrypted Data: $decryptedString\n")

        */