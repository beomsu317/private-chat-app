@file:OptIn(ExperimentalComposeUiApi::class)

package com.beomsu317.privatechatapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beomsu317.privatechatapp.R
import com.beomsu317.privatechatapp.presentation.common.Emojis
import com.beomsu317.privatechatapp.presentation.common.OneTimeEvent
import com.beomsu317.privatechatapp.presentation.startup.sign_up.SignUpEvent
import com.beomsu317.privatechatapp.presentation.startup.sign_up.SignUpViewModel

@Composable
fun SignUpScreen(
    showSnackbar: (String, SnackbarDuration) -> Unit,
    onSignedUp: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val oneTimeEventFlow = viewModel.oneTimeEventFlow

    LaunchedEffect(key1 = oneTimeEventFlow) {
        oneTimeEventFlow.collect { oneTimeEvent ->
            when (oneTimeEvent) {
                is OneTimeEvent.ShowSnackbar -> {
                    showSnackbar(oneTimeEvent.message, SnackbarDuration.Short)
                }
                is OneTimeEvent.SignedUp -> {
                    onSignedUp()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 30.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            GreetingSection()
            Spacer(modifier = Modifier.height(40.dp))
            SignUpSection(
                onSignUp = { displayName, email, password, confirmPassword ->
                    viewModel.onEvent(
                        SignUpEvent.SignUp(
                            displayName = displayName.trim(),
                            email = email.trim(),
                            password = password.trim(),
                            confirmPassword = confirmPassword.trim()
                        )
                    )
                }
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun GreetingSection() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Welcome to Private Chat ${Emojis.wavingHandSign}",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Hello, I guess you are new around here.\nYou can start using the application after sign up.",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.body1,
            color = Color.Gray,
            lineHeight = 25.sp
        )
    }
}

@Composable
fun SignUpSection(
    onSignUp: (String, String, String, String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var displayName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = displayName,
            onValueChange = {
                displayName = it.replace("\n", "")
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "username",
                    tint = MaterialTheme.colors.primary,
                )
            },
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text("Username")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it.replace("\n", "")
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "email",
                    tint = MaterialTheme.colors.primary,
                )
            },
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text(text = "Email Address")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it.replace("\n", "")
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "lock",
                    tint = MaterialTheme.colors.primary,
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) {
                                R.drawable.ic_baseline_visibility_24
                            } else {
                                R.drawable.ic_baseline_visibility_off_24
                            }
                        ),
                        contentDescription = "password visibility"
                    )
                }
            },
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text(text = "Password")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it.replace("\n", "")
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "lock",
                    tint = MaterialTheme.colors.primary,
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) {
                                R.drawable.ic_baseline_visibility_24
                            } else {
                                R.drawable.ic_baseline_visibility_off_24
                            }
                        ),
                        contentDescription = "password visibility"
                    )
                }
            },
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text(text = "Confirm Password")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSignUp(displayName, email, password, confirmPassword)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sign Up",
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}

fun OneTimeEvent.asd() {

}