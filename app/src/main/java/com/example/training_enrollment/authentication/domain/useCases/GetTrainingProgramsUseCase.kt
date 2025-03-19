package com.example.training_enrollment.authentication.domain.useCases

class GetTrainingProgramsUseCase {
    fun execute(): List<String> {
        return listOf(
            "Nursing Fundamentals",
            "Medical Billing & Coding",
            "Healthcare Administration",
            "Pharmaceutical Studies",
            "Public Health & Safety",
            "Emergency Medical Services"
        )
    }
}
