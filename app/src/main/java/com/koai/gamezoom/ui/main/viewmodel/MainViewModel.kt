package com.koai.gamezoom.ui.main.viewmodel

import android.app.PendingIntent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.koai.gamezoom.api.ApiController
import com.koai.gamezoom.api.ApiService
import com.koai.gamezoom.models.Game
import com.koai.gamezoom.models.User
import com.koai.gamezoom.models.base.BaseResponse
import com.koai.gamezoom.ui.main.MainActivity
import com.koai.gamezoom.utils.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val listGame = MutableLiveData<MutableList<Game>?>()

    fun getAllGames() {
        viewModelScope.launch(Dispatchers.IO){
            val apiService = ApiController.getService()
            val games = (apiService as ApiService).getAllGame()
            listGame.postValue(games.data)
        }
    }

    fun loginByFb(activity: MainActivity){
        LoginManager.getInstance().logInWithReadPermissions(
            activity, activity.callbackManager, listOf("public_profile", "email")
        )
    }

    fun loginByGg(oneTapClient: SignInClient,
                  registerForActivityResultLauncher: ActivityResultLauncher<IntentSenderRequest>,
                  loginCallBack: LoginCallBack){
        val signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setServerClientId(Const.SERVER_CLIENT_ID).setFilterByAuthorizedAccounts(false)
                .build()
        ).setPasswordRequestOptions(
            BeginSignInRequest.PasswordRequestOptions.builder().setSupported(true).build()
        ).setAutoSelectEnabled(false).build()

        oneTapClient.beginSignIn(signInRequest).addOnCompleteListener {
            if (it.isSuccessful) {
                val intentSenderRequest =
                    IntentSenderRequest.Builder(it.result.pendingIntent.intentSender)
                        .setFlags(PendingIntent.FLAG_IMMUTABLE, PendingIntent.FLAG_IMMUTABLE).build()
                registerForActivityResultLauncher.launch(intentSenderRequest)
            } else if (it.isCanceled) {
                loginCallBack.onLoginFail(BaseResponse<User>(message = "Canceled login by google"))
            } else {
                loginCallBack.onLoginFail(BaseResponse<User>(message = it.exception?.message))
            }
        }
    }

    interface LoginCallBack {
        fun onLoginSuccess(user: BaseResponse<User>? = null)
        fun onLoginFail(user: BaseResponse<User>?)
    }
}