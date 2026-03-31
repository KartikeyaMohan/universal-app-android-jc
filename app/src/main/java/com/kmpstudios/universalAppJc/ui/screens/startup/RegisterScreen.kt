package com.kmpstudios.universalAppJc.ui.screens.startup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kmpstudios.universalAppJc.ui.navigation.Home
import com.kmpstudios.universalAppJc.ui.navigation.LocalOnBack
import com.kmpstudios.universalAppJc.ui.navigation.LocalRootNavigator
import com.kmpstudios.universalAppJc.ui.theme.AppTheme
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.ui.viewmodels.RegisterViewModel
import com.kmpstudios.universalAppJc.ui.views.GlassCard

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = hiltViewModel()
) {

    val colors = AppTheme.colors
    val onBack = LocalOnBack.current
    val rootNavigator = LocalRootNavigator.current

    var email by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val registerResponse by registerViewModel.registerResponse.collectAsStateWithLifecycle()

    LaunchedEffect(registerResponse) {
        registerResponse?.let {
            if (it.tokens?.authToken.isNullOrEmpty().not() &&
                it.tokens.refreshToken.isNullOrEmpty().not()) {
                rootNavigator(Home)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.backgroundPrimary)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlassCard(
                modifier = Modifier
                    .padding(horizontal = 15.dp),
                color = colors.brandAccent
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(15.dp)
                ) {
                    Text(
                        text = "Sign up to get started",
                        fontSize = 24.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = colors.textPrimary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "Name") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics { testTag = TestTags.REGISTER_NAME },
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(text = "Email") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics { testTag = TestTags.REGISTER_EMAIL },
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(text = "Password") },
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics { testTag = TestTags.REGISTER_PASSWORD },
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text(text = "Password") },
                        singleLine = true,
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            onDone = {
                                focusManager.clearFocus()
                                registerViewModel.register(name, email, password)
                            }
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                confirmPasswordVisible = !confirmPasswordVisible
                            }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics { testTag = TestTags.REGISTER_CONFIRM_PASSWORD },
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {
                            if (password == confirmPassword) {
                                registerViewModel.register(
                                    name, email, password
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .semantics { testTag = TestTags.REGISTER_SUBMIT },
                        enabled = name.isNotBlank() && email.isNotBlank() &&
                                password.isNotBlank() && confirmPassword.isNotBlank() &&
                                password == confirmPassword
                    ) {
                        Text(
                            text = "Register",
                            fontSize = 24.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    TextButton(
                        onClick = { onBack() }
                    ) {
                        Text(
                            text = "Already have an account? Sign In",
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}