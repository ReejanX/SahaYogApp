package com.fyp.sahayogapp.api

object API_URL {

    const val BASE_URL = "http://10.0.2.2:4444/"
    const val GET_DONATION_LIST = "donor/getAllRequestList"
    const val USER_LOGIN = "auth/login"
    const val USER_REGISTER = "auth/register"
    const val SEND_OTP = "auth/send-reset-otp"
    const val OTP_VERIFY = "auth/otp-check"
    const val RESET_PASSWORD = "auth/reset-password"
    const val CHANGE_PASSWORD = "profile/change-password"
    const val DONOR_DETAILS = "user/getDonorDetails"
    const val HOSPITAL_DETAILS = "user/get-hospital-details"
    const val POST_REQUEST = "donor/post-donation-request"
    const val ACCEPT_REQUEST = "donor/accept-donation-request"
    const val GET_VENUE = "donor/get-venues"
    const val FCM_UPDATE = "donor/update-fcm-token"
    const val GET_MY_ACCEPTED_DONATIONS = "donor/my-accepted-history"
    const val GET_MY_REQUESTS = "donor/my-requests"
    const val GET_NOTIFICATIONS = "user/get-notifications"

}