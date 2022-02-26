package com.fyp.sahayogapp.dashboard.model

import androidx.activity.result.contract.ActivityResultContracts

data class UserLogin(
    val email: String,
    val password : String

)


data class ChangePassword(
    val email:String,
    val old_password:String,
    val new_password:String
)

data class APIResponse(
    val success : String,
    val code : String,
    val message : String
)

data class LoginResponse(
    val success : String,
    val code : String,
    val message : String,
    val data: LoginData
)



data class DonorInfoResponse(
    val success : String,
    val code : String,
    val message : String,
    val data: DonorData
)


data class LoginData(
     val token : String,
     val user_id: String,
     val user_name : String,
     val user_email: String,
     val user_role: String

)

data class DonorData(
     val email: String,
     val name: String,
     val phone: String,
     val blood_group: String,
     val sex: String,
     val last_donated: String

)


data class  VenueData(
   val venue_id : String,
    val venue_name: String,
val venue_contact: String,
val work_day : String,
val open_time : String,
val close_time : String,
val latitude : String,
val longitude : String
)

