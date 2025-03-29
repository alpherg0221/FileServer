package dev.alpherg.fileserver.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ConnectingAirports
import androidx.compose.material.icons.rounded.Hotel
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.alpherg.fileserver.ui.AppViewModel
import dev.alpherg.fileserver.ui.components.FSTopBar
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(openDrawer: suspend () -> Unit) {
    val appViewModel = hiltViewModel<AppViewModel>()
    val ipAddress by appViewModel.deviceIP.collectAsState()

    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            FSTopBar { scope.launch { openDrawer() } }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Server is ${state.name}",
                        fontWeight = FontWeight.Black,
                        style = MaterialTheme.typography.headlineLarge,
                        color = when (state) {
                            HomeState.Running, HomeState.Initializing -> Color.Green
                            HomeState.Stopping, HomeState.Stopped -> Color.Red
                        }
                    )

                    Spacer(Modifier.height(36.dp))

                    when (state) {
                        HomeState.Running -> Indicator(
                            color = Color.Green,
                            icon = Icons.Rounded.ConnectingAirports,
                        )

                        HomeState.Initializing -> Indicator(
                            fixed = false,
                            color = Color.Green,
                            icon = Icons.Rounded.RocketLaunch,
                        )

                        HomeState.Stopping -> Indicator(
                            fixed = false,
                            color = Color.Red,
                            icon = Icons.Rounded.NightsStay,
                        )

                        HomeState.Stopped -> Indicator(
                            color = Color.Red,
                            icon = Icons.Rounded.Hotel,
                        )
                    }

                    Spacer(Modifier.height(36.dp))

                    when (state) {
                        HomeState.Running -> ChangeStateButton(
                            onClick = viewModel::stopServer,
                            icon = Icons.Rounded.Stop,
                            text = "Stop Server",
                            color = Color.Red,
                        )

                        HomeState.Initializing -> ChangeStateButton(
                            onClick = {},
                            icon = Icons.Rounded.PlayArrow,
                            text = "Start Server",
                            color = Color.Green,
                            enabled = false,
                        )

                        HomeState.Stopping -> ChangeStateButton(
                            onClick = {},
                            icon = Icons.Rounded.Stop,
                            text = "Stop Server",
                            color = Color.Red,
                            enabled = false,
                        )

                        HomeState.Stopped -> ChangeStateButton(
                            onClick = viewModel::startServer,
                            icon = Icons.Rounded.PlayArrow,
                            color = Color.Green,
                            text = "Start Server",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Indicator(
    fixed: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primary,
    icon: ImageVector,
) {
    val indicatorSize = 320.dp
    val indicatorStrokeWidth = 12.dp

    Box(contentAlignment = Alignment.Center) {
        when (fixed) {
            true -> CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.size(indicatorSize),
                color = color,
                strokeWidth = indicatorStrokeWidth,
            )

            false -> CircularProgressIndicator(
                modifier = Modifier.size(indicatorSize),
                color = color,
                strokeWidth = indicatorStrokeWidth,
                strokeCap = StrokeCap.Butt,
            )
        }

        Image(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(128.dp),
            colorFilter = ColorFilter.tint(color)
        )
    }
}

@Composable
fun ChangeStateButton(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    color: Color,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors().copy(
            contentColor = color,
        )
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}