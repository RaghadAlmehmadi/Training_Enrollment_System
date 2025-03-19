package com.example.training_enrollment.authentication.domain.useCases



class EnrollTrainingProgramUseCase {
     fun execute(name: String, email: String, programName: String): String {
        if (name.isBlank() || email.isBlank()) {
            throw IllegalArgumentException("Name and Email cannot be empty.")
        }
        if (!isValidEmail(email)) {
            throw IllegalArgumentException("Invalid email format.")
        }

        return " $programName"
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

