package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SchoolRepository
import com.example.model.NewsItem
import com.example.ui.components.NewsCard
import com.example.ui.components.PrayerTimesRibbon
import com.example.ui.components.SchoolTopAppBar
import com.example.ui.components.SectionTitle
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.GoldSecondary

@Composable
fun HomeScreen(
    onNavigateToAcademic: () -> Unit,
    onNavigateToIbadah: () -> Unit,
    onNavigateToPpdb: () -> Unit,
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedNews by remember { mutableStateOf<NewsItem?>(null) }

    Scaffold(
        topBar = {
            SchoolTopAppBar(
                title = "MTs Tuan Diah",
                subtitle = "Portal Resmi Madrasah Tsanawiyah"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Hero Banner & Greeting Card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(EmeraldGreenDark)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.school_hero_banner_1790057077776),
                        contentDescription = "Gedung MTs Tuan Diah",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop,
                        alpha = 0.5f
                    )

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        EmeraldGreenDark.copy(alpha = 0.95f)
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.BottomStart)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = GoldSecondary
                        ) {
                            Text(
                                text = "Akreditasi A • Unggul",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Selamat Datang di MTs Tuan Diah",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Membentuk Generasi Qur'ani, Berakhlakul Karimah & Berprestasi",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        )
                    }
                }
            }

            // Prayer Times Ribbon
            item {
                PrayerTimesRibbon(
                    prayerTimes = SchoolRepository.prayerTimes,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Quick Stats Row
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickStatCard("350+", "Santri Aktif", Icons.Default.Groups, Modifier.weight(1f))
                    QuickStatCard("28", "Guru & Staff", Icons.Default.School, Modifier.weight(1f))
                    QuickStatCard("10+", "Ekstrakurikuler", Icons.Default.EmojiEvents, Modifier.weight(1f))
                }
            }

            // Quick Menu Actions Grid
            item {
                Spacer(modifier = Modifier.height(20.dp))
                SectionTitle(
                    title = "Layanan & Fitur Madrasah",
                    subtitle = "Akses cepat informasi kegiatan madrasah"
                )
                Spacer(modifier = Modifier.height(8.dp))
                QuickActionGrid(
                    onAcademicClick = onNavigateToAcademic,
                    onIbadahClick = onNavigateToIbadah,
                    onPpdbClick = onNavigateToPpdb,
                    onProfileClick = onNavigateToProfile
                )
            }

            // PPDB Banner Promotion
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable { onNavigateToPpdb() }
                        .testTag("home_ppdb_banner"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(EmeraldGreenPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.HowToReg,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "PPDB 2026/2027 Dibuka!",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                            Text(
                                text = "Daftarkan putra/putri Anda secara online untuk Program Reguler & Tahfidz.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                )
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = EmeraldGreenPrimary
                        )
                    }
                }
            }

            // Latest News & Announcements
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionTitle(
                    title = "Berita & Pengumuman",
                    subtitle = "Informasi terbaru seputar kegiatan madrasah"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(SchoolRepository.newsList) { news ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    NewsCard(
                        news = news,
                        onClick = { selectedNews = news }
                    )
                }
            }
        }
    }

    // News Detail Modal Dialog
    if (selectedNews != null) {
        val news = selectedNews!!
        AlertDialog(
            onDismissRequest = { selectedNews = null },
            confirmButton = {
                TextButton(onClick = { selectedNews = null }) {
                    Text("Tutup", fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Column {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = news.category,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = news.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "${news.date} • ${news.author}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            },
            text = {
                Text(
                    text = news.content,
                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp)
                )
            }
        )
    }
}

@Composable
fun QuickStatCard(
    value: String,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = EmeraldGreenPrimary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun QuickActionGrid(
    onAcademicClick: () -> Unit,
    onIbadahClick: () -> Unit,
    onPpdbClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val actions = listOf(
        QuickActionItem("Jadwal Pelajaran", Icons.Default.CalendarMonth, EmeraldGreenPrimary, onAcademicClick),
        QuickActionItem("Amalan & Ibadah", Icons.Default.Mosque, GoldSecondary, onIbadahClick),
        QuickActionItem("PPDB Santri", Icons.Default.PersonAdd, EmeraldGreenPrimary, onPpdbClick),
        QuickActionItem("Profil & Guru", Icons.Default.AccountBalance, AccentTeal, onProfileClick)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        actions.forEach { item ->
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { item.onClick() }
                    .testTag("quick_action_${item.title.lowercase().replace(" ", "_")}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp, horizontal = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(item.color.copy(alpha = 0.14f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = item.color,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp
                        ),
                        maxLines = 2,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

private val AccentTeal = Color(0xFF1E8278)

data class QuickActionItem(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)
