package com.ishant.passwordmanager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.db.entities.EntryDetails

@Database(
    entities = [Entry::class,EntryDetails::class],
    version = 1
)
abstract class PasswordManagerDatabase: RoomDatabase() {
    abstract fun getPasswordManagerDao(): PasswordManagerDao

    companion object {
        @Volatile
        private var instance: PasswordManagerDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PasswordManagerDatabase::class.java,
                "article_db.db"
            ).build()
    }
}