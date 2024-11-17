package com.example.idiotchefassistant.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.idiotchefassistant.AuthTokenManager
import com.example.idiotchefassistant.data.retrofit.endpoints.LoginRequestBody
import com.example.idiotchefassistant.data.retrofit.endpoints.LoginResponse
import com.example.idiotchefassistant.data.retrofit.endpoints.MessageResponse
import com.example.idiotchefassistant.userService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val authTokenManager: AuthTokenManager) {
    val data = MutableLiveData<LoginResponse>()
    private var scope: CoroutineScope? = null

    var user: String? = null
        private set
    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun login(username: String, password: String) {
        val body = LoginRequestBody(username, password)
        userService.login(body).enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        data.postValue(response.body())
                        response.body()?.let { responseBody ->
                            scope?.launch {
                                authTokenManager.saveAuthToken(responseBody.token)
                            }
                        }
                    } else
                        Log.i(
                            "Login Service",
                            "API Failed:${response.code()} ${response.message()}"
                        )
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Login Service", "API call failed: ${t.message}")
                }
            }
        )
    }

    fun logout() {
        userService.logout(data.value!!.token).enqueue(
            object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            scope?.launch {
                                authTokenManager.clearAuthToken()
                            }
                        }
                    } else {
                        Log.i(
                            "Login Service",
                            "API Failed:${response.code()} ${response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.e("Login Service", "API call failed: ${t.message}")
                }
            }
        )
    }

    fun setScope(scope: CoroutineScope) {
        this.scope = scope
    }

    private fun setLoggedInUser(uid: String) {
        this.user = uid
    }

}