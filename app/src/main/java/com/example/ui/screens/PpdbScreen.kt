package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SchoolRepository
import com.example.model.PpdbRegistration
import com.example.ui.components.SchoolTopAppBar
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.GoldSecondary
import kotlin.random.Random

@Composable
fun PpdbScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Form Pendaftaran", "Syarat & Alur", "Data Pendaftar")

    Scaffold(
        topBar = {
            SchoolTopAppBar(
                title = "PPDB Online 2026/2027",
                subtitle = "Penerimaan Peserta Didik Baru MTs Tuan Diah"
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
                0 -> PpdbFormView(onRegistrationSuccess = { selectedTab = 2 })
                1 -> PpdbInfoView(onRegisterNow = { selectedTab = 0 })
                2 -> PpdbDataView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PpdbFormView(
    onRegistrationSuccess: () -> Unit
) {
    var studentName by remember { mutableStateOf("") }
    var nisn by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Laki-laki") }
    var originSchool by remember { mutableStateOf("") }
    var parentName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var program by remember { mutableStateOf("Kelas Tahfidz Al-Qur'an") }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var registeredData by remember { mutableStateOf<PpdbRegistration?>(null) }

    val programOptions = listOf("Kelas Tahfidz Al-Qur'an", "Program Reguler Mandiri")
    val genderOptions = listOf("Laki-laki", "Perempuan")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = EmeraldGreenDark
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Formulir Pendaftaran Calon Santri",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Isi data dengan lengkap dan benar sesuai dokumen kartu keluarga & akta kelahiran.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    )
                }
            }
        }

        if (errorMessage != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = errorMessage!!,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }

        item {
            OutlinedTextField(
                value = studentName,
                onValueChange = {
                    studentName = it
                    errorMessage = null
                },
                label = { Text("Nama Lengkap Calon Santri *") },
                placeholder = { Text("Contoh: Muhammad Azzam") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ppdb_input_name"),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = nisn,
                onValueChange = {
                    if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                        nisn = it
                        errorMessage = null
                    }
                },
                label = { Text("NISN (Nomor Induk Siswa Nasional) *") },
                placeholder = { Text("10 digit angka") },
                leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ppdb_input_nisn"),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }

        item {
            Text(
                text = "Jenis Kelamin *",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                genderOptions.forEach { opt ->
                    FilterChip(
                        selected = gender == opt,
                        onClick = { gender = opt },
                        label = { Text(opt) },
                        modifier = Modifier.testTag("gender_${opt.lowercase()}")
                    )
                }
            }
        }

        item {
            OutlinedTextField(
                value = originSchool,
                onValueChange = {
                    originSchool = it
                    errorMessage = null
                },
                label = { Text("Asal Sekolah (SD / MI) *") },
                placeholder = { Text("Contoh: MI Al-Ikhlas / SDN 02") },
                leadingIcon = { Icon(Icons.Default.School, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ppdb_input_school"),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = parentName,
                onValueChange = {
                    parentName = it
                    errorMessage = null
                },
                label = { Text("Nama Orang Tua / Wali *") },
                placeholder = { Text("Contoh: Ahmad Yani") },
                leadingIcon = { Icon(Icons.Default.SupervisorAccount, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ppdb_input_parent"),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    errorMessage = null
                },
                label = { Text("Nomor WhatsApp Aktif *") },
                placeholder = { Text("08xxxxxxxxxx") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ppdb_input_phone"),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }

        item {
            Text(
                text = "Pilihan Program Madrasah *",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                programOptions.forEach { prog ->
                    Card(
                        onClick = { program = prog },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (program == prog)
                                EmeraldGreenPrimary.copy(alpha = 0.12f)
                            else MaterialTheme.colorScheme.surface
                        ),
                        border = if (program == prog)
                            CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(EmeraldGreenPrimary))
                        else null,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = program == prog,
                                onClick = { program = prog }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = prog,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = if (prog.contains("Tahfidz"))
                                        "Fokus hafalan 3-5 juz, pembinaan adab & karakter Qur'ani"
                                    else "Kurikulum Kemenag terpadu dengan sains & teknologi",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    if (studentName.isBlank() || originSchool.isBlank() || parentName.isBlank() || phone.isBlank()) {
                        errorMessage = "Mohon lengkapi semua kolom yang bertanda bintang (*)."
                        return@Button
                    }
                    if (nisn.length < 10) {
                        errorMessage = "NISN harus terdiri dari 10 digit angka."
                        return@Button
                    }

                    val regCode = "PPDB-2026-${Random.nextInt(100, 999)}"
                    val newReg = PpdbRegistration(
                        regNumber = regCode,
                        studentName = studentName.trim(),
                        nisn = nisn.trim(),
                        gender = gender,
                        originSchool = originSchool.trim(),
                        parentName = parentName.trim(),
                        phone = phone.trim(),
                        program = program
                    )

                    SchoolRepository.submitRegistration(newReg)
                    registeredData = newReg
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("ppdb_submit_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreenPrimary)
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Kirim Pendaftaran Online",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }

    // Success Registration Dialog
    if (registeredData != null) {
        val reg = registeredData!!
        AlertDialog(
            onDismissRequest = { registeredData = null },
            confirmButton = {
                Button(
                    onClick = {
                        registeredData = null
                        studentName = ""
                        nisn = ""
                        originSchool = ""
                        parentName = ""
                        phone = ""
                        onRegistrationSuccess()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreenPrimary)
                ) {
                    Text("Lihat Data Pendaftar")
                }
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = EmeraldGreenPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Pendaftaran Berhasil!", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Selamat! Formulir pendaftaran online santri MTs Tuan Diah telah berhasil disimpan ke sistem.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Nomor Registrasi:",
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                text = reg.regNumber,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldGreenPrimary
                                )
                            )
                        }
                    }
                    Text("Nama Santri: ${reg.studentName}", fontWeight = FontWeight.SemiBold)
                    Text("Program: ${reg.program}", style = MaterialTheme.typography.bodySmall)
                    Text("Asal Sekolah: ${reg.originSchool}", style = MaterialTheme.typography.bodySmall)
                    Text("No. WhatsApp: ${reg.phone}", style = MaterialTheme.typography.bodySmall)
                    HorizontalDivider()
                    Text(
                        text = "Simpan nomor registrasi ini untuk bukti verifikasi berkas dan jadwal tes wawancara.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        )
    }
}

@Composable
fun PpdbInfoView(onRegisterNow: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Jadwal Gelombang Pendaftaran",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    WaveScheduleItem(
                        title = "Gelombang 1 (Jalur Prestasi & Beasiswa)",
                        date = "01 September - 30 November 2026",
                        benefit = "Diskon 30% Infaq Pengembangan Madrasah + Bebas Biaya Pendaftaran."
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    WaveScheduleItem(
                        title = "Gelombang 2 (Jalur Reguler Mandiri)",
                        date = "01 Desember 2026 - 31 Maret 2027",
                        benefit = "Kuota terbatas 120 santri baru (4 rombel belajar)."
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Syarat Pendaftaran",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val requirements = listOf(
                        "Mengisi formulir pendaftaran online secara lengkap.",
                        "Fotokopi Ijazah / Surat Keterangan Lulus (SKL) SD/MI yang telah dilegalisir (2 lembar).",
                        "Fotokopi Kartu Keluarga (KK) dan Akta Kelahiran calon santri (2 lembar).",
                        "Pas foto formal terbaru ukuran 3x4 (4 lembar, latar biru).",
                        "Fotokopi piagam kejuaraan / sertifikat tahfidz (jika ada).",
                        "Mengikuti tes observasi baca Al-Qur'an dan wawancara santri & walimurid."
                    )
                    requirements.forEachIndexed { i, req ->
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "${i + 1}. ",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(text = req, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }

        item {
            Button(
                onClick = onRegisterNow,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreenPrimary)
            ) {
                Icon(imageVector = Icons.Default.PersonAdd, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Daftar Santri Baru Sekarang", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun WaveScheduleItem(title: String, date: String, benefit: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, color = EmeraldGreenPrimary)
            Text(text = date, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = benefit, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun PpdbDataView() {
    val registrations by SchoolRepository.registrations.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = "Daftar Calon Santri Terdaftar (${registrations.size})",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Data pendaftar PPDB Online Madrasah Tsanawiyah Tuan Diah Tahun Ajaran 2026/2027.",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }

        items(registrations) { reg ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("registered_card_${reg.regNumber}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = EmeraldGreenPrimary.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = reg.regNumber,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldGreenPrimary
                                )
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = GoldSecondary.copy(alpha = 0.18f)
                        ) {
                            Text(
                                text = "Menunggu Tes",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GoldSecondary,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = reg.studentName,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )

                    Text(
                        text = "NISN: ${reg.nisn} • ${reg.gender}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Asal: ${reg.originSchool}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = reg.program,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = EmeraldGreenPrimary
                            )
                        )
                    }
                }
            }
        }
    }
}
