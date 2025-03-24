package dev.alpherg.fileserver.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Settings(contents: LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = contents
    )
}

@Composable
fun SettingsSectionHeader(title: String) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 28.dp, bottom = 12.dp),
        text = title,
        fontWeight = FontWeight.Black,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
fun SwitchSettingsItem(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckChanged: (checked: Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            Icon(
                modifier = Modifier.padding(start = 16.dp, end = 38.dp),
                imageVector = icon,
                contentDescription = null,
            )
            Text(
                text = title,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        Switch(
            modifier = Modifier.padding(end = 16.dp),
            checked = checked,
            onCheckedChange = onCheckChanged,
        )
    }
}