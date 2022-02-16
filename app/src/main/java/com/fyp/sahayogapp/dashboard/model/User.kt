package com.fyp.sahayogapp.dashboard.model

data class UserLogin(
    val email: String,
    val password : String

)

data class APIResponse(
    val success : String,
    val code : String,
    val message : String,
    val data : LoginResponse

)

data class LoginResponse(
     val token : String,
     val user_name : String,
     val user_email: String,
     val user_role: String

)