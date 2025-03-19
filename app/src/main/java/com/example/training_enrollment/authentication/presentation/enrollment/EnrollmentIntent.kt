package com.example.training_enrollment.authentication.presentation.enrollment

sealed class EnrollmentIntent {
    data class Enroll(
        val name: String,
        val email: String,
        val programName: String
    ) : EnrollmentIntent()
}
