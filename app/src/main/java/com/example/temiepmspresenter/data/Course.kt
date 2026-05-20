package com.example.temiepmspresenter.data

data class CurriculumComponent(
    val name: String,
    val hours: Int,
    val subjects: List<String>
)

enum class CourseCategory {
    BEAUTY, HEALTH_EDUCATION, HOSPITALITY, COMMUNICATION, SPORTS, TECHNOLOGY
}

data class Course(
    val id: String,
    val name: String,
    val fullName: String = name,
    val shortDescription: String,
    val fullDescription: String,
    val level: String = "Nível 4 (QNQ/QEQ)",
    val equivalency: String = "Equivalente ao 12.º ano",
    val totalHours: String? = null,
    val components: List<CurriculumComponent> = emptyList(),
    val competencies: List<String> = emptyList(),
    val careerPaths: List<String> = emptyList(),
    val icon: String,
    val category: CourseCategory
)
