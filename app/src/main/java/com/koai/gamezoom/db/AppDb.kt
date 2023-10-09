package com.koai.gamezoom.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.koai.gamezoom.db.dao.GameDao
import com.koai.gamezoom.models.Game

@Database(entities = [Game::class], version = 2, exportSchema = true, autoMigrations = [AutoMigration(1,2)])
abstract class AppDb : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDb? = null

        fun getDatabase(context: Context): AppDb {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "game_zoom_db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}