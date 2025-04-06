package com.kk.letterme.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kk.letterme.ui.viewModel.AuthViewModel


@Composable
fun LabeledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordField: Boolean = false,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (isPasswordField) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}




@Composable
fun RegistrationScreen(
    authViewModel: AuthViewModel = viewModel()
) {
    val userinputform by authViewModel.userinputform.collectAsState()
    val registerResult by authViewModel.registerResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LabeledTextField(
            value = userinputform.username,
            onValueChange = { authViewModel.onFieldChange("username", it) },
            label = "Username",
            errorMessage = userinputform.usernameError
        )
        Spacer(modifier = Modifier.height(8.dp))
        LabeledTextField(
            value = userinputform.email,
            onValueChange = { authViewModel.onFieldChange("email", it) },
            label = "Email",
            errorMessage = userinputform.emailError,
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(8.dp))
        LabeledTextField(
            value = userinputform.password,
            onValueChange = { authViewModel.onFieldChange("password", it) },
            label = "Password",
            errorMessage = userinputform.passwordError,
            keyboardType = KeyboardType.Password,
//            isPasswordField = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        LabeledTextField(
            value = userinputform.confirmPassword,
            onValueChange = { authViewModel.onFieldChange("confirm password", it) },
            label = "Confirm Password",
            errorMessage = userinputform.confirmPasswordError,
            keyboardType = KeyboardType.Password,
//            isPasswordField = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { authViewModel.registerUser() }) {
            Text("Register")
        }
    }


    if (registerResult != null) {
        AlertDialog(
            onDismissRequest = { authViewModel.resetRegisterResult() },
            title = {
                if (registerResult!!.success) {
                    Text("Success")
                } else {
                    Text("Error")
                }
            },
            text = {
                if (registerResult!!.success) {
                    Text(registerResult!!.successMessage ?: "Registration successful!")
                } else {
                    Text(registerResult!!.errorMessage ?: "An unknown error occurred.")
                }
            },
            confirmButton = {
                Button(onClick = { authViewModel.resetRegisterResult() }) {
                    Text("OK")
                }
            }
        )
    }

    //Side effect
    LaunchedEffect(key1 = registerResult) {
        if (registerResult != null) {
            println(registerResult!!.errorMessage ?: "no error message")
            println(registerResult!!.successMessage ?: "no success message")
        }
    }
}






@Composable

fun RegisterUser (modifier: Modifier = Modifier, authviewmodel: AuthViewModel = viewModel()){

    val formState by authviewmodel.userinputform.collectAsState()
    Surface (modifier = modifier
        .fillMaxSize()
        .padding(all = 10.dp)) {

        Column (modifier = Modifier
            .fillMaxSize()
            .padding(all = 5.dp)) {

            Text("Registration Form", modifier = Modifier.padding(all = 5.dp))
            TextField(value = formState.username, onValueChange = {authviewmodel.onFieldChange("username", it)}, label = { Text("Username") })
            TextField(value = formState.email, onValueChange = {authviewmodel.onFieldChange("email", it)}, label = { Text("Email") })
            TextField(value = formState.password, onValueChange = {authviewmodel.onFieldChange("password", it)}, label = { Text("Password") })
            TextField(value = formState.confirmPassword, onValueChange = {authviewmodel.onFieldChange("confirm_password", it)}, label = { Text("Confirm Password") })

            Button(onClick = {}, modifier = Modifier.padding(all = 5.dp)) {
                Text("Submit")
            }
        }
    }


}

