package com.rainads.rainadsapp.util

object Handler {
    fun getErrorMessage(errorCode: Int): String {
        return when (errorCode) {
            MyConstants.INVALID_CREDENTIALS -> "Invalid Credentials"
            MyConstants.EMPTY_EMAIL_ERROR -> "Please enter an email"
            MyConstants.EMPTY_PASSWORD_ERROR -> "Please enter password"
            MyConstants.EMPTY_AD_URL -> "Please enter ad URL"
            MyConstants.EMPTY_AD_PRICE -> "Please enter ad price"
            MyConstants.EMPTY_AD_DESCRIPTION -> "Please enter ad description"
            MyConstants.EMPTY_AD_COUNTRIES -> "Please choose at least one country"
            MyConstants.CONFIRM_PASSWORD_ERROR -> "Confirm password does not match"
            MyConstants.NETWORK_FAILURE -> "Check internet connection"
            MyConstants.ERROR_CHANGE_AD_STATUS -> "Error while changing ad status"
            else -> "Unknown Error $errorCode"
        }
    }
}