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

fun fmt(v: Double): String {
    return String.format("%.2f", v)
}

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

    val totalIncome = incomes.sumOf { it.amount }
    val totalExpenses = expenses.sumOf { it.amount }
    val totalLoans = loans.sumOf { it.principal }
    val savingsValue = savings ?: 0.0

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
                    SummaryCard(Modifier.weight(1f), "Income", fmt(totalIncome), Icons.Filled.TrendingUp, MaterialTheme.colorScheme.primary)
                    SummaryCard(Modifier.weight(1f), "Expenses", fmt(totalExpenses), Icons.Filled.TrendingDown, MaterialTheme.colorScheme.error)
                }
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SummaryCard(Modifier.weight(1f), "Savings", fmt(savingsValue), Icons.Filled.Savings, MaterialTheme.colorScheme.tertiary)
                    SummaryCard(Modifier.weight(1f), "Loans", fmt(totalLoans), Icons.Filled.AccountBalance, MaterialTheme.colorScheme.secondary)
                }
            }
            item {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Quick Add", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            FilledTonalButton(onClick = { showAddExpense = true }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(4.dp)) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Filled.Remove, null, Modifier.size(20.dp))
                                    Text("Expense", style = MaterialTheme.typography.labelSmall)
                                }
                            }
                            FilledTonalButton(onClick = { showAddIncome = true }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(4.dp)) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Filled.Add, null, Modifier.size(20.dp))
                                    Text("Income", style = MaterialTheme.typography.labelSmall)
                                }
                            }
                            FilledTonalButton(onClick = { showAddNote = true }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(4.dp)) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Filled.Note, null, Modifier.size(20.dp))
                                    Text("Note", style = MaterialTheme.typography.labelSmall)
                                }
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
                                    Text("-" + fmt(e.amount), color = MaterialTheme.colorScheme.error)
                                }
                            }
                            incomes.take(3).forEach { i ->
                                Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(i.title)
                                    Text("+" + fmt(i.amount), color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }
            }
            item {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Upcoming Reminders", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        val upcoming = reminders.filter { it.dueDate > System.currentTimeMillis() }.take(3)
                        if (upcoming.isEmpty()) {
                            Text("No reminders set", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        } else {
                            upcoming.forEach { r ->
                                Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(r.title)
                                    Text(SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(r.dueDate)), style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddExpense) {
        var title by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        AlertDialog(onDismissRequest = { showAddExpense = false }, title = { Text("Add Expense") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                }
            },
            confirmButton = {
                Button(onClick = {
                    val a = amount.toDoubleOrNull()
                    if (title.isNotEmpty() && a != null) {
                        vm.insertExpense(title, a, "Other")
                        showAddExpense = false
                    }
                }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { showAddExpense = false }) { Text("Cancel") } }
        )
    }

    if (showAddIncome) {
        var title by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        AlertDialog(onDismissRequest = { showAddIncome = false }, title = { Text("Add Income") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                }
            },
            confirmButton = {
                Button(onClick = {
                    val a = amount.toDoubleOrNull()
                    if (title.isNotEmpty() && a != null) {
                        vm.insertIncome(title, a, "Other")
                        showAddIncome = false
                    }
                }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { showAddIncome = false }) { Text("Cancel") } }
        )
    }

    if (showAddNote) {
        var title by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }
        AlertDialog(onDismissRequest = { showAddNote = false }, title = { Text("New Note") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Content") }, modifier = Modifier.fillMaxWidth().height(120.dp))
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (title.isNotEmpty()) {
                        vm.insertNote(title, content)
                        showAddNote = false
                    }
                }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { showAddNote = false }) { Text("Cancel") } }
        )
    }
}

@Composable
fun SummaryCard(modifier: Modifier, title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: androidx.compose.ui.graphics.Color) {
    Card(modifier = modifier) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(icon, null, tint = color)
            Text(title, style = MaterialTheme.typography.labelMedium)
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}