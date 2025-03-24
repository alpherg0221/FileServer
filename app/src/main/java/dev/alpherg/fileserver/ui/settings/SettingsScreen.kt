package dev.alpherg.fileserver.ui.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dev.alpherg.fileserver.ui.components.Settings
import dev.alpherg.fileserver.ui.components.SettingsSectionHeader
import dev.alpherg.fileserver.ui.components.SwitchSettingsItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val settingsViewModel = hiltViewModel<SettingsViewModel>()
    val authEnabled by settingsViewModel.authEnabled.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        },
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            Settings {
                item { SettingsSectionHeader("Security") }

                item {
                    SwitchSettingsItem(
                        icon = Icons.Rounded.Fingerprint,
                        title = "Authentication Required",
                        checked = authEnabled,
                        onCheckChanged = { settingsViewModel.toggleAuthEnabled() },
                    )
                }

                item { HorizontalDivider() }
            }
        }
    }
}