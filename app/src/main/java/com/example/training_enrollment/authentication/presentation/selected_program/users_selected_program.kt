package com.example.training_enrollment.authentication.presentation.enrollment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.training_enrollment.authentication.presentation.vm.TrainingViewModel

@Composable
fun UsersSelectedProgram(navController: NavController, programName: String, viewModel: TrainingViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    val uiState by viewModel.uiState.collectAsState()

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Enroll in $programName", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Your Name", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = if (it.isBlank()) "Name cannot be empty" else null
            },
            textStyle = TextStyle(fontSize = 18.sp),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            isError = nameError != null
        )
        if (nameError != null) {
            Text(text = nameError!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Your Email", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = if (!isValidEmail(it)) "Invalid email format" else null
            },
            textStyle = TextStyle(fontSize = 18.sp),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            isError = emailError != null
        )
        if (emailError != null) {
            Text(text = emailError!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.processIntent(EnrollmentIntent.Enroll(name, email, programName))

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enroll")
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (uiState.enrollmentState) {
            is EnrollmentState.Loading -> CircularProgressIndicator()
            is EnrollmentState.Success -> {
                val enrolledPrograms = (uiState.enrollmentState as EnrollmentState.Success).enrolledPrograms
                Text(enrolledPrograms.formatEnrollmentMessage())
            }
            is EnrollmentState.Error -> Text(
                text = (uiState.enrollmentState as EnrollmentState.Error).error,
                color = MaterialTheme.colorScheme.error
            )
            else -> {}
        }


        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {  viewModel.resetEnrollmentState()
                navController.navigate("ListTrainingPrograms") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cancel")
        }
    }
}
