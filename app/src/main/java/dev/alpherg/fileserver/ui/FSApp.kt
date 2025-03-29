package dev.alpherg.fileserver.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.SettingsInputAntenna
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.alpherg.fileserver.ui.components.AppDest
import dev.alpherg.fileserver.ui.components.DrawerApp
import dev.alpherg.fileserver.ui.components.RouteInfo
import dev.alpherg.fileserver.ui.home.HomeScreen
import dev.alpherg.fileserver.ui.info.InfoScreen
import dev.alpherg.fileserver.ui.settings.SettingsScreen
import dev.alpherg.fileserver.ui.theme.FileServerTheme

@Composable
fun FSApp() {
    val navController = rememberNavController()

    // ドロワーの状態
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val appDest = object : AppDest() {
        val settingRoute = RouteInfo(
            icon = Icons.Rounded.Settings,
            label = "Setting",
            route = "setting",
        )
        val infoRoute = RouteInfo(
            icon = Icons.Rounded.Info,
            label = "Info",
            route = "info",
        )
    }

    val drawerItems = listOf(
        appDest.settingRoute,
        appDest.infoRoute,
    )

    val appViewModel = hiltViewModel<AppViewModel>()
    val deviceIP by appViewModel.deviceIP.collectAsState()

    val openDrawer: suspend () -> Unit = {
        drawerState.open()
    }

    val onBack: () -> Unit = {
        navController.popBackStack()
    }

    FileServerTheme {
        DrawerApp(
            navController = navController,
            drawerState = drawerState,
            appDest = appDest,
            drawerItems = drawerItems,
            gesturesDisabledRoute = listOf(
                appDest.settingRoute.route,
                appDest.infoRoute.route,
            ),
            drawerHeader = {
                Row(
                    modifier = Modifier.padding(start = 16.dp, end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        imageVector = Icons.Rounded.SettingsInputAntenna,
                        contentDescription = "",
                        modifier = Modifier.size(36.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        deviceIP,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Black,
                        fontSize = 30.sp,
                    )
                }
            }
        ) {
            composable(appDest.homeRoute.route) {
                HomeScreen(openDrawer = openDrawer)
            }
            composable(appDest.settingRoute.route) {
                SettingsScreen(onBack = onBack)
            }
            composable(appDest.infoRoute.route) {
                InfoScreen(onBack = onBack)
            }
        }
    }
}