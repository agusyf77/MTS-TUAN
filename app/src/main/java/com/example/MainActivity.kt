package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import com.example.ui.screens.*
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.MTsTuanDiahTheme

enum class SchoolNavDestination(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    HOME("Beranda", Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
    ACADEMIC("Akademik", Icons.Filled.School, Icons.Outlined.School, "nav_academic"),
    IBADAH("Ibadah", Icons.Filled.Mosque, Icons.Outlined.Mosque, "nav_ibadah"),
    PPDB("PPDB", Icons.Filled.PersonAdd, Icons.Outlined.PersonAdd, "nav_ppdb"),
    PROFILE("Profil", Icons.Filled.AccountBalance, Icons.Outlined.AccountBalance, "nav_profile")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MTsTuanDiahTheme {
                MainAppScreen()
            }
        }
    }
}

@Composable
fun MainAppScreen() {
    var currentDestination by rememberSaveable { mutableStateOf(SchoolNavDestination.HOME) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = NavigationBarDefaults.Elevation
            ) {
                SchoolNavDestination.entries.forEach { destination ->
                    val isSelected = currentDestination == destination
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentDestination = destination },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                                contentDescription = destination.label
                            )
                        },
                        label = {
                            Text(text = destination.label)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = EmeraldGreenPrimary,
                            selectedTextColor = EmeraldGreenPrimary,
                            indicatorColor = EmeraldGreenPrimary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag(destination.testTag)
                    )
                }
            }
        }
    ) { innerPadding ->
        when (currentDestination) {
            SchoolNavDestination.HOME -> HomeScreen(
                onNavigateToAcademic = { currentDestination = SchoolNavDestination.ACADEMIC },
                onNavigateToIbadah = { currentDestination = SchoolNavDestination.IBADAH },
                onNavigateToPpdb = { currentDestination = SchoolNavDestination.PPDB },
                onNavigateToProfile = { currentDestination = SchoolNavDestination.PROFILE },
                modifier = Modifier.padding(innerPadding)
            )
            SchoolNavDestination.ACADEMIC -> AcademicScreen(
                modifier = Modifier.padding(innerPadding)
            )
            SchoolNavDestination.IBADAH -> IbadahScreen(
                modifier = Modifier.padding(innerPadding)
            )
            SchoolNavDestination.PPDB -> PpdbScreen(
                modifier = Modifier.padding(innerPadding)
            )
            SchoolNavDestination.PROFILE -> ProfileScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
