package com.lifemanager.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("More", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { SectionHeader("Tools") }
            item { MoreMenuItem(icon = Icons.Filled.ShoppingCart, title = "Shopping Lists", subtitle = "Manage your shopping lists") }
            item { MoreMenuItem(icon = Icons.Filled.Home, title = "Address History", subtitle = "Track your residential history") }
            item { MoreMenuItem(icon = Icons.Filled.Notifications, title = "Reminders", subtitle = "Bills, loans and custom reminders") }
            item { MoreMenuItem(icon = Icons.Filled.Folder, title = "Document Vault", subtitle = "Store important documents") }
            item { MoreMenuItem(icon = Icons.Filled.BarChart, title = "Analytics", subtitle = "Trends and insights") }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item { SectionHeader("Settings & Data") }
            item { MoreMenuItem(icon = Icons.Filled.Lock, title = "Security", subtitle = "PIN and fingerprint lock") }
            item { MoreMenuItem(icon = Icons.Filled.Backup, title = "Backup & Restore", subtitle = "Export and import your data") }
            item { MoreMenuItem(icon = Icons.Filled.Palette, title = "Appearance", subtitle = "Dark mode, colors, font size") }
            item { MoreMenuItem(icon = Icons.Filled.Info, title = "About", subtitle = "Version 1.0") }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun MoreMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(icon, contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium)
                Text(subtitle, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}