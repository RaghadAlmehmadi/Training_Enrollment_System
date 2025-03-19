package com.example.training_enrollment.navigation


import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.training_enrollment.authentication.domain.useCases.EnrollTrainingProgramUseCase
import com.example.training_enrollment.authentication.domain.useCases.GetTrainingProgramsUseCase
import com.example.training_enrollment.authentication.presentation.enrollment.UsersSelectedProgram
import com.example.training_enrollment.authentication.presentation.list_training_programs.ListTrainingPrograms
import com.example.training_enrollment.authentication.presentation.vm.TrainingViewModel

@Composable
fun Navapp(navController: NavHostController) {
    val viewModel = remember {
        TrainingViewModel(
            getTrainingProgramsUseCase = GetTrainingProgramsUseCase(),
            enrollTrainingProgramUseCase = EnrollTrainingProgramUseCase()
        )
    }

    NavHost(navController, startDestination = "ListTrainingPrograms") {
        composable("ListTrainingPrograms") {
            ListTrainingPrograms(viewModel, navController)
        }
        composable("UsersSelectedProgram/{programName}") { backStackEntry ->
            val programName = backStackEntry.arguments?.getString("programName") ?: "Unknown Program"
            UsersSelectedProgram(navController, programName, viewModel)
        }
    }
}
