package com.example.training_enrollment.authentication.presentation.enrollment

sealed class EnrollmentState {
    object Idle : EnrollmentState()
    object Loading : EnrollmentState()
    data class Success(val enrolledPrograms: List<String>) : EnrollmentState()
    data class Error(val error: String) : EnrollmentState()
}