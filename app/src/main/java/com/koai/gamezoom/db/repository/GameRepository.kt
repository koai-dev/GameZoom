package com.koai.gamezoom.db.repository

import androidx.annotation.WorkerThread
import com.koai.gamezoom.db.dao.GameDao
import com.koai.gamezoom.models.Game
import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {
    fun allGameByName(): Flow<List<Game>> = gameDao.getAllGameByName()

    @WorkerThread
    suspend fun insert(game: Game) {
        gameDao.insertAll(game)
    }

    @WorkerThread
    suspend fun delete(game: Game) {
        gameDao.delete(game)
    }
}