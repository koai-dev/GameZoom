package com.koai.gamezoom.api

import com.koai.gamezoom.models.Game
import com.koai.gamezoom.models.base.BaseResponse
import retrofit2.http.*

interface ApiService {

    @GET("api/game/all")
    suspend fun getAllGame(): BaseResponse<MutableList<Game>>

    @GET("api/game/{typeGame}")
    suspend fun getGameDetail(@Path("typeGame") typeGame: String): BaseResponse<Game>

    @POST("api/game/updatePLayCount/{typeGame}")
    suspend fun updatePLayCount(@Path("typeGame") typeGame: String): BaseResponse<String>

    @POST("api/user/buy")
    suspend fun buyGame(@Body body: Map<String, String>): BaseResponse<Game>

}