package com.koai.gamezoom.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koai.gamezoom.account.AccountUtils
import com.koai.gamezoom.api.ApiController
import com.koai.gamezoom.api.ApiService
import com.koai.gamezoom.models.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    val game = MutableLiveData<Game?>()

    fun getDetailGame(typeGame: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = (ApiController.getService() as ApiService).getGameDetail(typeGame)
            game.postValue(result.data)
        }
    }

    fun updatePLayCount(typeGame: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val apiService = ApiController.getService()
            val response = (apiService as ApiService).updatePLayCount(typeGame)
        }
    }

    fun onBuySuccess(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            (ApiController.getApiService() as ApiService).buyGame(
                mapOf(
                    "userId" to AccountUtils.user!!.userId,
                    "type" to productId
                )
            )
        }
    }

    fun onBuyFail() {

    }
}