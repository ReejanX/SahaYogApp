package com.fyp.sahayogapp.auth.model


data class ResetPassword(
    val email: String,
    val otp :String,
    val password:String
)