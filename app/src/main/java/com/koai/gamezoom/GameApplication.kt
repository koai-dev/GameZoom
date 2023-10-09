package com.koai.gamezoom

import android.app.Application
import com.koai.gamezoom.db.AppDb
import com.koai.gamezoom.db.repository.GameRepository
import com.koai.gamezoom.utils.AppConfig

class GameApplication: Application() {
    private val database by lazy { AppDb.getDatabase(this) }
    val repository by lazy { GameRepository(database.gameDao()) }

    override fun onCreate() {
        super.onCreate()
       AppConfig.initRemote()
    }
}