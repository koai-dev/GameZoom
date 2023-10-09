package com.koai.gamezoom.account

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.koai.gamezoom.api.ApiFirebase
import com.koai.gamezoom.models.Game
import com.koai.gamezoom.models.User

object AccountUtils {
    var user: User? = null
    fun isLogin() = (user != null)
    fun auth() {
        if (Firebase.auth.currentUser != null){
            user = User(
                userId = Firebase.auth.currentUser?.uid!!,
                name = Firebase.auth.currentUser?.displayName ?: "No Name",
                email = Firebase.auth.currentUser?.email,
                image = Firebase.auth.currentUser?.photoUrl?.toString(),
            )
            ApiFirebase.getUserInfo(user!!.userId, user!!)
        }
    }

    fun signOut(){
        Firebase.auth.signOut()
        user = null
    }

}