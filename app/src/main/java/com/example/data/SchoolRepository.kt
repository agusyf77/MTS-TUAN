package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object SchoolRepository {

    val schoolProfile = mapOf(
        "name" to "MTs Tuan Diah",
        "nsm" to "121232010045",
        "npsn" to "20278912",
        "akreditasi" to "A (Unggul)",
        "kepala" to "Drs. H. Ahmad Zarkasyi, M.Pd.I",
        "alamat" to "Jl. Raya Tuan Diah No. 12, Kompleks Perguruan Islam",
        "telepon" to "(021) 8790-2341",
        "whatsapp" to "+62 812-3456-7890",
        "email" to "info@mtstuandiah.sch.id",
        "website" to "www.mtstuandiah.sch.id",
        "visi" to "Terwujudnya Generasi Muslim yang Berakhlakul Karimah, Unggul dalam Prestasi Akademik, Mahir Berbahasa Arab & Inggris, serta Mandiri dan Berjiwa Kepemimpinan.",
        "misi" to listOf(
            "Menanamkan aqidah yang kokoh dan pengamalan ibadah harian sesuai Al-Qur'an dan Sunnah.",
            "Melaksanakan pembelajaran aktif, inovatif, kreatif, dan berorientasi pada kemajuan iptek.",
            "Membina program tahfidz Al-Qur'an dengan target minimal 3 juz hafalan mutqin.",
            "Mengembangkan budaya disiplin, literasi, toleransi, dan kepedulian lingkungan hidup.",
            "Mendorong prestasi santri dalam kompetisi sains madrasah, seni Islam, dan olahraga."
        ),
        "tujuan" to listOf(
            "Melahirkan lulusan yang berkarakter islami dan siap bersaing di jenjang MA/SMA unggulan.",
            "Mencapai rata-rata nilai kelulusan di atas KKM nasional.",
            "Mencetak kader dai muda dan penghafal Al-Qur'an yang berwawasan kebangsaan."
        )
    )

    val facilities = listOf(
        "Gedung Kampus 3 Lantai Terpadu",
        "Masjid Jami' Baitul 'Ilmi Kapasitas 800 Jamaah",
        "Laboratorium Komputer & CBT Modern",
        "Laboratorium IPA Terpadu (Fisika & Biologi)",
        "Perpustakaan Digital & Ruang Baca Nyaman",
        "Lapangan Olahraga Serbaguna (Futsal, Basket, Voli)",
        "Studio Podcast & Media Dakwah Santri",
        "Kantin Bersih dan Halal Terstandarisasi",
        "UKS dan Ruang Bimbingan Konseling"
    )

    val prayerTimes = listOf(
        PrayerTime("Subuh", "الفجر", "04:42"),
        PrayerTime("Terbit", "الشروق", "05:54"),
        PrayerTime("Dzuhur", "الظهر", "11:58", isNext = true),
        PrayerTime("Ashar", "العصر", "15:15"),
        PrayerTime("Maghrib", "المغرب", "18:02"),
        PrayerTime("Isya", "العشاء", "19:11")
    )

    val newsList = listOf(
        NewsItem(
            id = "news-1",
            title = "Penerimaan Peserta Didik Baru (PPDB) Gelombang 1 Tahun Ajaran 2026/2027 Resmi Dibuka",
            category = "PPDB",
            date = "20 Sep 2026",
            summary = "MTs Tuan Diah membuka pendaftaran santri baru untuk Program Reguler dan Kelas Unggulan Tahfidz.",
            content = "Madrasah Tsanawiyah Tuan Diah secara resmi membuka Penerimaan Peserta Didik Baru (PPDB) Tahun Ajaran 2026/2027. Tersedia dua program pilihan: Program Reguler Mandiri dan Kelas Unggulan Tahfidz Al-Qur'an. Bagi pendaftar gelombang pertama berkesempatan mendapatkan beasiswa perlengkapan seragam dan potongan biaya pengembangan madrasah."
        ),
        NewsItem(
            id = "news-2",
            title = "Santri MTs Tuan Diah Meraih Juara 1 Kompetisi Sains Madrasah (KSM) Bidang IPA",
            category = "Prestasi",
            date = "15 Sep 2026",
            summary = "Ananda Muhammad Farhan (Kelas IX) mengharumkan nama madrasah di tingkat kabupaten.",
            content = "Prestasi membanggakan kembali diukir oleh santri MTs Tuan Diah. Ananda Muhammad Farhan, santri kelas IX-A, sukses menyabet Juara 1 dalam ajang Kompetisi Sains Madrasah (KSM) cabang IPA Terpadu tingkat Kabupaten. Kepala Madrasah menyampaikan apresiasi tinggi atas dedikasi para guru pembimbing dan motivasi santri."
        ),
        NewsItem(
            id = "news-3",
            title = "Peringatan Maulid Nabi Muhammad SAW 1448 H & Tabligh Akbar Bersama Walimurid",
            category = "Kegiatan",
            date = "10 Sep 2026",
            summary = "Agenda tahunan perayaan maulid disemarakkan dengan lomba hadroh dan santunan anak yatim.",
            content = "Keluarga besar MTs Tuan Diah menggelar peringatan Maulid Nabi Muhammad SAW dengan tema 'Meneladani Akhlak Rasulullah dalam Menuntut Ilmu dan Membangun Peradaban'. Kegiatan ini dihadiri oleh jajaran komite, dewan guru, santri, serta para wali murid, diiringi persembahan grup sholawat hadroh santri."
        ),
        NewsItem(
            id = "news-4",
            title = "Jadwal Penilaian Tengah Semester (PTS) Ganjil dan Ketentuan Pelaksanaan Ujian CBT",
            category = "Akademik",
            date = "05 Sep 2026",
            summary = "PTS Ganjil dijadwalkan berlangsung tanggal 28 September hingga 06 Oktober 2026.",
            content = "Diberitahukan kepada seluruh santri kelas VII, VIII, dan IX beserta wali murid bahwa Penilaian Tengah Semester (PTS) Ganjil akan diselenggarakan berbasis Computer Based Test (CBT) dan lembar kerja terstruktur. Seluruh santri diimbau mempersiapkan materi dan menjaga kesehatan."
        )
    )

    val subjects = listOf(
        SubjectItem("s-1", "Al-Qur'an Hadits", "QH-01", "Pendidikan Agama Islam", "Ust. H. Fauzi Rahman, S.Pd.I", 75, "Mempelajari hukum tajwid, pemahaman ayat-ayat Al-Qur'an dan sunnah nabawiyah."),
        SubjectItem("s-2", "Akidah Akhlak", "AA-02", "Pendidikan Agama Islam", "Ust. Ahmad Syukri, M.Ag", 75, "Penanaman rukun iman, adab islami, akhlak terpuji kepada orang tua, guru, dan sesama."),
        SubjectItem("s-3", "Fikih Ibadah & Muamalah", "FK-03", "Pendidikan Agama Islam", "Ustzh. Siti Maryam, S.Sy", 75, "Thaharah, sholat, puasa, zakat, haji, serta prinsip transaksi syariah sehari-hari."),
        SubjectItem("s-4", "Sejarah Kebudayaan Islam (SKI)", "SKI-04", "Pendidikan Agama Islam", "Ust. Ridwan Kamil, S.Pd.I", 75, "Perjalanan dakwah Rasulullah, Khulafaur Rasyidin, dan kejayaan daulah Islamiyah."),
        SubjectItem("s-5", "Bahasa Arab", "ARB-05", "Bahasa & Keagamaan", "Ustzh. Nurul Hidayah, Lc", 75, "Kosa kata (mufradat), percakapan (muhadatsah), dan tata bahasa arab (nahwu sharaf)."),
        SubjectItem("s-6", "Matematika", "MTK-06", "Mata Pelajaran Umum", "Bpk. Hendra Gunawan, M.Pd", 72, "Aljabar, geometri, statistika, logika matematika dan pemecahan masalah numerik."),
        SubjectItem("s-7", "Ilmu Pengetahuan Alam (IPA)", "IPA-07", "Mata Pelajaran Umum", "Ibu Dewi Rahmawati, S.Si", 72, "Eksplorasi biologi organisme, sistem gerak, kalor, serta fisika terapan."),
        SubjectItem("s-8", "Bahasa Indonesia", "BIN-08", "Mata Pelajaran Umum", "Ibu Endang Susilowati, S.Pd", 75, "Kemampuan membaca kritis, menulis teks deskripsi, cerpen islami, dan pidato."),
        SubjectItem("s-9", "Bahasa Inggris", "ENG-09", "Mata Pelajaran Umum", "Bpk. Reza Pratama, M.Hum", 72, "Daily conversation, grammar, reading comprehension, dan public speaking."),
        SubjectItem("s-10", "Informatika & Komputer", "INF-10", "Mata Pelajaran Umum", "Bpk. Arif Wibowo, S.Kom", 75, "Dasar pemrograman, pengolah data digital, etika berinternet, dan kecerdasan artifisial.")
    )

    val sampleSchedules = listOf(
        ScheduleItem("sch-1", "Senin", "Kelas VII-A", "Upacara Bendera & Apel", "Semua Guru", "07:00 - 07:45", "Lapangan"),
        ScheduleItem("sch-2", "Senin", "Kelas VII-A", "Al-Qur'an Hadits", "Ust. H. Fauzi Rahman", "07:45 - 09:15", "R-101"),
        ScheduleItem("sch-3", "Senin", "Kelas VII-A", "Matematika", "Bpk. Hendra Gunawan", "09:45 - 11:15", "R-101"),
        ScheduleItem("sch-4", "Senin", "Kelas VII-A", "Sholat Dzuhur Berjamaah", "Ust. Syukri", "11:45 - 12:30", "Masjid"),
        ScheduleItem("sch-5", "Senin", "Kelas VII-A", "Bahasa Arab", "Ustzh. Nurul Hidayah, Lc", "12:30 - 14:00", "R-101"),

        ScheduleItem("sch-6", "Selasa", "Kelas VII-A", "Sholat Dhuha & Tahfidz", "Ust. Ridwan", "07:00 - 08:00", "Masjid"),
        ScheduleItem("sch-7", "Selasa", "Kelas VII-A", "IPA Terpadu", "Ibu Dewi Rahmawati", "08:00 - 09:30", "Lab IPA"),
        ScheduleItem("sch-8", "Selasa", "Kelas VII-A", "Bahasa Indonesia", "Ibu Endang Susilowati", "10:00 - 11:30", "R-101"),
        ScheduleItem("sch-9", "Selasa", "Kelas VII-A", "Fikih", "Ustzh. Siti Maryam", "12:30 - 14:00", "R-101"),

        ScheduleItem("sch-10", "Rabu", "Kelas VII-A", "Bahasa Inggris", "Bpk. Reza Pratama", "07:00 - 08:30", "R-101"),
        ScheduleItem("sch-11", "Rabu", "Kelas VII-A", "Informatika / CBT", "Bpk. Arif Wibowo", "08:30 - 10:00", "Lab Komputer"),
        ScheduleItem("sch-12", "Rabu", "Kelas VII-A", "Akidah Akhlak", "Ust. Ahmad Syukri", "10:30 - 12:00", "R-101"),
        ScheduleItem("sch-13", "Rabu", "Kelas VII-A", "Sejarah Kebudayaan Islam", "Ust. Ridwan Kamil", "12:30 - 14:00", "R-101"),

        ScheduleItem("sch-14", "Kamis", "Kelas VII-A", "Tahfidz & Muraja'ah", "Ust. H. Fauzi Rahman", "07:00 - 08:30", "R-101"),
        ScheduleItem("sch-15", "Kamis", "Kelas VII-A", "Matematika (Latihan)", "Bpk. Hendra Gunawan", "08:30 - 10:00", "R-101"),
        ScheduleItem("sch-16", "Kamis", "Kelas VII-A", "Pendidikan Jasmani (PJOK)", "Bpk. Surya Dinata", "10:30 - 12:00", "Lapangan"),

        ScheduleItem("sch-17", "Jumat", "Kelas VII-A", "Kajian Dhuha & Muhadharah", "Dewan Guru", "07:00 - 08:30", "Masjid"),
        ScheduleItem("sch-18", "Jumat", "Kelas VII-A", "Bahasa Arab (Muhadatsah)", "Ustzh. Nurul Hidayah", "08:30 - 10:00", "R-101"),
        ScheduleItem("sch-19", "Jumat", "Kelas VII-A", "Sholat Jumat Berjamaah", "Ust. Drs. H. Ahmad", "11:30 - 12:45", "Masjid"),

        ScheduleItem("sch-20", "Sabtu", "Kelas VII-A", "Kegiatan Ekstrakurikuler", "Pembina Ekskul", "07:30 - 11:30", "Kampus MTs")
    )

    val academicCalendar = listOf(
        AcademicEvent("cal-1", "Awal Masuk Tahun Ajaran Baru & MATSAMA", "14 - 18 Juli 2026", "Juli", "Akademik", "Masa Ta'aruf Siswa Madrasah bagi santri baru dan awal KBM."),
        AcademicEvent("cal-2", "Peringatan Tahun Baru Islam 1 Muharram 1448 H", "28 Juli 2026", "Juli", "Peringatan", "Pawai ta'aruf dan santunan sosial santri."),
        AcademicEvent("cal-3", "Peringatan HUT Kemerdekaan RI ke-81", "17 Agustus 2026", "Agustus", "Peringatan", "Upacara kenegaraan dan aneka lomba kreativitas santri."),
        AcademicEvent("cal-4", "Penilaian Tengah Semester (PTS) Ganjil", "28 Sep - 06 Okt 2026", "September", "Ujian", "Ujian evaluasi capaian belajar paruh semester ganjil."),
        AcademicEvent("cal-5", "Penilaian Akhir Semester (PAS) Ganjil", "30 Nov - 10 Des 2026", "November", "Ujian", "Asesmen sumatif akhir semester berbasis CBT."),
        AcademicEvent("cal-6", "Pembagian Buku Rapor Semester Ganjil", "19 Desember 2026", "Desember", "Akademik", "Pertemuan walimurid dan konsultasi evaluasi belajar."),
        AcademicEvent("cal-7", "Libur Akhir Semester Ganjil", "21 Des 2026 - 02 Jan 2027", "Desember", "Libur", "Libur semester dan persiapan semester genap.")
    )

    val teachers = listOf(
        Teacher("t-1", "Drs. H. Ahmad Zarkasyi, M.Pd.I", "Kepala Madrasah", "Fikih Ushul", "S2 Manajemen Pendidikan Islam (UIN)"),
        Teacher("t-2", "Ust. H. Fauzi Rahman, S.Pd.I", "Waka Kurikulum", "Al-Qur'an Hadits & Tahfidz", "S1 Pendidikan Agama Islam"),
        Teacher("t-3", "Bpk. Hendra Gunawan, M.Pd", "Waka Kesiswaan", "Matematika", "S2 Pendidikan Matematika"),
        Teacher("t-4", "Ustzh. Nurul Hidayah, Lc", "Waka Humas", "Bahasa Arab", "S1 Sastra & Studi Islam (Al-Azhar Kairo)"),
        Teacher("t-5", "Ust. Ahmad Syukri, M.Ag", "Guru Pengajar", "Akidah Akhlak", "S2 Aqidah dan Filsafat Islam"),
        Teacher("t-6", "Ibu Dewi Rahmawati, S.Si", "Kepala Lab IPA", "Ilmu Pengetahuan Alam", "S1 Sains Biologi (IPB)"),
        Teacher("t-7", "Bpk. Arif Wibowo, S.Kom", "Kepala Lab Komputer", "Informatika & TIK", "S1 Sistem Informasi"),
        Teacher("t-8", "Ibu Endang Susilowati, S.Pd", "Pembina OSIS", "Bahasa Indonesia", "S1 Pendidikan Bahasa & Sastra"),
        Teacher("t-9", "Bpk. Reza Pratama, M.Hum", "Pembina English Club", "Bahasa Inggris", "S2 Linguistik Terapan"),
        Teacher("t-10", "Ust. Ridwan Kamil, S.Pd.I", "Pembina Pramuka & Hadroh", "Sejarah Kebudayaan Islam", "S1 Sejarah Peradaban Islam")
    )

    val extracurriculars = listOf(
        Extracurricular("ex-1", "Tahfidzul Qur'an", "AutoAwesome", "Setiap Hari 06:00 - 06:45 & Sore", "Ust. H. Fauzi Rahman", "Bimbingan tahsin talaqqi dan setor hafalan target 3-5 juz mutqin."),
        Extracurricular("ex-2", "Pramuka Madrasah (Gudep Tuan Diah)", "Flag", "Sabtu, 07:30 - 09:30", "Ust. Ridwan Kamil", "Pelatihan kedisiplinan, kepemimpinan, tali-temali, dan survival santri."),
        Extracurricular("ex-3", "Seni Hadroh & Marawis Sholawat", "MusicNote", "Kamis, 15:30 - 17:00", "Ust. Ahmad Syukri", "Pelatihan tabuhan rebana klasik dan vokal lantunan qasidah madrasah."),
        Extracurricular("ex-4", "Palang Merah Remaja (PMR)", "LocalHospital", "Sabtu, 09:30 - 11:00", "Ibu Dewi Rahmawati", "Kesiapsiagaan P3K, kesehatan reproduksi remaja, dan aksi donor darah."),
        Extracurricular("ex-5", "Futsal & Olahraga Prestasi", "SportsSoccer", "Selasa & Jumat Sore", "Bpk. Surya Dinata", "Latihan teknik bola futsal, pembinaan fisik, dan turnamen antarmadrasah."),
        Extracurricular("ex-6", "Klub Bahasa (Arabic & English Club)", "Translate", "Rabu Sore, 15:30 - 17:00", "Ustzh. Nurul & Bpk. Reza", "Praktik muhadatsah, speech, debate, serta persiapan olimpiade bahasa."),
        Extracurricular("ex-7", "Seni Kaligrafi Islam", "Brush", "Senin Sore, 15:30 - 17:00", "Ust. M. Haris, S.Pd.I", "Penulisan khath naskhi, tsuluts, riq'ah di media kanvas dan kertas karya.")
    )

    val defaultHabits = listOf(
        DailyHabit("h-1", "Sholat Subuh Berjamaah", "Sholat Fardhu", 10),
        DailyHabit("h-2", "Sholat Dzuhur Berjamaah", "Sholat Fardhu", 10),
        DailyHabit("h-3", "Sholat Ashar Berjamaah", "Sholat Fardhu", 10),
        DailyHabit("h-4", "Sholat Maghrib Berjamaah", "Sholat Fardhu", 10),
        DailyHabit("h-5", "Sholat Isya Berjamaah", "Sholat Fardhu", 10),
        DailyHabit("h-6", "Sholat Dhuha", "Sholat Sunnah", 10),
        DailyHabit("h-7", "Sholat Tahajjud / Qiyamul Lail", "Sholat Sunnah", 10),
        DailyHabit("h-8", "Tilawah Al-Qur'an (Min. 1 'Ain / Juz)", "Al-Qur'an", 15),
        DailyHabit("h-9", "Hafalan / Muraja'ah Ayat Pilihan", "Al-Qur'an", 10),
        DailyHabit("h-10", "Dzikir Pagi & Petang (Al-Ma'tsurat)", "Adab & Dzikir", 10),
        DailyHabit("h-11", "Sedekah Subuh / Infaq Santri", "Adab & Dzikir", 5)
    )

    val dailyPrayers = listOf(
        DailyPrayer(
            id = "p-1",
            title = "Doa Sebelum Belajar",
            arabic = "رَضِيتُ بِاللهِ رَبًّا، وَبِالإِسْلاَمِ دِينًا، وَبِمُحَمَّدٍ نَبِيًّا وَرَسُولاً، رَبِّ زِدْنِي عِلْمًا وَارْزُقْنِي فَهْمًا",
            latin = "Rodhitubillahi robba, wabil islaami deena, wabimuhammadin nabiyya warasuula, robbi zidnii 'ilman warzuqnii fahman.",
            translation = "Kami ridho Allah sebagai Tuhanku, Islam agamaku, dan Nabi Muhammad sebagai Nabi dan Rasul-Nya. Ya Tuhanku, tambahkanlah ilmu kepadaku dan anugerahkanlah aku kepahaman.",
            occasion = "Dibaca bersama sebelum memulai pelajaran di kelas"
        ),
        DailyPrayer(
            id = "p-2",
            title = "Doa untuk Kedua Orang Tua",
            arabic = "رَبِّ اغْفِرْ لِي وَلِوَالِدَيَّ وَارْحَمْهُمَا كَمَا رَبَّيَانِي صَغِيرًا",
            latin = "Robbighfirlii waliwaalidayya warhamhumaa kamaa robbayaanii shoghiroo.",
            translation = "Wahai Tuhanku, ampunilah dosaku dan dosa kedua orang tuaku, serta sayangilah mereka sebagaimana mereka menyayangiku di waktu kecil.",
            occasion = "Dibaca setiap selesai sholat fardhu"
        ),
        DailyPrayer(
            id = "p-3",
            title = "Doa Kebaikan Dunia dan Akhirat (Sapu Jagad)",
            arabic = "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ",
            latin = "Robbanaa aatinaa fiddunyaa hasanah, wafil aakhiroti hasanah, waqinaa 'adzaaban naar.",
            translation = "Ya Tuhan kami, berilah kami kebaikan di dunia dan kebaikan di akhirat, dan lindungilah kami dari siksa api neraka.",
            occasion = "Doa utama pelengkap seluruh amalan"
        ),
        DailyPrayer(
            id = "p-4",
            title = "Doa Masuk Masjid",
            arabic = "اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ",
            latin = "Allahummaf tahlii abwaaba rohmatik.",
            translation = "Ya Allah, bukakanlah untukku pintu-pintu rahmat-Mu.",
            occasion = "Ketika melangkahkan kaki kanan memasuki masjid"
        ),
        DailyPrayer(
            id = "p-5",
            title = "Sayyidul Istighfar (Rajanya Doa Pengampunan)",
            arabic = "اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ",
            latin = "Allahumma anta robbii laa ilaaha illaa anta, kholaqtanii wa ana 'abduka, wa ana 'ala 'ahdika wawa'dika mastatho'tu, a'uudzu bika min syarri maa shona'tu, abuu-u laka bini'matika 'alayya, wa abuu-u bidzanbii faghfirlii fainnahu laa yaghfirudz dzunuuba illaa anta.",
            translation = "Ya Allah, Engkaulah Tuhanku, tiada tuhan selain Engkau. Engkaulah yang menciptakanku dan aku adalah hamba-Mu...",
            occasion = "Dibaca rutin pagi dan petang hari"
        )
    )

    // Reactive State for Habits Tracker
    private val _habitsState = MutableStateFlow(defaultHabits)
    val habitsState: StateFlow<List<DailyHabit>> = _habitsState.asStateFlow()

    fun toggleHabit(habitId: String) {
        _habitsState.update { list ->
            list.map { item ->
                if (item.id == habitId) item.copy(isDone = !item.isDone) else item
            }
        }
    }

    fun resetHabits() {
        _habitsState.value = defaultHabits
    }

    // Reactive State for PPDB Registrations
    private val _registrations = MutableStateFlow<List<PpdbRegistration>>(
        listOf(
            PpdbRegistration(
                regNumber = "PPDB-2026-001",
                studentName = "Ahmad Rayhan Al-Fatih",
                nisn = "0123456789",
                gender = "Laki-laki",
                originSchool = "MI Nurul Iman",
                parentName = "Bambang Santoso",
                phone = "081298765432",
                program = "Kelas Tahfidz Al-Qur'an"
            ),
            PpdbRegistration(
                regNumber = "PPDB-2026-002",
                studentName = "Aisyah Nur Amini",
                nisn = "0123459876",
                gender = "Perempuan",
                originSchool = "SD Negeri 01 Pagi",
                parentName = "Muhammad Yusuf",
                phone = "081377889900",
                program = "Program Reguler"
            )
        )
    )
    val registrations: StateFlow<List<PpdbRegistration>> = _registrations.asStateFlow()

    fun submitRegistration(reg: PpdbRegistration) {
        _registrations.update { listOf(reg) + it }
    }
}
