package com.beomsu317.privatechatapp.presentation.startup.sign_in

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.beomsu317.privatechatapp.presentation.common.Emojis

@Composable
fun SignInScreen(
    onSignInButtonClick: () -> Unit,
    onSignUpButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 30.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        GreetingSection()
        Spacer(modifier = Modifier.height(40.dp))
        AccountSection()
        Spacer(modifier = Modifier.height(20.dp))
        SignInOrSignUpSection(
            onSignInButtonClick = onSignInButtonClick,
            onSignUpButtonClick = onSignUpButtonClick
        )
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
fun AccountSection() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            }
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
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text(text = "Password")
            }
        )
    }
}

@Composable
fun SignInOrSignUpSection(
    onSignInButtonClick: () -> Unit,
    onSignUpButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                onSignInButtonClick()
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
