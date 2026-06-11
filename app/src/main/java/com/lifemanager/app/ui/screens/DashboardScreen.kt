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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lifemanager.app.viewmodel.AppViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(vm: AppViewModel = viewModel()) {
    val expenses by vm.allExpenses.collectAsStateWithLifecycle()
    val incomes by vm.allIncomes.collectAsStateWithLifecycle()
    val loans by vm.allLoans.collectAsStateWithLifecycle()
    val savings by vm.totalSavings.collectAsStateWithLifecycle()
    val reminders by vm.allReminders.collectAsStateWithLifecycle()
    var showAddExpense by remember { mutableStateOf(false) }
    var showAddIncome by remember { mutableStateOf(false) }
    var showAddNote by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Life Manager", fontWeight = FontWeight.Bold) },
                actions = { IconButton(onClick = {}) { Icon(Icons.Filled.Settings, null) } }
            )
        }
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Overview", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SummaryCard(Modifier.weight(1f), "Income", "${"%.2f".format(incomes.sumOf{it.amount})}", Icons.Filled.TrendingUp, MaterialTheme.colorScheme.primary)
                    SummaryCard(Modifier.weight(1f), "Expenses", "${"%.2f".format(expenses.sumOf{it.amount})}", Icons.Filled.TrendingDown, MaterialTheme.colorScheme.error)
                }
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SummaryCard(Modifier.weight(1f), "Savings", "${"%.2f".format(savings ?: 0.0)}", Icons.Filled.Savings, MaterialTheme.colorScheme.tertiary)
                    SummaryCard(Modifier.weight(1f), "Loans", "${"%.2f".format(loans.sumOf{it.principal})}", Icons.Filled.AccountBalance, MaterialTheme.colorScheme.secondary)
                }
            }
            item {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Quick Add", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            FilledTonalButton(onClick = { showAddExpense = true }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(4.dp)) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Filled.Remove, null, Modifier.size(20.dp)); Text("Expense", style = MaterialTheme.typography.labelSmall) }
                            }
                            FilledTonalButton(onClick = { showAddIncome = true }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(4.dp)) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Filled.Add, null, Modifier.size(20.dp)); Text("Income", style = MaterialTheme.typography.labelSmall) }
                            }
                            FilledTonalButton(onClick = { showAddNote = true }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(4.dp)) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Filled.Note, null, Modifier.size(20.dp)); Text("Note", style = MaterialTheme.typography.labelSmall) }
                            }
                        }
                    }
                }
            }
            item {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Recent Transactions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        if (expenses.isEmpty() && incomes.isEmpty()) {
                            Text("No transactions yet", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        } else {
                            expenses.take(3).forEach { e ->
                                Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(e.title)
                                    Text("-${"%.2f".format(e.amount)}", color = MaterialTheme.colorScheme.error)
                                }
                            }
                            incomes.take(3).forEach { i ->
                                Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.S