package com.lifemanager.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(vm: AppViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Expenses", "Income", "Loans", "Savings")
    Scaffold(topBar = { TopAppBar(title = { Text("Finance", fontWeight = FontWeight.Bold) }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            ScrollableTabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { i, t -> Tab(selected = selectedTab == i, onClick = { selectedTab = i }, text = { Text(t) }) }
            }
            when (selectedTab) {
                0 -> ExpensesTab(vm)
                1 -> IncomeTab(vm)
                2 -> LoansTab(vm)
                3 -> SavingsTab(vm)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesTab(vm: AppViewModel) {
    val expenses by vm.allExpenses.collectAsStateWithLifecycle()
    var showAdd by remember { mutableStateOf(false) }
    val categories = listOf("Groceries","Fuel","Utilities","Rent","Entertainment","Medical","Shopping","Transport","Education","Personal","Other")
    Box(Modifier.fillMaxSize()) {
        if (expenses.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No expenses yet", color = MaterialTheme.colorScheme.onSurfaceVariant) }
        } else {
            LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Total", style = MaterialTheme.typography.labelMedium)
                            Text("${"%.2f".format(expenses.sumOf { it.amount })}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                items(expenses) { e ->
                    Card(Modifier.fillMaxWidth()) {
                        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) { Text(e.title, fontWeight = FontWeight.Medium); Text(e.category, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                            Text("${"%.2f".format(e.amount)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                            IconButton(onClick = { vm.deleteExpense(e) }) { Icon(Icons.Filled.Delete, null, tint = MaterialTheme.colorScheme.error) }
                        }
                    }
                }
            }
        }
        FloatingActionButton(onClick = { showAdd = true }, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) { Icon(Icons.Filled.Add, null) }
    }
    if (showAdd) {
        var title by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var category by remember { mutableStateOf(categories[0]) }
        var expanded by remember { mutableStateOf(false) }
        AlertDialog(onDismissRequest = { showAdd = false }, title = { Text("Add Expense") },
            text = { Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                    OutlinedTextField(value = category, onValueChange = {}, readOnly = true, label = { Text("Category") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }, modifier = Modifier.menuAnchor().fillMaxWidth())
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        categories.forEach { c -> DropdownMenuItem(text = { Text(c) }, onClick = { category = c; expanded = false }) }
                    }
                }
            }},
            confirmButton = { Button(onClick = { val a = amount.toDoubleOrNull(); if (title.isNotEmpty() && a != null) { vm.insertExpense(title, a, category); showAdd = false } }) { Text("Save") } },
            dismissButton = { TextButton(onClick = { showAdd = false }) { Text("Cancel") } }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeTab(vm: AppViewModel) {
    val incomes by vm.allIncomes.collectAsStateWithLifecycle()
    var showAdd by remember { mutableStateOf(false) }
    val sources = listOf("Salary","Business","Interest","Side Job","Other")
    Box(Modifier.fillMaxSize()) {
        if (incomes.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No income yet", color = MaterialTheme.colorScheme.onSurfaceVariant) }
        } else {
            LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Total", style = MaterialTheme.typography.labelMedium)
                            Text("${"%.2f".format(incomes.sumOf { it.amount })}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                items(incomes) { i ->
                    Card(Modifier.fillMaxWidth()) {
                        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) { Text(i.title, fontWeight = FontWeight.Medium); Text(i.source, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                            Text("${"%.2f".format(i.amount)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            IconButton(onClick = { vm.deleteIncome(i) }) { Icon(Icons.Filled.Delete, null, tint = MaterialTheme.colorScheme.error) }
                        }
                    }
                }
            }
        }
        FloatingActionButton(onClick = { showAdd = true }, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) { Icon(Icons.Filled.Add, null) }
    }
    if (showAdd) {
        var title by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var source by remember { mutableStateOf(sources[0]) }
        var expanded by remember { mutableStateOf(false) }
        AlertDialog(onDismissRequest = { showAdd = false }, title = { Text("Add Income") },
            text = { Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                    OutlinedTextField(value = source, onValueChange = {}, readOnly = true, label = { Text("Source") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }, modifier = Modifier.menuAnchor().fillMaxWidth())
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        sources.forEach { s -> DropdownMenuItem(text = { Text(s) }, onClick = { source = s; expanded = false }) }
                    }
                }
            }},
            confirmButton = { Button(onClick = { val a = amount.toDoubleOrNull(); if (title.isNotEmpty() && a != null) { vm.insertIncome(title, a, source); showAdd = false } }) { Text("Save") } },
            dismissButton = { TextButton(onClick = { showAdd = false }) { Text("Cancel") } }
        )
    }
}

@Composable
fun LoansTab(vm: AppViewModel) {
    val loans by vm.allLoans.collectAsStateWithLifecycle()
    var showAdd by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize()) {
        if (loans.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No loans yet", color = MaterialTheme.colorScheme.onSurfaceVariant) }
        } else {
            LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(loans) { l ->
                    Card(Modifier.fillMaxWidth()) {
                        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) { Text(l.name, fontWeight = FontWeight.Medium); Text(l.lenderBorrower, style = MaterialTheme.typography.bodySmall) }
                            Text("${"%.2f".format(l.principal)}", fontWeight = FontWeight.Bold)
                            IconButton(onClick = { vm.deleteLoan(l) }) { Icon(Icons.Filled.Delete, null, tint = MaterialTheme.colorScheme.error) }
                        }
                    }
                }
            }
        }
        FloatingActionButton(onClick = { showAdd = true }, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) { Icon(Icons.Filled.Add, null) }
    }
    if (showAdd) {
        var name by remember { mutableStateOf("") }
        var person by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        AlertDialog(onDismissRequest = { showAdd = false }, title = { Text("Add Loan") },
            text = { Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Loan Name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(value = person, onValueChange = { person = it }, label = { Text("Lender/Borrower") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            }},
            confirmButton = { Button(onClick = { val a = amount.toDoubleOrNull(); if (name.isNotEmpty() && a != null) { vm.insertLoan(name, person, a, 0.0, "monthly", System.currentTimeMillis(), System.currentTimeMillis()); showAdd = false } }) { Text("Save") } },
            dismissButton = { TextButton(onClick = { showAdd = false }) { Text("Cancel") } }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavingsTab(vm: AppViewModel) {
    val savings by vm.allSavings.collectAsStateWithLifecycle()
    val total by vm.totalSavings.collectAsStateWithLifecycle()
    var showAdd by remember { mutableStateOf(false) }
    val types = listOf("Bank","Cash","Investment","Asset","Other")
    Box(Modifier.fillMaxSize()) {
        LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Total Savings", style = MaterialTheme.typography.labelMedium)
                        Text("${"%.2f".format(total ?: 0.0)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    }
                }
            }
            items(savings) { s ->
                Card(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) { Text(s.name, fontWeight = FontWeight.Medium); Text(s.type, style = MaterialTheme.typography.bodySmall) }
                        Text("${"%.2f".format(s.amount)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        IconButton(onClick = { vm.deleteSavings(s) }) { Icon(Icons.Filled.Delete, null, tint = MaterialTheme.colorScheme.error) }
                    }
                }
            }
        }
        FloatingActionButton(onClick = { showAdd = true }, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) { Icon(Icons.Filled.Add, null) }
    }
    if (showAdd) {
        var name by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var type by remember { mutableStateOf(types[0]) }
        var expanded by remember { mutableStateOf(false) }
        AlertDialog(onDismissRequest = { showAdd = false }, title = { Text("Add Savings") },
            text = { Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                    OutlinedTextField(value = type, onValueChange = {}, readOnly = true, label = { Text("Type") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }, modifier = Modifier.menuAnchor().fillMaxWidth())
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        types.forEach { t -> DropdownMenuItem(text = { Text(t) }, onClick = { type = t; expanded = false }) }
                    }
                }
            }},
            confirmButton = { Button(onClick = { val a = amount.toDoubleOrNull(); if (name.isNotEmpty() && a != null) { vm.insertSavings(name, type, a); showAdd = false } }) { Text("Save") } },
            dismissButton = { TextButton(onClick = { showAdd = false }) { Text("Cancel") } }
        )
    }
}