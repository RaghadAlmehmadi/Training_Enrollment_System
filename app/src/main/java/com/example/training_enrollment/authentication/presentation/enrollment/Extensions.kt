package com.example.training_enrollment.authentication.presentation.enrollment

fun List<String>.formatEnrollmentMessage(): String {
    return if (isEmpty())
        "No enrollments found."
    else
        "Successfully enrolled in ${joinToString(", ")}"
}
