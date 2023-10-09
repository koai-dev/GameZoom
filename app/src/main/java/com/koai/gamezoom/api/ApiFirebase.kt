package com.koai.gamezoom.api

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.koai.gamezoom.account.AccountUtils
import com.koai.gamezoom.models.Game
import com.koai.gamezoom.models.User
import com.koai.gamezoom.utils.Const
import kotlinx.coroutines.tasks.await

object ApiFirebase {

    fun addGame(typeGame: String, game: Game, userId: String) {
        Firebase.firestore.collection(typeGame).document(userId).get().addOnCompleteListener {
            if (it.result.exists()) {
                updateGameScore(typeGame, game, userId)
            } else {
                Firebase.firestore.collection(typeGame).document(userId).set(game)
            }
        }
    }

    private fun updateGameScore(typeGame: String, game: Game, userId: String) {
        val map = mutableMapOf<String, Any?>()
        map["score"] = game.score
        map["timestamp"] = game.timestamp
        map["userId"] = game.userId
        map["userImage"] = game.userImage
        map["userName"] = game.userName
        map["buy"] = game.isBuy
        Firebase.firestore.collection(typeGame).document(userId).update(map)
    }

    fun getGamesRank(typeGame: String, listener: GameListener? = null) {
        Firebase.firestore.collection(typeGame).orderBy("score", Query.Direction.DESCENDING).limit(50).get().addOnCompleteListener {
            if (it.isSuccessful) {
                listener?.onSuccess(it.result.toObjects(Game::class.java))
            } else {
                listener?.onError(it.exception?.message ?: "Undefine Error!")
            }
        }
    }

    fun getUserInfo(userId: String, user: User) {
        Firebase.firestore.collection(Const.USERS).document(userId).get().addOnCompleteListener {
            if (it.result.exists()) {
                AccountUtils.user = it.result.toObject(User::class.java)
            } else {
                Firebase.firestore.collection(Const.USERS).document(userId).set(user)
                    .addOnCompleteListener {
                        getUserInfo(userId, user)
                    }
            }
        }
    }


    interface GameListener {
        fun onSuccess(games: List<Game>? = null)
        fun onError(mess: String? = null)
    }
}