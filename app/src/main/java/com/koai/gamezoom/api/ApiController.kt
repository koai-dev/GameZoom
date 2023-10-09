package com.koai.gamezoom.api

import com.koai.base.network.BaseApiController

object ApiController : BaseApiController() {
    override fun getBaseUrl(): String  = "https://gamezoomserver-e521d197a8b6.herokuapp.com/"
    override fun getApiService(): Class<*> = ApiService::class.java
}