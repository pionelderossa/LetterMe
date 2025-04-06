package com.kk.letterme.data.dto

data class
UserInputForm (

    var username: String = "",
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val emailError: String? = null,

)