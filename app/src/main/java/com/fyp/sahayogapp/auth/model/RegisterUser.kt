package com.fyp.sahayogapp.auth.model

data class RegisterUser(
 val name:String,
 val email:String,
 val password:String,
 val phoneNumber:String,
 val user_role:String,
 val sex:String,
 val blood_group:String,
 val registration_number:String,
 val landline_number:String,
 val work_day:String,
 val open_time:String,
 val close_time:String,
 val latitude:String,
 val longitude:String,
 val fcm_token: String?,
)
