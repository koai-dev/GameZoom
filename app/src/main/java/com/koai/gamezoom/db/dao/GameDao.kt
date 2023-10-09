package com.koai.gamezoom.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.koai.gamezoom.models.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getAllGameByName(): Flow<List<Game>>

    @Insert
    fun insertAll(vararg games: Game)

    @Delete
    fun delete(game: Game)
}