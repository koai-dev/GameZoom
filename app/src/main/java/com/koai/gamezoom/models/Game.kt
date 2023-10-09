package com.koai.gamezoom.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    @PrimaryKey(autoGenerate = true) val no: Int = 0,
    val isBuy: Boolean = false,
    val id: Int = 0,
    val name: String? = "UNK Game",
    val playCount: Int? = 0,
    val price: Int? = 0,
    val productId: String? = "",
    val score: Int? = 0,
    val thumb: String? = "https://play-lh.googleusercontent.com/jkPd8JUvTB9_aYOKcH-14Yx_1PBMzdRP3TcTCyIzLY8G_E0tsf-seN4owqrq9ugV3vg=w240-h480-rw",
    val timestamp: Long = 0,
    val type: String? = "",
    val userId: String? = null,
    val userImage: String? = "https://play-lh.googleusercontent.com/jkPd8JUvTB9_aYOKcH-14Yx_1PBMzdRP3TcTCyIzLY8G_E0tsf-seN4owqrq9ugV3vg=w240-h480-rw",
    val userName: String? = "UNK Gamer",
    val description: String? = "",
) {
    companion object {
        const val TYPE_FARM_PUZZLE = "farm_puzzle"
        const val TYPE_KNIFE = "knife"
        const val TYPE_JUMP_PRINCESS = "jump_princess"
        const val TYPE_2048 = "game_2048"
        const val TYPE_FREE_DRAWER = "free_drawer_puzzle"
        const val TYPE_15_PUZZLE = "puzzle15"
    }
}
