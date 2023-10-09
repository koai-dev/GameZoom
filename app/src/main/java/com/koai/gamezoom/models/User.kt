package com.koai.gamezoom.models

data class User(
    val email: String?= null,
    val image: String? = "https://play-lh.googleusercontent.com/jkPd8JUvTB9_aYOKcH-14Yx_1PBMzdRP3TcTCyIzLY8G_E0tsf-seN4owqrq9ugV3vg=w240-h480-rw",
    val status: String? = "",
    val name: String? = "UNK Gamer",
    val userId: String ="",
    val accessToken: String? = null,
    val isVip: Boolean? = null,
    val fcmToken: String? = null
)