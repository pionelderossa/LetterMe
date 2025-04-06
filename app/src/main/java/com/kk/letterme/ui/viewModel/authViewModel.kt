package com.kk.letterme.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kk.letterme.data.dto.UserInputForm
import com.kk.letterme.data.repository.UserInputRepository
import com.kk.letterme.utils.UserInputValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterResult(val success: Boolean, val errorMessage: String? = null, val successMessage: String? = null)


class AuthViewModel(private val userInputValidator: UserInputValidator =  UserInputValidator(),
    private val userInputRepository: UserInputRepository = UserInputRepository()
) : ViewModel()  {

    private val _userinputform = MutableStateFlow(UserInputForm())
    val userinputform: StateFlow<UserInputForm> = _userinputform.asStateFlow()


    private val _registerResult = MutableStateFlow<RegisterResult?>(null)
    val registerResult: StateFlow<RegisterResult?> = _registerResult.asStateFlow()


     fun registerUser() {

        val isUsernameValid = userInputValidator.isUsernameValid(_userinputform.value.username)
        val isEmailValid = userInputValidator.isEmailValid(_userinputform.value.email)
        val isPasswordValid = userInputValidator.isPasswordValid(_userinputform.value.password)

        if (!isUsernameValid) {_userinputform.value = _userinputform.value.copy(usernameError = "Invalid username")}
         else _userinputform.value = _userinputform.value.copy(usernameError = "Valid username")
        if (!isEmailValid) {_userinputform.value = _userinputform.value.copy(emailError = "Invalid email")}
         else _userinputform.value = _userinputform.value.copy(emailError = "Valid email")
        if (!isPasswordValid) {_userinputform.value = _userinputform.value.copy(passwordError = "Invalid password")}
         else _userinputform.value = _userinputform.value.copy(passwordError = "Valid password")

        if (isUsernameValid && isEmailValid && isPasswordValid) {
            if (_userinputform.value.password != _userinputform.value.confirmPassword) {
                _userinputform.value = _userinputform.value.copy(confirmPasswordError = "Password mismatch")
            }
            else {

                //create a co-routine to add the user to the database
                _userinputform.value = _userinputform.value.copy(confirmPasswordError = "Password matched")

                viewModelScope.launch(Dispatchers.IO) {

                    val result = userInputRepository.isUsernameAvailable(_userinputform.value.username)
                    if (result.isSuccess) {
                        if (!result.getOrThrow( )) {
                            _userinputform.value =
                                _userinputform.value.copy(usernameError = "Username already taken")
                        } else {
                            val submitResult: Result<String> =
                                userInputRepository.setUserByCredentials(_userinputform.value)

                            if (submitResult.isSuccess) {
                                _registerResult.value = RegisterResult(
                                    success = true,
                                    successMessage = submitResult.getOrThrow())
                            } else {
                                _registerResult.value = RegisterResult(
                                    success = false,
                                    errorMessage = submitResult.exceptionOrNull()?.message
                                )
                            }

                        }
                    } else {
                            // handle exception
                            println("an error occurred checking for username availabilty: ${result.exceptionOrNull()}")

                    }

                    }
                }
            }
        }




    fun onFieldChange(field: String, value: String) {
        _userinputform.value = when (field) {
            "username" -> _userinputform.value.copy(username = value)
            "email" -> _userinputform.value.copy(email = value)
            "password" -> _userinputform.value.copy(
                password = value)
            "confirm password" -> _userinputform.value.copy(
                confirmPassword = value,
            )

            else -> _userinputform.value
        }
    }

    fun resetRegisterResult() {
        _registerResult.value = null
    }
    
    

}