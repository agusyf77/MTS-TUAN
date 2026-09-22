package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SchoolRepository
import com.example.model.DailyHabit
import com.example.model.DailyPrayer
import com.example.ui.components.SchoolTopAppBar
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.GoldSecondary

@Composable
fun IbadahScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Mutaba'ah Yaumiyah", "Doa Harian Santri")

    Scaffold(
        topBar = {
            SchoolTopAppBar(
                title = "Ibadah & Amalan Santri",
                subtitle = "Pembinaan Karakter Islami & Spiritualitas"
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
                0 -> MutabaahView()
                1 -> DailyPrayersView()
            }
        }
    }
}

@Composable
fun MutabaahView() {
    val habits by SchoolRepository.habitsState.collectAsState()

    val totalPoints = habits.sumOf { it.points }
    val earnedPoints = habits.filter { it.isDone }.sumOf { it.points }
    val progress = if (totalPoints > 0) earnedPoints.toFloat() / totalPoints.toFloat() else 0f
    val percentage = (progress * 100).toInt()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Summary Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mutabaah_summary_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = EmeraldGreenDark
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Pencapaian Hari Ini",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "$earnedPoints dari $totalPoints Poin Amalan",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            )
                        }

                        Surface(
                            shape = CircleShape,
                            color = GoldSecondary
                        ) {
                            Text(
                                text = "$percentage%",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = GoldSecondary,
                        trackColor = Color.White.copy(alpha = 0.2f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (percentage >= 80) "Alhamdulillah! Pertahankan istiqamah dalam beribadah."
                        else if (percentage >= 50) "Bagus! Terus tingkatkan amalan fardhu dan sunnah hari ini."
                        else "Awali hari dengan niat lillahi ta'ala. Lengkapi target ibadahmu!",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.9f),
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )
                }
            }
        }

        // Action Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Daftar Amalan Harian",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                TextButton(
                    onClick = { SchoolRepository.resetHabits() },
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Reset Harian")
                }
            }
        }

        items(habits) { habit ->
            HabitItemCard(
                habit = habit,
                onToggle = { SchoolRepository.toggleHabit(habit.id) }
            )
        }
    }
}

@Composable
fun HabitItemCard(
    habit: DailyHabit,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("habit_${habit.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (habit.isDone)
                EmeraldGreenPrimary.copy(alpha = 0.08f)
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = habit.isDone,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = EmeraldGreenPrimary,
                    checkmarkColor = Color.White
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = habit.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = if (habit.isDone) FontWeight.Bold else FontWeight.Medium,
                        color = if (habit.isDone) EmeraldGreenPrimary else MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = habit.category,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                )
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (habit.isDone) EmeraldGreenPrimary.copy(alpha = 0.15f)
                else MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = "+${habit.points} Poin",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (habit.isDone) EmeraldGreenPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}

@Composable
fun DailyPrayersView() {
    var searchQuery by remember { mutableStateOf("") }

    val filteredPrayers = remember(searchQuery) {
        if (searchQuery.isBlank()) SchoolRepository.dailyPrayers
        else SchoolRepository.dailyPrayers.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.translation.contains(searchQuery, ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("prayer_search_input"),
                placeholder = { Text("Cari doa sehari-hari...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Cari")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Hapus")
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }

        items(filteredPrayers) { prayer ->
            PrayerDetailCard(prayer = prayer)
        }
    }
}

@Composable
fun PrayerDetailCard(prayer: DailyPrayer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("prayer_${prayer.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = prayer.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = EmeraldGreenPrimary
                    )
                )
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = GoldSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Arabic text in prominent calligraphic styling
            Text(
                text = prayer.arabic,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = FontFamily.Default,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.End
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(EmeraldGreenPrimary.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = prayer.latin,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Artinya: \"${prayer.translation}\"",
                style = MaterialTheme.typography.bodySmall.copy(
                    lineHeight = 18.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = "Keutamaan: ${prayer.occasion}",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}
