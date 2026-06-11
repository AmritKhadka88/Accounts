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
fun DashboardScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Life Manager", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { SummaryCardsRow() }
            item { QuickActionsCard() }
            item { RecentTransactionsCard() }
            item { UpcomingRemindersCard() }
        }
    }
}

@Composable
fun SummaryCardsRow() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Overview", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SummaryCard(
                modifier = Modifier.weight(1f),
                title = "Income",
                value = "0.00",
                icon = Icons.Filled.TrendingUp,
                color = MaterialTheme.colorScheme.primary
            )
            SummaryCard(
                modifier = Modifier.weight(1f),
                title = "Expenses",
                value = "0.00",
                icon = Icons.Filled.TrendingDown,
                color = MaterialTheme.colorScheme.error
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SummaryCard(
                modifier = Modifier.weight(1f),
                title = "Savings",
                value = "0.00",
                icon = Icons.Filled.Savings,
                color = MaterialTheme.colorScheme.tertiary
            )
            SummaryCard(
                modifier = Modifier.weight(1f),
                title = "Loans",
                value = "0.00",
                icon = Icons.Filled.AccountBalance,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun SummaryCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: androidx.compose.ui.graphics.Color
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(icon, contentDescription = null, tint = color)
            Text(title, style = MaterialTheme.typography.labelMedium)
            Text("$$value", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun QuickActionsCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Quick Add", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                QuickActionButton(modifier = Modifier.weight(1f), label = "Expense", icon = Icons.Filled.Remove)
                QuickActionButton(modifier = Modifier.weight(1f), label = "Income", icon = Icons.Filled.Add)
                QuickActionButton(modifier = Modifier.weight(1f), label = "Fuel", icon = Icons.Filled.LocalGasStation)
                QuickActionButton(modifier = Modifier.weight(1f), label = "Note", icon = Icons.Filled.Note)
            }
        }
    }
}

@Composable
fun QuickActionButton(modifier: Modifier = Modifier, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    FilledTonalButton(
        onClick = {},
        modifier = modifier,
        contentPadding = PaddingValues(4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Text(label, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun RecentTransactionsCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Recent Transactions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("No transactions yet", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun UpcomingRemindersCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Upcoming Reminders", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("No reminders set", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
