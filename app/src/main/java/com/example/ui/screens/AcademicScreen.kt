package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SchoolRepository
import com.example.model.AcademicEvent
import com.example.model.ScheduleItem
import com.example.model.SubjectItem
import com.example.ui.components.SchoolTopAppBar
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.GoldSecondary

@Composable
fun AcademicScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Jadwal Pelajaran", "Mata Pelajaran", "Kalender Akademik")

    Scaffold(
        topBar = {
            SchoolTopAppBar(
                title = "Akademik Madrasah",
                subtitle = "Tahun Ajaran 2026/2027 • Semester Ganjil"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Tab Header
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        }
                    )
                }
            }

            when (selectedTab) {
                0 -> ScheduleView()
                1 -> SubjectsView()
                2 -> CalendarView()
            }
        }
    }
}

@Composable
fun ScheduleView() {
    val days = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")
    var selectedDay by remember { mutableStateOf("Senin") }
    val classes = listOf("Kelas VII-A", "Kelas VII-B", "Kelas VIII-A", "Kelas IX-A")
    var selectedClass by remember { mutableStateOf("Kelas VII-A") }

    val filteredSchedule = remember(selectedDay, selectedClass) {
        SchoolRepository.sampleSchedules.filter { it.day.equals(selectedDay, ignoreCase = true) }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Class Selector Chip
        item {
            Text(
                text = "Pilih Kelas:",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(classes) { cls ->
                    FilterChip(
                        selected = selectedClass == cls,
                        onClick = { selectedClass = cls },
                        label = { Text(cls) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Day Selector Chip
        item {
            Text(
                text = "Pilih Hari:",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(days) { day ->
                    FilterChip(
                        selected = selectedDay == day,
                        onClick = { selectedDay = day },
                        label = { Text(day) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldGreenPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Schedule List Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jadwal $selectedDay ($selectedClass)",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "${filteredSchedule.size} Mata Pelajaran",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

        if (filteredSchedule.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.EventBusy,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tidak ada jadwal tatap muka",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Kegiatan santri berfokus pada pembinaan mandiri dan ekstrakurikuler.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        } else {
            items(filteredSchedule) { item ->
                ScheduleCard(item = item)
            }
        }
    }
}

@Composable
fun ScheduleCard(item: ScheduleItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("schedule_item_${item.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(EmeraldGreenPrimary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = EmeraldGreenPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.subject,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = item.teacher,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = item.timeRange,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp
                            )
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = GoldSecondary.copy(alpha = 0.16f)
                    ) {
                        Text(
                            text = item.room,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldSecondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectsView() {
    var selectedCategory by remember { mutableStateOf("Semua") }
    val categories = listOf("Semua", "Pendidikan Agama Islam", "Mata Pelajaran Umum")
    var selectedSubject by remember { mutableStateOf<SubjectItem?>(null) }

    val filteredSubjects = remember(selectedCategory) {
        if (selectedCategory == "Semua") SchoolRepository.subjects
        else SchoolRepository.subjects.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(filteredSubjects) { subj ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedSubject = subj }
                    .testTag("subject_${subj.code}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (subj.category.contains("Agama"))
                                EmeraldGreenPrimary.copy(alpha = 0.12f)
                            else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = subj.code,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (subj.category.contains("Agama"))
                                        EmeraldGreenPrimary
                                    else MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = "KKM: ${subj.kkm}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = subj.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Text(
                        text = "Pengampu: ${subj.teacherName}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = subj.description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    )
                }
            }
        }
    }

    if (selectedSubject != null) {
        val s = selectedSubject!!
        AlertDialog(
            onDismissRequest = { selectedSubject = null },
            confirmButton = {
                TextButton(onClick = { selectedSubject = null }) {
                    Text("Tutup", fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Text(
                    text = s.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Kode: ${s.code}", style = MaterialTheme.typography.bodyMedium)
                    Text("Kategori: ${s.category}", style = MaterialTheme.typography.bodyMedium)
                    Text("KKM Minimal: ${s.kkm}", style = MaterialTheme.typography.bodyMedium)
                    Text("Guru Pengajar: ${s.teacherName}", style = MaterialTheme.typography.bodyMedium)
                    HorizontalDivider()
                    Text("Deskripsi Pembelajaran:", fontWeight = FontWeight.SemiBold)
                    Text(s.description, style = MaterialTheme.typography.bodySmall)
                }
            }
        )
    }
}

@Composable
fun CalendarView() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = EmeraldGreenPrimary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Kalender Kegiatan Akademik Madrasah Tsanawiyah Semester Ganjil 2026/2027.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
        }

        items(SchoolRepository.academicCalendar) { event ->
            AcademicEventCard(event = event)
        }
    }
}

@Composable
fun AcademicEventCard(event: AcademicEvent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("event_${event.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        when (event.type) {
                            "Ujian" -> GoldSecondary.copy(alpha = 0.15f)
                            "Libur" -> Color(0xFFE57373).copy(alpha = 0.15f)
                            "Peringatan" -> EmeraldGreenPrimary.copy(alpha = 0.15f)
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (event.type) {
                        "Ujian" -> Icons.Default.EditNote
                        "Libur" -> Icons.Default.BeachAccess
                        "Peringatan" -> Icons.Default.Celebration
                        else -> Icons.Default.Event
                    },
                    contentDescription = null,
                    tint = when (event.type) {
                        "Ujian" -> GoldSecondary
                        "Libur" -> Color(0xFFD32F2F)
                        "Peringatan" -> EmeraldGreenPrimary
                        else -> MaterialTheme.colorScheme.primary
                    },
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = event.type,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.dateRange,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                )
            }
        }
    }
}
