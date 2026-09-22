package com.example.model

data class NewsItem(
    val id: String,
    val title: String,
    val category: String,
    val date: String,
    val summary: String,
    val content: String,
    val author: String = "Humas MTs Tuan Diah"
)

data class PrayerTime(
    val name: String,
    val arabicName: String,
    val time: String,
    val isNext: Boolean = false
)

data class ScheduleItem(
    val id: String,
    val day: String,
    val className: String,
    val subject: String,
    val teacher: String,
    val timeRange: String,
    val room: String
)

data class SubjectItem(
    val id: String,
    val name: String,
    val code: String,
    val category: String, // "Agama Islam" / "Mata Pelajaran Umum"
    val teacherName: String,
    val kkm: Int,
    val description: String
)

data class AcademicEvent(
    val id: String,
    val title: String,
    val dateRange: String,
    val month: String,
    val type: String, // "Ujian", "Libur", "Akademik", "Peringatan"
    val description: String
)

data class Teacher(
    val id: String,
    val name: String,
    val role: String,
    val subject: String,
    val education: String
)

data class Extracurricular(
    val id: String,
    val name: String,
    val iconName: String,
    val schedule: String,
    val mentor: String,
    val description: String
)

data class DailyHabit(
    val id: String,
    val title: String,
    val category: String, // "Sholat Fardhu", "Sholat Sunnah", "Al-Qur'an", "Adab & Dzikir"
    val points: Int,
    val isDone: Boolean = false
)

data class DailyPrayer(
    val id: String,
    val title: String,
    val arabic: String,
    val latin: String,
    val translation: String,
    val occasion: String
)

data class PpdbRegistration(
    val regNumber: String,
    val studentName: String,
    val nisn: String,
    val gender: String,
    val originSchool: String,
    val parentName: String,
    val phone: String,
    val program: String,
    val timestamp: Long = System.currentTimeMillis()
)
