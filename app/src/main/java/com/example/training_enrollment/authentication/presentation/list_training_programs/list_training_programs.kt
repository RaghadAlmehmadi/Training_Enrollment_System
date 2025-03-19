package com.example.training_enrollment.authentication.presentation.list_training_programs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.training_enrollment.authentication.presentation.vm.TrainingViewModel


@Composable
fun ListTrainingPrograms(viewModel: TrainingViewModel, navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "List of training programs available:",
            modifier = Modifier.padding(16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
          )
        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.programs.isEmpty()) {
            Text(text = "No training programs available",
                style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn {
                items(uiState.programs) { program ->
                    TrainingProgramItem(program) {
                        navController.navigate("UsersSelectedProgram/$program")
                    }
                }
            }
        }
    }
}


@Composable
fun TrainingProgramItem(program: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = program,
            modifier = Modifier.padding(16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
