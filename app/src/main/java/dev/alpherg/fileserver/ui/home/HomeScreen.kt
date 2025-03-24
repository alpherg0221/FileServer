package dev.alpherg.fileserver.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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
                        ipAddress,
                        fontWeight = FontWeight.Black,
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.headlineLarge,
                    )

                    Spacer(Modifier.height(36.dp))

                    when (state) {
                        HomeState.Running -> Indicator(color = Color.Red)

                        HomeState.Initializing -> Indicator(fixed = false, color = Color.Green)

                        HomeState.Stopping -> Indicator(fixed = false, color = Color.Red)

                        HomeState.Stopped -> Indicator(color = Color.Green)
                    }

                    Spacer(Modifier.height(36.dp))

                    Text(
                        "Server is ${state.name}",
                        fontWeight = FontWeight.Black,
                        style = MaterialTheme.typography.headlineLarge,
                        color = when (state) {
                            HomeState.Running, HomeState.Initializing -> Color.Green
                            HomeState.Stopping, HomeState.Stopped -> Color.Red
                        }
                    )
                }

                when (state) {
                    HomeState.Running -> IndicatorButton("Stop Server", color = Color.Red) {
                        viewModel.stopServer()
                    }

                    HomeState.Initializing -> Image(
                        imageVector = Icons.Rounded.RocketLaunch,
                        contentDescription = null,
                        modifier = Modifier.size(128.dp),
                        colorFilter = ColorFilter.tint(Color.Green)
                    )

                    HomeState.Stopping -> Image(
                        imageVector = Icons.Rounded.NightsStay,
                        contentDescription = null,
                        modifier = Modifier.size(128.dp),
                        colorFilter = ColorFilter.tint(Color.Red)
                    )

                    HomeState.Stopped -> IndicatorButton("Start Server", color = Color.Green) {
                        viewModel.startServer()
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
) {
    val indicatorSize = 320.dp
    val indicatorStrokeWidth = 12.dp

    return if (fixed) {
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.size(indicatorSize),
            color = color,
            strokeWidth = indicatorStrokeWidth,
        )
    } else {
        CircularProgressIndicator(
            modifier = Modifier.size(indicatorSize),
            color = color,
            strokeWidth = indicatorStrokeWidth,
            strokeCap = StrokeCap.Butt,
        )
    }
}

@Composable
fun IndicatorButton(
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
) {
    val indicatorSize = 320.dp
    val indicatorStrokeWidth = 12.dp

    return TextButton(
        modifier = Modifier.size(indicatorSize - indicatorStrokeWidth),
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors().copy(
            containerColor = color.copy(alpha = 0.1f),
            contentColor = color,
        )
    ) {
        Text(text, style = MaterialTheme.typography.headlineLarge)
    }
}