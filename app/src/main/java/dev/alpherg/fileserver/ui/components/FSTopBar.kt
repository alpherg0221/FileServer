package dev.alpherg.fileserver.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.alpherg.fileserver.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FSTopBar(openDrawer: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(stringResource(R.string.app_name))
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = null,
                )
            }
        }
    )
}