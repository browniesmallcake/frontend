package com.example.idiotchefassistant.data.retrofit

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class LoginResponse(
    val token:String
)

data class LoginRequestBody(
    val username:String,
    val password:String
)

data class RegisterRequestBody(
    val username:String,
    val password:String,
    val email: String
)

data class UserResponse(
    val id:Int,
    val username:String,
    val email:String,
    val level:Int
)


// A generic class that contains message of a **successful** operation.
data class MessageResponse(
    val message:String
)

data class SearchResponse(
    val id:Int,
    val name:String,
    val description:String,
    @SerializedName("video_link")  val videoLink:String,
    val rtype:Int
)

interface User {

    @POST("/user/login")
    fun login(
        @Body loginRequestBody: LoginRequestBody
    ): Call<LoginResponse>

    @GET("/user/me")
    fun me(
        @Header("X-API-Key") token:String
    ): Call<UserResponse>

    @GET("/user/logout")
    fun logout(
        @Header("X-API-Key") token:String
    ): Call<MessageResponse>

    @GET("/user/logout/all")
    fun logoutAll(
        @Header("X-API-Key") token:String
    ): Call<MessageResponse>

    @POST("/user/register")
    fun register(
        @Body loginRequestBody: RegisterRequestBody
    ): Call<MessageResponse>

    @GET("/user/searches")
    fun searches(
        @Header("X-API-Key") token:String
    ): Call<MessageResponse>

}