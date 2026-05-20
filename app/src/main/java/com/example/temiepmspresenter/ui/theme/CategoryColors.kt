package com.example.temiepmspresenter.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.temiepmspresenter.data.CourseCategory

fun categoryColor(category: CourseCategory): Color = when (category) {
    CourseCategory.BEAUTY -> BeautyColor
    CourseCategory.HEALTH_EDUCATION -> HealthEducationColor
    CourseCategory.HOSPITALITY -> HospitalityColor
    CourseCategory.COMMUNICATION -> CommunicationColor
    CourseCategory.SPORTS -> SportsColor
    CourseCategory.TECHNOLOGY -> TechnologyColor
}
