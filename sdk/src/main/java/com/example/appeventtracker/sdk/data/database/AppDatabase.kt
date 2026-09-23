package com.example.appeventtracker.sdk.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appeventtracker.sdk.data.dao.EventDao
import com.example.appeventtracker.sdk.data.model.EventEntity

@Database(
    entities = [EventEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao() : EventDao
}

internal val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE events ADD COLUMN next_retry_at INTEGER")
    }
}