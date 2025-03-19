package com.example.training_enrollment.authentication.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.training_enrollment.authentication.domain.useCases.EnrollTrainingProgramUseCase
import com.example.training_enrollment.authentication.domain.useCases.GetTrainingProgramsUseCase
import com.example.training_enrollment.authentication.presentation.enrollment.EnrollmentIntent
import com.example.training_enrollment.authentication.presentation.enrollment.EnrollmentState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EnrollmentUIState(
    val programs: List<String> = emptyList(),
    val enrollmentState: EnrollmentState = EnrollmentState.Idle
)

class TrainingViewModel(
    private val getTrainingProgramsUseCase: GetTrainingProgramsUseCase,
    private val enrollTrainingProgramUseCase: EnrollTrainingProgramUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EnrollmentUIState())
    val uiState: StateFlow<EnrollmentUIState> = _uiState.asStateFlow()

    init {
        fetchTrainingPrograms()
    }
    fun processIntent(intent: EnrollmentIntent) {
        when (intent) {
            is EnrollmentIntent.Enroll -> enroll(intent.name, intent.email, intent.programName)
        }
    }

    private fun fetchTrainingPrograms() {
        viewModelScope.launch {
            val programs = getTrainingProgramsUseCase.execute()
            _uiState.value = _uiState.value.copy(programs = programs)
        }
    }

    fun enroll(name: String, email: String, programName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(enrollmentState = EnrollmentState.Loading)
            delay(1500)
            try {
                val result = enrollTrainingProgramUseCase.execute(name, email, programName)
                _uiState.value = _uiState.value.copy(
                    enrollmentState = EnrollmentState.Success(listOf(result))
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    enrollmentState = EnrollmentState.Error(e.message ?: "Failed to enroll")
                )
            }
        }
    }
    fun resetEnrollmentState() {
        _uiState.value = _uiState.value.copy(enrollmentState = EnrollmentState.Idle)
    }

}
