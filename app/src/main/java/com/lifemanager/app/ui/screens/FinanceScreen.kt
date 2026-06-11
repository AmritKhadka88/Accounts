package com.lifemanager.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Expenses", "Income", "Loans", "Savings")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finance", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ScrollableTabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
            when (selectedTab) {
                0 -> ExpensesTab()
                1 -> IncomeTab()
                2 -> LoansTab()
                3 -> SavingsTab()
            }
        }
    }
}

@Composable
fun ExpensesTab() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("This Month", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Total: $0.00", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
        item {
            Text("No expenses recorded yet", style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun IncomeTab() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("This Month", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Total: $0.00", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
        item {
            Text("No income recorded yet", style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun LoansTab() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Outstanding Loans", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Total: $0.00", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
        item {
            Text("No loans recorded yet", style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun SavingsTab() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Total Savings", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Total: $0.00", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
        item {
            Text("No savings recorded yet", style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp))
        }
    }
}
