package com.koai.gamezoom.models.base

data class BaseResponse<T>(val statusCode: Int = 200, val message: String? = TYPE_SUCCESS, val data: T? = null){
    companion object{
        const val TYPE_SUCCESS = "success!"
        const val TYPE_FAIL = "fail!"
    }
}
