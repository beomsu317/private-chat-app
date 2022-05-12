@file:OptIn(ExperimentalComposeUiApi::class)

package com.beomsu317.privatechatapp.presentation.startup.sign_in

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beomsu317.privatechatapp.R
import com.beomsu317.privatechatapp.presentation.common.Emojis
import com.beomsu317.privatechatapp.presentation.common.OneTimeEvent
import kotlinx.coroutines.flow.collect

@Composable
fun SignInScreen(
    onSignedIn: () -> Unit,
    onSignUpButtonClick: () -> Unit,
    showSnackbar: (String, SnackbarDuration) -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val oneTimeEventFlow = viewModel.oneTimeEventFlow

    LaunchedEffect(key1 = oneTimeEventFlow) {
        oneTimeEventFlow.collect { oneTimeEvent ->
            when (oneTimeEvent) {
                is OneTimeEvent.ShowSnackbar -> {
                    showSnackbar(oneTimeEvent.message, SnackbarDuration.Short)
                }
                is OneTimeEvent.SignedIn -> {
                    onSignedIn()
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
            AccountSection(
                onSignInButtonClick = { email, password ->
                    viewModel.onEvent(SignInEvent.SignIn(email, password))
                },
                onSignUpButtonClick = onSignUpButtonClick
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
            text = "Welcome Back ${Emojis.wavingHandSign}",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "I am happy to see you again. You can\ncontinue where you left off by logging in.",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.body1,
            color = Color.Gray,
            lineHeight = 25.sp
        )
    }
}

@Composable
fun AccountSection(
    onSignInButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var email by remember { mutableStateOf("beomsu318@gmail.com") }
    var password by remember { mutableStateOf("12345678") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
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
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                onSignInButtonClick(email.trim(), password.trim())
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Sign In",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
        Text(
            text = "or",
            style = MaterialTheme.typography.body1,
            color = Color.Gray,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.body1
            )
            TextButton(
                onClick = {
                    onSignUpButtonClick()
                }
            ) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
